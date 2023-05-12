package hu.bme.aut.webshop.alf2023javant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String description;
}
