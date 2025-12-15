package alzo.sn.services.impl;
import alzo.sn.repository.BurgerRepository;
import alzo.sn.services.BurgerServices;
import alzo.sn.entity.Burger;
import java.util.List;
import java.util.Optional;
public class BurgerServicesImpl implements BurgerServices {

    private static BurgerServicesImpl instance;
    private final BurgerRepository burgerRepository;

    private BurgerServicesImpl(BurgerRepository burgerRepository) {
        this.burgerRepository = burgerRepository;
    }

    public static BurgerServicesImpl getInstance(BurgerRepository burgerRepository) {
        if (instance == null) {
            instance = new BurgerServicesImpl(burgerRepository);
        }
        return instance;
    }
    @Override
    public boolean createBurger(Burger burger) {
        return this.burgerRepository.insert(burger)!=0;
    }
    @Override
    
    public Optional<Burger> getById(int id) {
        return this.burgerRepository.selectById(id);
    }
    @Override
    public List<Burger> selectAll() {
        return burgerRepository.selectAll();
    }
    @Override
    public Optional<Burger> getByName(String name) {
        return burgerRepository.selectByName(name);
    }
   

}


