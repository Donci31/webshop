package hu.bme.aut.webshop.alf2023javant.dto;

import hu.bme.aut.webshop.alf2023javant.entity.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignUpDto {

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Role role;

    @NotBlank
    @Size(min = 3, max = 30)
    private String password;
}