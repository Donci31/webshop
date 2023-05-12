package hu.bme.aut.webshop.alf2023javant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDto {

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    private Integer price;

    @NotNull
    private Long categoryId;
}
