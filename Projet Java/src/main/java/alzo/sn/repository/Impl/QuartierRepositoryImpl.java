package alzo.sn.repository.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alzo.sn.config.database.Database;
import alzo.sn.config.factory.services.ServicesFactory;
import alzo.sn.entity.Quartier;
import alzo.sn.entity.entityName;
import alzo.sn.repository.QuartierRepository;

import alzo.sn.services.ZoneServices;

public class QuartierRepositoryImpl implements QuartierRepository {
    private static QuartierRepositoryImpl instance = null;
    private Database database;
     public QuartierRepositoryImpl(Database database) {
        this.database = database;
    }

   public static QuartierRepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new QuartierRepositoryImpl(database);
        }
        return instance;
    }
     @Override
    public int insert(Quartier quartier) {
        String sql = "INSERT INTO quartier (nom, zoneid) VALUES (?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, quartier.getNom());
            statement.setInt(2, quartier.getZone().getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Quartier> selectAll() {
       List<Quartier> quartiers = new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("select * from quartier");
            return database.<Quartier>fetchAll(ps,this::toEntity);
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return quartiers;
    }

    @Override
    public Optional<Quartier> selectById(int id) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("Select * from quartier where id=?");
            ps.setInt(1, id);
            return database.<Quartier>fetch(ps,this::toEntity);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Optional<Quartier> selectByName(String name) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("Select * from quartier where nom=?");
            ps.setString(1, name);
            return database.<Quartier>fetch(ps,this::toEntity);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Quartier toEntity(ResultSet rs) throws SQLException{
        ZoneServices zoneServices = (ZoneServices) ServicesFactory.createServices(entityName.ZONE);
        Quartier quartier = new Quartier();
        quartier.setId(rs.getInt("id"));
        quartier.setNom(rs.getString("nom"));
        quartier.setZone(zoneServices.selectById(rs.getInt("zoneid")).orElse(null));
        return quartier;
    }
}
