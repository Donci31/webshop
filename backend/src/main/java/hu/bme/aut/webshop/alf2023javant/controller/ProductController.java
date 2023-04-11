package hu.bme.aut.webshop.alf2023javant.controller;

import hu.bme.aut.webshop.alf2023javant.model.Product;
import hu.bme.aut.webshop.alf2023javant.model.ProductDto;
import hu.bme.aut.webshop.alf2023javant.repository.CategoryRepository;
import hu.bme.aut.webshop.alf2023javant.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/products")
    ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    ResponseEntity<Product> getProductsById(@PathVariable Long id) {
        Optional<Product> optProduct = productRepository.findById(id);
        return optProduct
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/products")
    ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) {

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryRepository.getReferenceById(productDto.getCategoryId()));
        productRepository.save(product);

        return new ResponseEntity<>("Product saved to database!", HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Optional<Product> optProduct = productRepository.findById(id);

        if (optProduct.isEmpty())
            return new ResponseEntity<>("No such product exist!", HttpStatus.BAD_REQUEST);

        Product product = optProduct.get();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryRepository.getReferenceById(productDto.getCategoryId()));

        productRepository.save(product);

        return new ResponseEntity<>("Product updated!", HttpStatus.OK);
    }
}
