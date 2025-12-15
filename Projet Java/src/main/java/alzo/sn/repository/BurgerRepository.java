package alzo.sn.repository;

import java.util.List;
import java.util.Optional;

import alzo.sn.entity.Burger;

public interface BurgerRepository {
    List<Burger> selectAll();
    Optional<Burger> selectById(int id);
    Optional<Burger> selectByName(String name);
    int insert(Burger burger);
}
