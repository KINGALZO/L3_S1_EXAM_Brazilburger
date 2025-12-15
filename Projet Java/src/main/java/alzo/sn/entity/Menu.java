package alzo.sn.entity;


import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Menu {
    private int id;

    private String nom;

    private String description;

    private String imagepath;

    private Double price;

    private Burger burger;

    private Complement complement;

    private boolean archived = false;
        
}
