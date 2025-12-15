package alzo.sn.services.impl;
import alzo.sn.repository.MenuRepository;
import alzo.sn.services.MenuServices;
import alzo.sn.entity.*;
import java.util.List;
import java.util.Optional;
public class MenuServicesImpl implements MenuServices {

    private static MenuServicesImpl instance;
    private final MenuRepository repository;

    private MenuServicesImpl(MenuRepository repository) {
        this.repository = repository;
    }

    public static MenuServicesImpl getInstance(MenuRepository repository) {
        if (instance == null) {
            instance = new MenuServicesImpl(repository);
        }
        return instance;
    }

    public boolean insert(Menu menu) {
        return this.repository.insert(menu)!=0;
    }
    @Override
    public List<Menu> selectAll() {
        return repository.selectAll();
    }
    @Override
    public Optional<Menu> selectByName(String name) {
        return repository.selectByName(name);
    }
}