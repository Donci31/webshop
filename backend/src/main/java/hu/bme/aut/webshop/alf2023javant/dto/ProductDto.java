package hu.bme.aut.webshop.alf2023javant.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;

    private String description;

    private Integer price;

    private Long categoryId;
}
