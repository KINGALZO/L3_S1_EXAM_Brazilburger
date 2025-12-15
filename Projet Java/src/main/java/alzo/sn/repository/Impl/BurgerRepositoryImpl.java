package alzo.sn.repository.Impl;
import alzo.sn.repository.BurgerRepository;
import alzo.sn.entity.Burger;
import alzo.sn.config.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BurgerRepositoryImpl implements BurgerRepository {

    private static BurgerRepositoryImpl instance = null;
    
    private Database database;
    private BurgerRepositoryImpl(Database database) {
        this.database = database;
    }
    public static BurgerRepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new BurgerRepositoryImpl(database);
        }
        return instance;
    }
    @Override
    public List<Burger> selectAll() {
        List<Burger> Burgers = new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("select * from burger");
            return database.<Burger>fetchAll(ps,this::toEntity);
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return Burgers;
    }

    @Override
    public int insert(Burger burger) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("INSERT INTO burger (name, description, price, imagepath, archived) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, burger.getName());
            ps.setString(2, burger.getDescription());
            ps.setDouble(3, burger.getPrice());
            ps.setString(4, burger.getImagepath());
            ps.setBoolean(5, burger.isArchived());
            return ps.executeUpdate();
        }  catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public Optional<Burger> selectById(int id) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("Select * from burger where id=?");
            ps.setInt(1, id);
            return database.<Burger>fetch(ps,this::toEntity);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Optional<Burger> selectByName(String name) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {   
            ps = conn.prepareStatement("Select * from burger where name=?");
            ps.setString(1, name);
            return database.<Burger>fetch(ps,this::toEntity);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Burger toEntity(ResultSet rs) throws SQLException{
        Burger burger = new Burger();
        burger.setId(rs.getInt("id"));
        burger.setName(rs.getString("name"));
        burger.setDescription(rs.getString("description"));
        burger.setPrice(rs.getDouble("price"));
        burger.setImagepath(rs.getString("imagepath"));
        burger.setArchived(rs.getBoolean("archived"));
        return burger;
    }
}