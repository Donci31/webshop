package hu.bme.aut.webshop.alf2023javant.controller;

import hu.bme.aut.webshop.alf2023javant.model.Category;
import hu.bme.aut.webshop.alf2023javant.model.CategoryDto;
import hu.bme.aut.webshop.alf2023javant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    ResponseEntity<List<Category>> getProducts() {
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    ResponseEntity<Category> getProductsById(@PathVariable Long id) {
        Optional<Category> optProduct = categoryRepository.findById(id);
        return optProduct
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/categories")
    ResponseEntity<String> createProduct(@RequestBody CategoryDto categoryDto) {

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        categoryRepository.save(category);

        return new ResponseEntity<>("Category saved to database!", HttpStatus.OK);
    }

    @PutMapping("/categories/{id}")
    ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Optional<Category> optCategory = categoryRepository.findById(id);

        if (optCategory.isEmpty())
            return new ResponseEntity<>("No such category exist!", HttpStatus.BAD_REQUEST);

        Category category = optCategory.get();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        categoryRepository.save(category);

        return new ResponseEntity<>("Category updated!", HttpStatus.OK);
    }
}
