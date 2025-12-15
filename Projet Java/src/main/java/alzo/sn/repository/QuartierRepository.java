package alzo.sn.repository;
import java.util.List;
import java.util.Optional;
import alzo.sn.entity.Quartier;
public interface QuartierRepository {
    int insert(Quartier quartier);
    List<Quartier> selectAll();
    Optional<Quartier> selectById(int id);
    Optional<Quartier> selectByName(String name);

}
