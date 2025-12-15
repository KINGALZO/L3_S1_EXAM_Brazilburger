package alzo.sn.repository.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;

import alzo.sn.config.database.Database;
import alzo.sn.repository.ClientRepository;

import alzo.sn.entity.Client;

public class ClientRepositoryImpl implements ClientRepository {
    private static ClientRepositoryImpl instance = null;
    
    private Database database;
    private ClientRepositoryImpl(Database database) {
        this.database = database;
    }
    public static ClientRepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new ClientRepositoryImpl(database);
        }
        return instance;
    }
    @Override
    public int insert(Client client) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("INSERT INTO client (firstname, lastname, email, phone, passwordhash) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getPhone());
            ps.setString(5, client.getPasswordHash());
            return ps.executeUpdate();
        }  catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
        
    }
    @Override
    public List<Client> selectAll() {
         List<Client> clients = new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("select * from client");
            return database.<Client>fetchAll(ps,this::toEntity);
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
        
    }
    @Override
    public Optional<Client> selectById(int id) {
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("Select * from client where id=?");
            ps.setInt(1, id);
            return database.<Client>fetch(ps,this::toEntity);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();

}
    private Client toEntity(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setFirstName(rs.getString("firstname"));
        client.setLastName(rs.getString("lastname"));
        client.setEmail(rs.getString("email"));
        client.setPhone(rs.getString("phone"));
        client.setPasswordHash(rs.getString("passwordhash"));
        return client;
    }
}
