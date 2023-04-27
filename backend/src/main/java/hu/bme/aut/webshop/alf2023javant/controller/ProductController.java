package hu.bme.aut.webshop.alf2023javant.controller;

import hu.bme.aut.webshop.alf2023javant.entity.Product;
import hu.bme.aut.webshop.alf2023javant.dto.ProductDto;
import hu.bme.aut.webshop.alf2023javant.repository.CategoryRepository;
import hu.bme.aut.webshop.alf2023javant.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    @CrossOrigin
    ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> getProductsById(@PathVariable Long id) {
        Optional<Product> optProduct = productRepository.findById(id);
        return optProduct
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) {

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryRepository.getReferenceById(productDto.getCategoryId()));
        productRepository.save(product);

        return new ResponseEntity<>("Product saved to database!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Optional<Product> optProduct = productRepository.findById(id);

        if (optProduct.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Product product = optProduct.get();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryRepository.getReferenceById(productDto.getCategoryId()));

        productRepository.save(product);

        return new ResponseEntity<>("Product updated!", HttpStatus.OK);
    }
}
