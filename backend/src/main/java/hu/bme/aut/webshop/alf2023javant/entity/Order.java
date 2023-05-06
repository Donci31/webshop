package hu.bme.aut.webshop.alf2023javant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private String created_at;
    private String shipping_zip;
    private String shipping_city;
    private String shipping_address;
    private String comment;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}