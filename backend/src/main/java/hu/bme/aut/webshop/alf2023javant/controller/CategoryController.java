package hu.bme.aut.webshop.alf2023javant.controller;

import hu.bme.aut.webshop.alf2023javant.entity.Category;
import hu.bme.aut.webshop.alf2023javant.dto.CategoryDto;
import hu.bme.aut.webshop.alf2023javant.entity.Product;
import hu.bme.aut.webshop.alf2023javant.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    ResponseEntity<List<Category>> getProducts() {
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Category> getProductsById(@PathVariable Long id) {
        Optional<Category> optCategory = categoryRepository.findById(id);
        return optCategory
                .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    ResponseEntity<String> createProduct(@Valid @RequestBody CategoryDto categoryDto) {

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        categoryRepository.save(category);

        return new ResponseEntity<>("Category saved to database!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        Optional<Category> optCategory = categoryRepository.findById(id);

        if (optCategory.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Category category = optCategory.get();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        categoryRepository.save(category);

        return new ResponseEntity<>("Category updated!", HttpStatus.OK);
    }

    @GetMapping("/{id}/products")
    ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long id) {
        Optional<Category> optCategory = categoryRepository.findById(id);
        return optCategory
                .map(category -> new ResponseEntity<>(category.getProducts(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
