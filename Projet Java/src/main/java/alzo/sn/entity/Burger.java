package alzo.sn.entity;


import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Burger {
    

    private int id;

    private String name;

    private String description;

    private Double price;

    private String imagepath;

    private boolean archived = false;
}
