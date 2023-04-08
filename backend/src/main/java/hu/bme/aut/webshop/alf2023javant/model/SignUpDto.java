package hu.bme.aut.webshop.alf2023javant.model;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
}