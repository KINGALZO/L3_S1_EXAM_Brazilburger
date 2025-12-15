package alzo.sn.services.impl;
import alzo.sn.repository.LivraisonRepository;
import alzo.sn.services.LivraisonServices;
public class LivraisonServicesImpl implements LivraisonServices {

    private static LivraisonServicesImpl instance;
    private final LivraisonRepository repository;

    private LivraisonServicesImpl(LivraisonRepository repository) {
        this.repository = repository;
    }

    public static LivraisonServicesImpl getInstance(LivraisonRepository repository) {
        if (instance == null) {
            instance = new LivraisonServicesImpl(repository);
        }
        return instance;
    }
}
