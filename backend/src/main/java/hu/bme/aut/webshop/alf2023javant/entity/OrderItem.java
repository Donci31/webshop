package hu.bme.aut.webshop.alf2023javant.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderitem")
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    private Integer quantity;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}