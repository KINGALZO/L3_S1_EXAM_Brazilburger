package alzo.sn.repository.Impl;

import alzo.sn.repository.CommandeRepository;
import alzo.sn.config.database.Database;
import alzo.sn.config.factory.services.ServicesFactory;
import alzo.sn.entity.*;
import alzo.sn.services.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CommandeRepositoryImpl implements CommandeRepository {

    private Database database;
    private static CommandeRepositoryImpl instance = null;

    public CommandeRepositoryImpl(Database database) {
        this.database = database;
    }

    public static CommandeRepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new CommandeRepositoryImpl(database);
        }
        return instance;
    }

    @Override
    public int insert(Commande commande) {
        String sql = "INSERT INTO commande " +
                "(clientid, menuid, complementid, burgerid, datecommande, statut, modepaiement, montanttotal, archived) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, commande.getClient().getId());
            statement.setInt(2, commande.getClient().getId());

            if (commande.getMenu() != null) {
                statement.setInt(3, commande.getMenu().getId());
            } else {
                statement.setNull(3, Types.INTEGER);
            }

            if (commande.getComplement() != null) {
                statement.setInt(4, commande.getComplement().getId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            if (commande.getBurger() != null) {
                statement.setInt(5, commande.getBurger().getId());
            } else {
                statement.setNull(5, Types.INTEGER);
            }
            statement.setTimestamp(6, new Timestamp(commande.getDateCommande().getTime()));
            statement.setString(7, commande.getStatut().name());
            statement.setString(8, commande.getModePaiement().name());
            statement.setDouble(9, commande.getMontantTotal());
            statement.setBoolean(10, commande.isArchived());

            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Commande> selectAll() {
        List<Commande> commandes = new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement ps;

        try {
            ps = conn.prepareStatement("SELECT * FROM commande");
            return database.<Commande>fetchAll(ps, this::toEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandes;
    }

    @Override
    public Optional<Commande> selectById(int id) {
        Connection conn = database.getConnection();
        PreparedStatement ps;

        try {
            ps = conn.prepareStatement("SELECT * FROM commande WHERE id=?");
            ps.setInt(1, id);
            return database.<Commande>fetch(ps, this::toEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private Commande toEntity(ResultSet rs) throws SQLException {
        Commande commande = new Commande();

        ClientServices clientServices =
                (ClientServices) ServicesFactory.createServices(entityName.CLIENT);
        MenuServices menuServices =
                (MenuServices) ServicesFactory.createServices(entityName.MENU);
        ComplementServices complementServices =
                (ComplementServices) ServicesFactory.createServices(entityName.COMPLEMENT);
        BurgerServices burgerServices =
                (BurgerServices) ServicesFactory.createServices(entityName.BURGER);

        commande.setId(rs.getInt("id"));
        commande.setClient(clientServices.getById(rs.getInt("clientid")).orElse(null));
        commande.setMenu(menuServices.selectByName(rs.getString("menuname")).orElse(null));
        commande.setComplement(complementServices.getById(rs.getInt("complementid")).orElse(null));
        commande.setBurger(burgerServices.getById(rs.getInt("burgerid")).orElse(null));
        commande.setDateCommande(new Date(rs.getTimestamp("datecommande").getTime()));
        commande.setStatut(StatusCommande.valueOf(rs.getString("statut")));
        commande.setModePaiement(typePayement.valueOf(rs.getString("modepaiement")));
        commande.setMontantTotal(rs.getDouble("montanttotal"));
        commande.setArchived(rs.getBoolean("archived"));

        return commande;
    }
}

