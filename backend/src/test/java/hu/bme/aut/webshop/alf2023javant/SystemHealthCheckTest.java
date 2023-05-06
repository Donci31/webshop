package hu.bme.aut.webshop.alf2023javant;

import hu.bme.aut.webshop.alf2023javant.entity.Category;
import hu.bme.aut.webshop.alf2023javant.entity.Product;
import hu.bme.aut.webshop.alf2023javant.entity.User;
import hu.bme.aut.webshop.alf2023javant.repository.CategoryRepository;
import hu.bme.aut.webshop.alf2023javant.repository.ProductRepository;
import hu.bme.aut.webshop.alf2023javant.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemHealthCheckTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCategoryDatabaseConnection() {

        Category data = new Category();

        data.setName("testName");
        data.setDescription("testDescription");
        categoryRepository.save(data);


        Optional<Category> savedData = categoryRepository.findByName(data.getName());
        Assert.assertTrue(savedData.isPresent());
        Assert.assertEquals(data.getId(), savedData.get().getId());

        categoryRepository.delete(data);

        Optional<Category> deletedData = categoryRepository.findByName(data.getName());
        Assert.assertFalse(deletedData.isPresent());
    }

    @Test
    public void testProductDatabaseConnection() {

        Product data = new Product();

        data.setName("testName");
        data.setDescription("testDescription");
        productRepository.save(data);


        Optional<Product> savedData = productRepository.findByName(data.getName());
        Assert.assertTrue(savedData.isPresent());
        Assert.assertEquals(data.getId(), savedData.get().getId());

        productRepository.delete(data);

        Optional<Product> deletedData = productRepository.findByName(data.getName());
        Assert.assertFalse(deletedData.isPresent());
    }

    @Test
    public void testUserDatabaseConnection() {

        User data = new User();

        data.setName("testUsername");
        data.setEmail("testEmail");
        data.setPassword("testPassword");
        userRepository.save(data);


        Optional<User> savedData = userRepository.findByEmail(data.getEmail());
        Assert.assertTrue(savedData.isPresent());
        Assert.assertEquals(data.getId(), savedData.get().getId());

        userRepository.delete(data);

        Optional<User> deletedData = userRepository.findByEmail(data.getEmail());
        Assert.assertFalse(deletedData.isPresent());
    }
}

