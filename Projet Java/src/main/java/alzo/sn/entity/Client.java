package alzo.sn.entity;


import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Client {
    private int id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String passwordHash;
}
