package alzo.sn.services.impl;
import alzo.sn.services.QuartierServices;
import alzo.sn.repository.QuartierRepository;
import alzo.sn.entity.Quartier;
import java.util.List;
import java.util.Optional;

public class QuartierServicesImpl implements QuartierServices {

    private static QuartierServicesImpl instance= null;
    private final QuartierRepository repository;

    private QuartierServicesImpl(QuartierRepository repository) {
        this.repository = repository;
    }

    public static QuartierServicesImpl getInstance(QuartierRepository repository) {
        if (instance == null) {
            instance = new QuartierServicesImpl(repository);
        }
        return instance;
    }
    @Override
    public int insert(Quartier quartier) {
        return repository.insert(quartier);
    }
    @Override
    public List<Quartier> selectAll() {
        return repository.selectAll();
    }
    @Override
    public Optional<Quartier> selectById(int id) {
        return repository.selectById(id);
    }
    @Override
    public Optional<Quartier> selectByName(String name) {
        return repository.selectByName(name);
    }
}
