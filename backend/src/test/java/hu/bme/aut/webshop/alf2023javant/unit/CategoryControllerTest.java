package hu.bme.aut.webshop.alf2023javant.unit;

import hu.bme.aut.webshop.alf2023javant.controller.CategoryController;
import hu.bme.aut.webshop.alf2023javant.dto.CategoryDto;
import hu.bme.aut.webshop.alf2023javant.entity.Category;
import hu.bme.aut.webshop.alf2023javant.entity.Product;
import hu.bme.aut.webshop.alf2023javant.repository.CategoryRepository;
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
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void testGetProducts() {
        List<Category> categories = new ArrayList<>();

        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");
        category1.setDescription("Description 1");

        Category category2 = new Category();
        category1.setId(2L);
        category1.setName("Category 2");
        category1.setDescription("Description 2");

        Category category3 = new Category();
        category1.setId(3L);
        category1.setName("Category 3");
        category1.setDescription("Description 3");

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);

        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        ResponseEntity<List<Category>> response = categoryController.getProducts();

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(categories, response.getBody());
    }

    @Test
    public void testGetProductsById() {
        Long categoryId = 1L;

        Category category = new Category();
        category.setId(categoryId);
        category.setName("Category 1");
        category.setDescription("Description 1");

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        ResponseEntity<Category> response = categoryController.getProductsById(categoryId);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(category, response.getBody());
    }

    @Test
    public void testGetProductsById_NotFound() {
        Long categoryId = 1L;

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        ResponseEntity<Category> response = categoryController.getProductsById(categoryId);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }

    @Test
    public void testCreateProduct() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("CategoryDto 1");
        categoryDto.setDescription("Description 1");

        ResponseEntity<String> response = categoryController.createProduct(categoryDto);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Category saved to database!", response.getBody());

        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));
    }

    @Test
    public void testUpdateProduct() {
        Long categoryId = 1L;

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("CategoryDto 1");
        categoryDto.setDescription("Description 1");

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Category 1");
        existingCategory.setDescription("Description 1");

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        ResponseEntity<String> response = categoryController.updateProduct(categoryId, categoryDto);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Category updated!", response.getBody());

        Mockito.verify(categoryRepository, Mockito.times(1)).save(existingCategory);
        Assert.assertEquals(categoryDto.getName(), existingCategory.getName());
        Assert.assertEquals(categoryDto.getDescription(), existingCategory.getDescription());
    }

    @Test
    public void testUpdateProduct_NotFound() {
        Long categoryId = 1L;

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("CategoryDto 1");
        categoryDto.setDescription("Description 1");

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = categoryController.updateProduct(categoryId, categoryDto);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertNull(response.getBody());

        Mockito.verify(categoryRepository, Mockito.never()).save(Mockito.any(Category.class));
    }
}
