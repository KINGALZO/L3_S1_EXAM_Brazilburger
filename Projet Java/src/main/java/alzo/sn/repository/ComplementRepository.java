package alzo.sn.repository;

import java.util.List;
import java.util.Optional;

import alzo.sn.entity.Complement;

public interface ComplementRepository {
    List<Complement> selectAll();
    Optional<Complement> selectById(int id);
    Optional<Complement> selectByName(String name);
    int insert(Complement complement);
}
