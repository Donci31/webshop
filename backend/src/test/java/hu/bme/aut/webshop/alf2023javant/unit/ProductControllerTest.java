package hu.bme.aut.webshop.alf2023javant.unit;

import hu.bme.aut.webshop.alf2023javant.controller.ProductController;
import hu.bme.aut.webshop.alf2023javant.dto.ProductDto;
import hu.bme.aut.webshop.alf2023javant.entity.Category;
import hu.bme.aut.webshop.alf2023javant.entity.Product;
import hu.bme.aut.webshop.alf2023javant.repository.CategoryRepository;
import hu.bme.aut.webshop.alf2023javant.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void testGetProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        products.add(new Product());

        Mockito.when(productRepository.findAll()).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getProducts();

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(products, response.getBody());
    }

    @Test
    public void testGetProductsById() {
        Long productId = 1L;
        Product product = new Product();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProductsById(productId);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(product, response.getBody());
    }

    @Test
    public void testGetProductsById_NotFound() {
        Long productId = 1L;

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.getProductsById(productId);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }

    @Test
    public void testCreateProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Product 1");
        productDto.setDescription("Description 1");
        productDto.setPrice(10);
        productDto.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);

        Mockito.when(categoryRepository.getReferenceById(productDto.getCategoryId())).thenReturn(category);

        ResponseEntity<String> response = productController.createProduct(productDto);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Product saved to database!", response.getBody());

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        ProductDto productDto = new ProductDto();
        productDto.setName("Updated Product");
        productDto.setDescription("Updated Description");
        productDto.setPrice(20);
        productDto.setCategoryId(1L);

        Product existingProduct = new Product();
        existingProduct.setId(productId);

        Category category = new Category();
        category.setId(1L);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        Mockito.when(categoryRepository.getReferenceById(productDto.getCategoryId())).thenReturn(category);

        ResponseEntity<String> response = productController.updateProduct(productId, productDto);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Product updated!", response.getBody());

        Mockito.verify(productRepository, Mockito.times(1)).save(existingProduct);
        Assert.assertEquals(productDto.getName(), existingProduct.getName());
        Assert.assertEquals(productDto.getDescription(), existingProduct.getDescription());
        Assert.assertEquals(productDto.getPrice(), existingProduct.getPrice(), 0.001);
        Assert.assertEquals(category, existingProduct.getCategory());
    }
}