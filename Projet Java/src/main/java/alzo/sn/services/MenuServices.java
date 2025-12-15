package alzo.sn.services;
import alzo.sn.entity.Menu;
import java.util.List;
import java.util.Optional;
public interface MenuServices {
    public boolean insert(Menu menu);
    public List<Menu> selectAll();
    public Optional<Menu> selectByName(String name);
}
