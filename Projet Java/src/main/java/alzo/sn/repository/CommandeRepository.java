package alzo.sn.repository;
import alzo.sn.entity.Commande;
import java.util.List;
public interface CommandeRepository {
    int insert(Commande commande);
    List<Commande> selectAll();
    java.util.Optional<Commande> selectById(int id);
}
