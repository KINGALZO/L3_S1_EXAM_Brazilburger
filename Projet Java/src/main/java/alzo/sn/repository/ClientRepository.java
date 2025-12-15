package alzo.sn.repository;

import java.util.List;
import java.util.Optional;


import alzo.sn.entity.*;

public interface ClientRepository {
    List<Client> selectAll();
    Optional<Client> selectById(int id);
    int insert(Client client);
}
