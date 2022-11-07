package consumer.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import consumer.contracts.ProductResponse;
import consumer.exceptions.InvalidProductId;
import consumer.services.ProductsFetcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "Products Application", pactVersion = PactSpecVersion.V3)
public class ProductsFetcherPactTests {

    private ProductsFetcher productsFetcher;

    @BeforeEach
    void setUp(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();
        productsFetcher = new ProductsFetcher(restTemplate);
    }

    @Pact(consumer = "Customer Application")
    public RequestResponsePact existingProducts(PactDslWithProvider builder) {
        return builder
                .given("existing products")
                .uponReceiving("a request for products")
                .path("/api/products")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonArray()
                        .object()
                        .uuid("id", UUID.fromString("01234567-0123-0123-0123-0123456789ab"))
                        .stringType("name", "Product 1")
                        .stringType("description", "Product 1 description")
                        .numberType("price", 1.0)
                )
                .toPact();
    }

    @Pact(consumer = "Customer Application")
    public RequestResponsePact oneProductExists(PactDslWithProvider builder) {
        return builder
                .given("one product exists")
                .uponReceiving("a request for a product")
                .path("/api/products/01234567-0123-0123-0123-0123456789ab")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .uuid("id", UUID.fromString("01234567-0123-0123-0123-0123456789ab"))
                        .stringType("name", "Product 1")
                        .stringType("description", "Product 1 description")
                        .numberType("price", 1.0))
                .toPact();
    }

    @Pact(consumer = "Customer Application")
    public RequestResponsePact oneProductDoesNotExist(PactDslWithProvider builder) {
        return builder
                .given("one product does not exist")
                .uponReceiving("a request for a product")
                .path("/api/products/01234567-0123-0123-0123-0123456789ab")
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Pact(consumer = "Customer Application")
    public RequestResponsePact createProduct(PactDslWithProvider builder) {
        return builder
                .given("one create product")
                .uponReceiving("a request to create a product")
                .path("/api/products")
                .method("POST")
                .body(new PactDslJsonBody()
                        .stringType("name", "Product 1")
                        .stringType("description", "Product 1 description")
                        .numberType("price", 1.0)
                        .getBody()
                        .toString())
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .uuid("id", UUID.fromString("01234567-0123-0123-0123-0123456789ab"))
                        .stringType("name", "Product 1")
                        .stringType("description", "Product 1 description")
                        .numberType("price", 1.0))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "existingProducts")
    void testExistingProducts() {
        ProductResponse[] productResponses = productsFetcher.getAllProducts();
        ProductResponse productResponse = productResponses[0];

        assertEquals(UUID.fromString("01234567-0123-0123-0123-0123456789ab"), productResponse.getId());
        assertEquals("Product 1", productResponse.getName());
        assertEquals("Product 1 description", productResponse.getDescription());
        assertEquals(1.0, productResponse.getPrice());
    }

    @Test
    @PactTestFor(pactMethod = "oneProductExists")
    void testOneProductExists() throws InvalidProductId {
        ProductResponse productResponse = productsFetcher.getProduct(UUID.fromString("01234567-0123-0123-0123-0123456789ab"));

        assertEquals(UUID.fromString("01234567-0123-0123-0123-0123456789ab"), productResponse.getId());
        assertEquals("Product 1", productResponse.getName());
        assertEquals("Product 1 description", productResponse.getDescription());
        assertEquals(1.0, productResponse.getPrice());
    }

    @Test
    @PactTestFor(pactMethod = "oneProductDoesNotExist")
    void testOneProductDoesNotExist() {
        assertThrows(InvalidProductId.class, () -> productsFetcher.getProduct(UUID.fromString("01234567-0123-0123-0123-0123456789ab")));
    }

    @Test
    @PactTestFor(pactMethod = "createProduct")
    void testCreateProduct() {
        ProductResponse productResponse = productsFetcher.createProduct("Product 1", "Product 1 description", 1.0);

        assertEquals(UUID.fromString("01234567-0123-0123-0123-0123456789ab"), productResponse.getId());
        assertEquals("Product 1", productResponse.getName());
        assertEquals("Product 1 description", productResponse.getDescription());
        assertEquals(1.0, productResponse.getPrice());
    }
}
