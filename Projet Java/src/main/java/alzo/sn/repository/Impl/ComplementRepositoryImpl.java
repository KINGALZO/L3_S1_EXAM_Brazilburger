package alzo.sn.repository.Impl;
import alzo.sn.repository.ComplementRepository;
import alzo.sn.config.database.Database;
import alzo.sn.entity.Complement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComplementRepositoryImpl implements ComplementRepository {

    private static ComplementRepositoryImpl instance = null;
    
    
    private Database database;
    private ComplementRepositoryImpl(Database database) {
        this.database = database;
    }
    public static ComplementRepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new ComplementRepositoryImpl(database);
        }
        return instance;
    }
    @Override
    public List<Complement> selectAll() {
        List<Complement> complements = new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("select * from complement");
            return database.<Complement>fetchAll(ps,this::toEntity);
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return complements;
    }
    @Override
    public Optional<Complement> selectById(int id) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("Select * from complement where id=?");
            ps.setInt(1, id);
            return database.<Complement>fetch(ps,this::toEntity);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Optional<Complement> selectByName(String name) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("Select * from complement where name=?");
            ps.setString(1, name);
            return database.<Complement>fetch(ps,this::toEntity);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public int insert(Complement complement) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("INSERT INTO complement (name, price, imagepath, archived) VALUES (?, ?, ?, ?)");
            ps.setString(1, complement.getName());
            ps.setDouble(2, complement.getPrice());
            ps.setString(3, complement.getImagepath());
            ps.setBoolean(4, complement.isArchived());
            return ps.executeUpdate();
        }  catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    private Complement toEntity(ResultSet rs) throws SQLException{
        Complement complement = new Complement();
        complement.setId(rs.getInt("id"));
        complement.setName(rs.getString("name"));
        complement.setPrice(rs.getDouble("price"));
        complement.setImagepath(rs.getString("imagepath"));
        complement.setArchived(rs.getBoolean("archived"));
        return complement;
    }


}
