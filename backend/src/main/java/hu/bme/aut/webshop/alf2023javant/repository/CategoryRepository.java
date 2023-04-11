package hu.bme.aut.webshop.alf2023javant.repository;

import hu.bme.aut.webshop.alf2023javant.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
