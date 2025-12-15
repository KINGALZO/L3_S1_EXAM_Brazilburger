package alzo.sn.services;
import alzo.sn.entity.Commande;
import java.util.List;
import java.util.Optional;
public interface CommandeServices {
    int insert(Commande commande);
    Optional<Commande> findById(int id);
    List<Commande> findAll();
}
