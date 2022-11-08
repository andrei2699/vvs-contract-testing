package consumer;

import consumer.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MainProductsConsumer implements CommandLineRunner {

    @Autowired
    private ProductsService productsService;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MainProductsConsumer.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }

    @Override
    public void run(String... args) {
        List<String> productNames = productsService.getProductNames();
        System.out.println(productNames);

        productsService.createProduct("new Product", "brand new and expensive product", 100);
        System.out.println(productsService.getProductNames());

    }
}
