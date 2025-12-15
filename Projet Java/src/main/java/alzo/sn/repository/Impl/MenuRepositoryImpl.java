package alzo.sn.repository.Impl;
import alzo.sn.repository.MenuRepository;
import alzo.sn.config.database.Database;
import alzo.sn.config.factory.services.ServicesFactory;
import alzo.sn.entity.*;
import alzo.sn.services.BurgerServices;
import alzo.sn.services.ComplementServices;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class MenuRepositoryImpl implements MenuRepository {

    private Database database;
    private static MenuRepositoryImpl instance = null;

    public MenuRepositoryImpl(Database database) {
        this.database = database;
    }

   public static MenuRepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new MenuRepositoryImpl(database);
        }
        return instance;
    }


    @Override
    public int insert(Menu menu) {
        String sql = "INSERT INTO menu (nom, description, imagepath, price, burgerid, complementid, archived) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, menu.getNom());
            statement.setString(2, menu.getDescription());
            statement.setString(3, menu.getImagepath());
            statement.setDouble(4, menu.getPrice());
            statement.setInt(5, menu.getBurger().getId());
            statement.setInt(6, menu.getComplement().getId());
            statement.setBoolean(7, menu.isArchived());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Menu> selectAll() {
       List<Menu> menus = new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("select * from menu");
            return database.<Menu>fetchAll(ps,this::toEntity);
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }

    @Override
    public Optional<Menu> selectByName(String name) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("Select * from menu where nom=?");
            ps.setString(1, name);
            return database.<Menu>fetch(ps,this::toEntity);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
     @Override
    public Optional<Menu> selectById(int id) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("Select * from menu where id=?");
            ps.setInt(1, id);
            return database.<Menu>fetch(ps,this::toEntity);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Menu toEntity(ResultSet rs) throws SQLException{
        Menu menu = new Menu();
        BurgerServices burgerServices=(BurgerServices)ServicesFactory.createServices(entityName.BURGER);
        ComplementServices complementServices=(ComplementServices)ServicesFactory.createServices(entityName.COMPLEMENT);
        menu.setId(rs.getInt("id"));
        menu.setNom(rs.getString("nom"));
        menu.setPrice(rs.getDouble("price"));
        menu.setDescription(rs.getString("description"));
        menu.setBurger(burgerServices.getById(rs.getInt("burgerid")).orElse(null));
        menu.setComplement(complementServices.getById(rs.getInt("complementid")).orElse(null));
        menu.setImagepath(rs.getString("imagepath"));
        menu.setArchived(rs.getBoolean("archived"));
        return menu;
    }
}