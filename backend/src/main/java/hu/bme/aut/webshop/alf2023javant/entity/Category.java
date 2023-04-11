package hu.bme.aut.webshop.alf2023javant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @JsonBackReference
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}