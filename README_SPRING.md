# Spring Boot quick tutorial

## Introduction

This tutorial is a quick introduction to Spring Boot. It is based on
the [official documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-first-application)
.

## REST

REST is a way of providing interoperability between computer systems on the web, making it easier for systems to
communicate with each other.

For a request, we set up a verb (the operation that we want to perform), a path (the resource that we want to access),
some headers and a request body.

The usual verbs are:

- GET - retrieve a specific resource (by id) or a collection of resources
- POST - create a new resource
- PUT - update a specific resource (by id)
- DELETE - remove a specific resource by id

The body usually is in JSON format.

The response has a status code, some headers and a response body. The status code is a number that indicates the result
of the request, for example if it was successful or had some errors.

More information can be found [here](https://www.codecademy.com/article/what-is-rest)

## Spring Boot

### What is Spring

Spring is a framework for Java applications. It is a collection of modules that can be used to build a Java application.
Spring Boot is a framework that makes it easy to create stand-alone Spring based Applications that are easy to configure
and run.

We usually use Spring Boot to create REST APIs. That means that we will create a server that will listen to requests and
will return responses, usually in JSON format.

### Dependencies

The tutorial requires the following dependencies:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>2.7.5</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <version>2.7.5</version>
    <scope>test</scope>
</dependency>
```

## Controllers

The controllers are the classes that will handle the requests. The webserver will listen for incoming requests and will
call the appropriate controller method.

To create a controller we need to add the `@RestController` annotation to the class. This annotation will tell Spring
that this class is a controller. We set up the main path for the controller by adding the `@RequestMapping` annotation
to the class.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {
}
```

## Endpoints

The endpoints are the methods that will handle the requests. We set up the path for the endpoint by adding the
`@RequestMapping` annotation to the method. We also need to specify the HTTP verb that we want to use.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @GetMapping
    public String getProducts() {
        return "Hello world";
    }
}
```

When the webserver receives a request to the path `/api/products` with the GET verb, it will call the `getProducts`
method and return the string "Hello world".

To add a different endpoint, we can add a new method with a different path and verb.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @PutMapping("values")
    public String putProducts() {
        return "Hello world";
    }
}
```

When the webserver receives a request to the path `/api/products/values` with the PUT verb, it will call
the `putProducts` method and return the string "Hello world".

## Request parameters

### Request Body

We can also send some information to the controller in the request body. We can access this information by adding a
parameter to the method. The parameter will be automatically populated with the information from the request body. The
parameter needs to be annotated with the `@RequestBody` annotation.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @PutMapping("values")
    public String putProducts(@RequestBody String body) {
        return "Hello world";
    }
}
```

Although in the HTTP protocol the body is string, because we send it in JSON format, Spring will automatically convert
it to the appropriate object.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @PutMapping("values")
    public String putProducts(@RequestBody Product product) {
        return "Hello world";
    }
}
```

### Path variables

We can also send some information to the controller in the path. We can access this information by adding a parameter to
the method. The parameter will be automatically populated with the information from the path. The parameter needs to be
annotated with the `@PathVariable` annotation.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @PutMapping("{id}")
    public String putProducts(@PathVariable String id) {
        return "Hello world";
    }
}
```

Let's say that we send a request to the path `/api/products/123`. The `id` parameter will be populated with the
value `123` and the method `putProducts` will be called. This construct is very useful when we want to parameterize the
path.

We can also add multiple parameters to the path.

```java 
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @PutMapping("values/{id}/{name}")
    public String putProducts(@PathVariable String id, @PathVariable String name) {
        return "Hello world";
    }
}
```

### Query parameters

We can also send some information to the controller in the query. We can access this information by adding a parameter
to the method. The parameter will be automatically populated with the information from the query. The parameter needs to
be annotated with the `@RequestParam` annotation.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @PutMapping("values")
    public String putProducts(@RequestParam String id) {
        return "Hello world";
    }
}
```

Let's say that we send a request to the path `/api/products/values?id=123`. The `id` parameter will be populated with
the value `123` and the method `putProducts` will be called.

## Response

We can return a response from the controller by adding a return statement to the method. The return statement will be
automatically converted to a JSON string and sent as a response of the endpoint.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @PostMapping()
    public Product postProducts() {
        return new Product("123", "Product 1");
    }
}
```

### Response status

The default response status is 200 OK. Sometimes we want to return a different status code for the response to indicate
that something went wrong. We can do this by using the `ResponseEntity` class.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @GetMapping("{id}")
    public ResponseEntity<Product> postProducts(@PathVariable String id) {
        return ResponseEntity.notFound().build();
    }
}
```

If an exception is thrown in the controller and is not caught, the response status will be 500 Internal Server Error.

## Services

Services are the classes that will handle the business logic. The controllers will call the services in order to process
the requests and return the response.

To create a service we need to add the `@Service` annotation to the class. This annotation will tell Spring that this
class is a service.

```java
@Service
public class ProductsService {
}
```

In order for the controller to be able to call the service, we need to inject it in the controller. We can do this by
adding a field to the controller and adding the `@Autowired` annotation to it. The initialization of the field will be
done automatically by Spring.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;
}
```

Another approach is to add a constructor with the service as a parameter. When the controller is initialized, Spring
will automatically call the constructor and initialize the field.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Product> postProducts(@PathVariable String id) {
        return productsService.getProduct(id);
    }
}
```

## RestTemplate

The `RestTemplate` class is used to send HTTP requests to other services.

### Get

To send a GET request we can use the `getForEntity` method.

```java
ResponseEntity<Product> response = restTemplate.getForEntity("http://localhost:8080/api/products/1", Product.class);
Product product = response.getBody();
```

To send a GET request for an array of objects we can use the `getForEntity` method.

```java
ResponseEntity<Product[]> response = restTemplate.getForEntity("http://localhost:8080/products", Product[].class);
Product[] products = response.getBody();
```

### Post

To send a POST request we can use the `postForEntity` method.

```java
Product newProduct = new Product("123", "Product 1");
ResponseEntity<ProductResponse> response = restTemplate.postForEntity("http://localhost:8080/api/products", newProduct, ProductResponse.class);
ProductResponse productResponse = response.getBody();
```

## Testing

Testing springboot application components can be done using mockito and junit, but sometimes we want to test the whole
application. For this we can use the `@SpringBootTest` annotation.

```java
@SpringBootTest
public class ProductsControllerTest {

    @Autowired
    private ProductsController productsController;

    @Test
    public void testGetProducts() {
        ResponseEntity<Product> response = productsController.getProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
```

The code above will start the application like it would start from the command line. It will create the controller and
initialize it with all its dependencies. We can then test the controller methods by calling them directly.

> The `@SpringBootTest` annotation will start the webserver on a port and be as close as possible to the real application.

We can customize the port that the webserver will use by specifying some parameters in the `@SpringBootTest` annotation.
If we want to know which port the webserver will use, we can use the `@LocalServerPort` annotation on an integer field.
When the test starts, that field will be populated with the port that the webserver will use.

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ProductsControllerTest {
    @LocalServerPort
    private int serverPort;
}
```

## Lombok

Lombok is a library that allows to generate boilerplate code. Lombok is used to reduce the amount of code that we need
to write.

### Setup

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.24</version>
    <scope>provided</scope>
</dependency>
```

### @AllArgsConstructor

When the annotation `@AllArgsConstructor` is added to a class, a constructor with all the fields as parameters will be
generated.

```java
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
}
```

```java
public class Product {
    private String id;
    private String name;

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

### @NoArgsConstructor

When the annotation `@NoArgsConstructor` is added to a class, a constructor with no parameters will be generated.

```java
@NoArgsConstructor
public class Product {
    private String id;
    private String name;
}
```

```java
public class Product {
    private String id;
    private String name;

    public Product() {
    }
}
```

### @RequiredArgsConstructor

When the annotation `@RequiredArgsConstructor` is added to a class, a constructor with all the final fields as
parameters will be generated.

```java
@RequiredArgsConstructor
public class Product {
    private final String id;
    private String name;
}
```

```java
public class Product {
    private final String id;
    private String name;

    public Product(String id) {
        this.id = id;
    }
}
```

### @Getter

When the annotation `@Getter` is added to a class, a getter method will be generated for each field.

```java
@Getter
public class Product {
    private String id;
    private String name;
}
```

```java
public class Product {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
```

### @Setter

When the annotation `@Setter` is added to a class, a setter method will be generated for each field.

```java
@Setter
public class Product {
    private String id;
    private String name;
}
```

```java
public class Product {
    private String id;
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### @ToString

When the annotation `@ToString` is added to a class, a `toString` method will be generated.

```java
@ToString
public class Product {
    private String id;
    private String name;
}
```

```java
public class Product {
    private String id;
    private String name;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
```

### @Data

When the annotation `@Data` is added to a class, the same behavior as the `@Getter`, `@Setter`, `@ToString`,
`@EqualsAndHashCode` and `@RequiredArgsConstructor` annotations will be generated.

```java
@Data
public class Product {
    private final String id;
    private String name;
}
```

```java
public class Product {
    private final String id;
    private String name;

    public Product(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
```

### @Builder

When the annotation `@Builder` is added to a class, a builder will be generated.

```java
@Builder
public class Product {
    private String id;
    private String name;
}
```

```java
public class Product {
    private String id;
    private String name;

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private String id;
        private String name;

        ProductBuilder() {
        }

        public ProductBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Product build() {
            return new Product(id, name);
        }

        public String toString() {
            return "Product.ProductBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
```

## Resources

- https://www.codecademy.com/article/what-is-rest
- https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-first-application
- https://www.baeldung.com/spring-boot-starters
- https://www.baeldung.com/spring-boot-testing
- https://projectlombok.org/features/all
- https://www.baeldung.com/rest-template
- https://www.baeldung.com/spring-rest-template-list