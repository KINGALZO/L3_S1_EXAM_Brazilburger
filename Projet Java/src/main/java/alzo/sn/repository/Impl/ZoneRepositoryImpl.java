package alzo.sn.repository.Impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alzo.sn.config.database.Database;
import alzo.sn.entity.Zone;
import alzo.sn.repository.ZoneRepository;


public class ZoneRepositoryImpl implements ZoneRepository {
    private static ZoneRepositoryImpl instance = null;
    private final Database database;
        public ZoneRepositoryImpl(Database database) {
            this.database = database;
        }
         public static ZoneRepositoryImpl getInstance(Database database) {
            if (instance == null) {
                instance = new ZoneRepositoryImpl(database);
            }
            return instance;
        }

     @Override
        public int insert(Zone zone) {
            String sql = "INSERT INTO zone (nom,prixlivraison) VALUES (?, ?)";
            try (Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, zone.getNom());
                statement.setDouble(2, zone.getPrixLivraison());
                return statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }
        @Override
        public List<Zone> selectAll() {
            List<Zone> zones = new ArrayList<>();
            Connection conn = database.getConnection();
            PreparedStatement ps;
            try {
                ps = conn.prepareStatement("select * from zone");
                return database.<Zone>fetchAll(ps,this::toEntity);
            }  catch (SQLException e) {
                e.printStackTrace();
            }
            return zones;
        }
         @Override
    public Optional<Zone> selectById(int id) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("Select * from zone where id=?");
            ps.setInt(1, id);
            return database.<Zone>fetch(ps,this::toEntity);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Optional<Zone> selectByName(String name) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("Select * from zone where nom=?");
            ps.setString(1, name);
            return database.<Zone>fetch(ps,this::toEntity);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Zone toEntity(ResultSet rs) throws SQLException{
        Zone zone = new Zone();
        zone.setId(rs.getInt("id"));
        zone.setNom(rs.getString("nom"));
        zone.setPrixLivraison(rs.getDouble("prixlivraison"));
        return zone;

}
}