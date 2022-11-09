package provider.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import provider.contracts.CreateProduct;
import provider.contracts.ProviderProductResponse;
import provider.services.ProductsService;

import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping()
    public List<ProviderProductResponse> getProducts() {
        return productsService.getProducts();
    }

    @GetMapping("{id}")
    public ResponseEntity<ProviderProductResponse> getProduct(@PathVariable UUID id) {
        return productsService.getProduct(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/find/{index}")
    public ProviderProductResponse getProductByIndex(@PathVariable int index) {
        return productsService.getProductByIndex(index);
    }

    @PostMapping()
    public ProviderProductResponse createProduct(@RequestBody CreateProduct createProduct) {
        return productsService.createProduct(createProduct);
    }
}
