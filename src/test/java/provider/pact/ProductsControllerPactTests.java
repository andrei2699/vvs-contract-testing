package provider.pact;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import provider.MainProductsProvider;
import provider.services.ProductsService;
import provider.contracts.ProviderProductResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@PactFolder("pacts")
@Provider("Products Application")
@SpringBootTest(classes = MainProductsProvider.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsControllerPactTests {
    @MockBean
    private ProductsService productsService;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void setupTestTarget(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", serverPort));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("existing products")
    void existingProduct() {
        ProviderProductResponse product = new ProviderProductResponse(UUID.randomUUID(), "Product 1", "Product 1 description", 20, "store 1", 1);

        when(productsService.getProducts()).thenReturn(List.of(product));
    }

    @State("one product exists")
    void oneProductExists() {
        ProviderProductResponse product = new ProviderProductResponse(UUID.randomUUID(), "Product 2", "Product 2 description", 44, "store 2", 2);

        when(productsService.getProduct(any())).thenReturn(Optional.of(product));
    }

    @State("one product does not exist")
    void oneProductDoesNotExist() {
        when(productsService.getProduct(any())).thenReturn(Optional.empty());
    }

    @State("one create product")
    void oneCreateProduct() {
        ProviderProductResponse product = new ProviderProductResponse(UUID.randomUUID(), "Product 1", "Product 1 description", 11, "store 1", 1);

        when(productsService.createProduct(any())).thenReturn(product);
    }

    @State("get product by index existing")
    void getProductByIndexExists() {
        ProviderProductResponse product = new ProviderProductResponse(UUID.randomUUID(), "Product 1", "Product 1 description", 11, "store 1", 1);

        when(productsService.getProductByIndex(anyInt())).thenReturn(product);
    }

    @State("get product by index non existing")
    void getProductByIndexNotExists() {
        when(productsService.getProductByIndex(anyInt())).thenThrow(new IndexOutOfBoundsException());
    }
}
