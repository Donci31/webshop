package hu.bme.aut.webshop.alf2023javant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Alf2023JavanTApplication {
    private static final Logger logger = LoggerFactory.getLogger(Alf2023JavanTApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(Alf2023JavanTApplication.class, args);
    }
}
