package consumer.services;

import consumer.exceptions.InvalidProductIdException;
import consumer.contracts.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsFetcher productsFetcher;

    public List<String> getProductNames() {
        ProductResponse[] body = productsFetcher.getAllProducts();

        if (body == null) {
            return List.of();
        }

        return Arrays.stream(body)
                .map(ProductResponse::getName)
                .collect(Collectors.toList());
    }

    public String getProductName(UUID id) throws InvalidProductIdException {
        ProductResponse product = productsFetcher.getProduct(id);

        if (product == null) {
            return null;
        }

        return product.getName();
    }

    public ProductResponse createProduct(String name, String description, double price) {
        return productsFetcher.createProduct(name, description, price);
    }
}
