package hu.bme.aut.webshop.alf2023javant.model;

import lombok.Data;

@Data
public class ProductDto {
    private String name;

    private String description;

    private int price;

    private int categoryId;
}
