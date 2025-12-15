package alzo.sn.entity;


import java.util.List;

import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Zone {

    private int id;

    private String nom;

    private Double prixLivraison;

    private List<Quartier> quartiers;

    public Quartier addQuartier(Quartier quartier) {
        this.quartiers.add(quartier);
        return quartier;
    }

    public String ListQuartierNames() {
        StringBuilder names = new StringBuilder();
        for (Quartier quartier : quartiers) {
            names.append(quartier.getNom()).append(", ");
        }
        if (names.length() > 0) {
            names.setLength(names.length() - 2); // Remove trailing comma and space
        }
        return names.toString();
    }
    
}
