package hu.bme.aut.webshop.alf2023javant.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;
}