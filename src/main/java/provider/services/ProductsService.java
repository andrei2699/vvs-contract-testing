package provider.services;

import provider.contracts.CreateProduct;
import provider.contracts.ProviderProductResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductsService {
    List<ProviderProductResponse> getProducts();

    Optional<ProviderProductResponse> getProduct(UUID id);

    ProviderProductResponse createProduct(CreateProduct createProduct);

    ProviderProductResponse getProductByIndex(int index);
}
