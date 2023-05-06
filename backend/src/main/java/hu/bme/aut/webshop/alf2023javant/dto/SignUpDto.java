package hu.bme.aut.webshop.alf2023javant.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;

    private String email;

    private String password;
}