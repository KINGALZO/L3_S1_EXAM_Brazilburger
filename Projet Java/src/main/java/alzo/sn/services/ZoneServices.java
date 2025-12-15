package alzo.sn.services;
import java.util.List;
import java.util.Optional;
import alzo.sn.entity.Zone;
public interface ZoneServices {
    int insert(Zone zone);
    List<Zone> selectAll();
    Optional<Zone> selectById(int id);
    Optional<Zone> selectByName(String name);
}
