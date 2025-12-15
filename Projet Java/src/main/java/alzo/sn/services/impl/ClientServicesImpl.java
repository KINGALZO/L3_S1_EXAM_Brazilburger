package alzo.sn.services.impl;
import java.util.List;
import java.util.Optional;

import alzo.sn.entity.*;
import alzo.sn.repository.ClientRepository;
import alzo.sn.services.ClientServices;
public class ClientServicesImpl implements ClientServices {

    private static ClientServicesImpl instance;
    private final ClientRepository repository;

    private ClientServicesImpl(ClientRepository repository) {
        this.repository = repository;
    }

    public static ClientServicesImpl getInstance(ClientRepository repository) {
        if (instance == null) {
            instance = new ClientServicesImpl(repository);
        }
        return instance;
    }
     @Override
    public boolean createClient(Client client) {
        return this.repository.insert(client)!=0;
    }
    @Override

    public Optional<Client> getById(int id) {
        return this.repository.selectById(id);
    }
    @Override
    public List<Client> selectAll() {
        return repository.selectAll();
    }
}
