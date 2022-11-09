package consumer.services;

import consumer.contracts.CreateProduct;
import consumer.contracts.ProductResponse;
import consumer.exceptions.InvalidProductIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductsFetcher {
    private final RestTemplate restTemplate;

    public ProductResponse[] getAllProducts() {
        return restTemplate.getForEntity("/api/products", ProductResponse[].class).getBody();
    }

    public ProductResponse getProduct(UUID id) throws InvalidProductIdException {
        try {
            return restTemplate.getForEntity("/api/products/" + id, ProductResponse.class).getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new InvalidProductIdException(id);
            }

            throw e;
        }
    }

    public ProductResponse createProduct(String name, String description, double price) {
        CreateProduct createProduct = new CreateProduct(name, description, price);

        return restTemplate.postForEntity("/api/products", createProduct, ProductResponse.class).getBody();
    }

    public ProductResponse getProductByIndex(int index) {
        try {
            return restTemplate.getForEntity("/api/products/find/" + index, ProductResponse.class).getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                throw new RuntimeException("The product at index " + index + " does not exist");
            }

            throw e;
        }
    }
}
