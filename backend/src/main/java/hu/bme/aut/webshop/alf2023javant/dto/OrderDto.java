package hu.bme.aut.webshop.alf2023javant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private String created_at;

    @NotBlank
    @Size(max = 15)
    private String shipping_zip;

    @NotBlank
    @Size(max = 30)
    private String shipping_city;

    @NotBlank
    @Size(max = 50)
    private String shipping_address;

    @NotNull
    private String comment;

    private Long user_id;

    @NotEmpty
    private List<OrderItemDto> products;
}