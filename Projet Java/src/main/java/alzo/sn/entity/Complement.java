package alzo.sn.entity;


import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Complement {

    private int id;

    private String name;

    private double price;

    private String imagepath;

    private boolean archived = false;
}
