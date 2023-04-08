package hu.bme.aut.webshop.alf2023javant.model;

import lombok.Data;

@Data
public class LoginDto {
    private String email;

    private String password;
}