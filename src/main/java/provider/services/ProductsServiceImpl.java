package provider.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import provider.contracts.CreateProduct;
import provider.contracts.ProviderProductResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private List<ProviderProductResponse> products = List.of(
            new ProviderProductResponse(UUID.randomUUID(), "product1", "description1", 1, "store 1", 55),
            new ProviderProductResponse(UUID.randomUUID(), "product2", "description2", 2, "store 2", 66),
            new ProviderProductResponse(UUID.randomUUID(), "product3", "description3", 3, "store 3", 77)
    );

    @Override
    public List<ProviderProductResponse> getProducts() {
        return products;
    }

    @Override
    public Optional<ProviderProductResponse> getProduct(UUID id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public ProviderProductResponse createProduct(CreateProduct createProduct) {
        ProviderProductResponse productResponse = new ProviderProductResponse(UUID.randomUUID(), createProduct.getName(),
                createProduct.getDescription(), createProduct.getPrice(), null, 0);

        products.add(productResponse);
        return productResponse;
    }
}
