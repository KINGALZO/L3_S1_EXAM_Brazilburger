package alzo.sn.entity;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString

public class Quartier {
    private int id;

    private String nom;
    
    private Zone zone;
}
