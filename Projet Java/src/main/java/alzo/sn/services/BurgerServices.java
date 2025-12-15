package alzo.sn.services;

import java.util.List;
import java.util.Optional;

import alzo.sn.entity.Burger;

public interface BurgerServices {
    public boolean createBurger(Burger burger);
    Optional<Burger> getById(int id);
    List<Burger> selectAll();
    Optional<Burger> getByName(String name);
    
}
