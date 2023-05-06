package hu.bme.aut.webshop.alf2023javant.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long product_id;
    private Integer quantity;
    public OrderItemDto(Long product_id, Integer quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }
}