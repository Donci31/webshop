package hu.bme.aut.webshop.alf2023javant.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Integer price;

    @ManyToOne
    private Category category;
}