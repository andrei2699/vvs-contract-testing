package consumer;

import consumer.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class MainCustomersConsumer implements CommandLineRunner {

    @Autowired
    private ProductsService productsService;

    public static void main(String[] args) {
        SpringApplication.run(MainCustomersConsumer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> productNames = productsService.getProductNames();
        System.out.println(productNames);
    }
}
