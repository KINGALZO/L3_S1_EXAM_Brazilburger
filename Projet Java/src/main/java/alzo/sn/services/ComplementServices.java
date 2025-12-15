package alzo.sn.services;

import java.util.List;
import java.util.Optional;

import alzo.sn.entity.Complement;

public interface ComplementServices {
    public boolean createComplement(Complement complement);
    Optional<Complement> getById(int id);
    Optional<Complement> getByName(String name);
    List<Complement> selectAll();
}
