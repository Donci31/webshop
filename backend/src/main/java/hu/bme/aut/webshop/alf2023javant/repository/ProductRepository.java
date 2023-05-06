package hu.bme.aut.webshop.alf2023javant.repository;

import hu.bme.aut.webshop.alf2023javant.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String Name);
}
