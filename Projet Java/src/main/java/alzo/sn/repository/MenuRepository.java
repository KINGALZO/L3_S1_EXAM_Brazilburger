package alzo.sn.repository;
import alzo.sn.entity.Menu;
import java.util.List;
import java.util.Optional;
public interface MenuRepository {
    public int insert(Menu menu);
    public List<Menu> selectAll();
    Optional <Menu> selectById(int id);
    public Optional<Menu> selectByName(String name);
}
