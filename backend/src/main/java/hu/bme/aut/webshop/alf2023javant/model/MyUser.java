package hu.bme.aut.webshop.alf2023javant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class MyUser {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String role;
}
