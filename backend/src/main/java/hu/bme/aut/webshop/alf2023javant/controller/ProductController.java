package hu.bme.aut.webshop.alf2023javant.controller;

import hu.bme.aut.webshop.alf2023javant.entity.Product;
import hu.bme.aut.webshop.alf2023javant.dto.ProductDto;
import hu.bme.aut.webshop.alf2023javant.repository.CategoryRepository;
import hu.bme.aut.webshop.alf2023javant.repository.ProductRepository;
import jakarta.validation.Valid;
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
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    @CrossOrigin
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductsById(@PathVariable Long id) {
        Optional<Product> optProduct = productRepository.findById(id);
        return optProduct
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductDto productDto) {

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryRepository.getReferenceById(productDto.getCategoryId()));
        productRepository.save(product);

        return new ResponseEntity<>("Product saved to database!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
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
