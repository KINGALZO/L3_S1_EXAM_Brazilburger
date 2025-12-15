package alzo.sn.services.impl;
import alzo.sn.repository.ComplementRepository;
import alzo.sn.services.ComplementServices;
import alzo.sn.entity.Complement;
import java.util.List;
import java.util.Optional;
public class ComplementServicesImpl implements ComplementServices {

    private static ComplementServicesImpl instance;
    private final ComplementRepository repository;

    private ComplementServicesImpl(ComplementRepository repository) {
        this.repository = repository;
    }

    public static ComplementServicesImpl getInstance(ComplementRepository repository) {
        if (instance == null) {
            instance = new ComplementServicesImpl(repository);
        }
        return instance;
    }
    @Override
    public boolean createComplement(Complement complement) {
        int result = repository.insert(complement);
        return result > 0;
    }
    @Override
    public Optional<Complement> getById(int id) {
        return repository.selectById(id);
    }
    @Override
    public List<Complement> selectAll() {
        return repository.selectAll();
    }
    @Override
    public Optional<Complement> getByName(String name) {
        return repository.selectByName(name);   
    }
}

