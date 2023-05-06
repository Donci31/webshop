package hu.bme.aut.webshop.alf2023javant.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private String created_at;
    private String shipping_zip;
    private String shipping_city;
    private String shipping_address;
    private String comment;
    private Long user_id;
    private List<OrderItemDto> orderItems;
}