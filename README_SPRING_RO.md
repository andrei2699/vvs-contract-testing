# Spring Boot tutorial

## Introducere

Acest tutorial este o introducere pentru Spring Boot. Este bazat
pe [documentația oficială](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-first-application)
.

## REST

REST este un mod de a oferi interoperabilitate între sistemele de calcul de pe web, facând mai ușor pentru sistemele de
a comunica între ele.

Pentru un request, setăm un verb (operatia pe care vrem sa o efectuam), un path (resursa pe care vrem sa o accesam),
câteva header-uri și un request body.

Verbele obișnuite sunt:

- GET - pentru a citi o resursa (cu id) sau lista de resurse
- POST - pentru a crea o resursa
- PUT - pentru a actualiza o resursa (cu id)
- DELETE - pentru a șterge o resursa (cu id)

Request body-ul este de obicei in formatul JSON.

Raspunsul are un cod de status, cateva header-uri si un body. Codul de status este un numar care indica rezultatul
request-ului, de exemplu daca a fost un succes sau a avut erori.

Mai multe informatii pot fi gasite [aici](https://www.codecademy.com/article/what-is-rest).

## Spring Boot

### What is Spring

Spring este un framework pentru aplicatii Java. Este o colectie de module care pot fi folosite pentru a construi o
aplicatie Java. Spring Boot este un framework care face mai usor sa cream aplicatii Spring standalone care sunt usor de
configurat si rulate.

Uzual folosim Spring Boot pentru a crea REST APIs. Asta inseamna ca vom crea un server care va asculta request-uri si va
returna raspunsuri, de obicei in format JSON.

### Dependinte

Tutorialul necesita urmatoarele dependinte:

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

Controller-ul este o clasa care va gestiona request-urile. Server-ul web va asculta request-uri si va apela metoda
corespunzatoare din controller.

Pentru a crea un controller, adaugam anotatia `@RestController` la clasa. Aceasta anotare va spune Spring ca aceasta
clasa este un controller. Setam path-ul principal pentru controller prin adaugarea anotarii `@RequestMapping` la clasa.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {
}
```

## Endpoints

Endpoint-urile sunt metodele care vor gestiona request-urile. Setam path-ul pentru endpoint prin adaugarea anotarii
`@RequestMapping` la metoda. De asemenea, trebuie sa specificam verbul HTTP pe care vrem sa il folosim.

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

Cand server-ul web primeste un request la path-ul `/api/products` cu verbul GET, va apela metoda `getProducts` si va
returna string-ul "Hello world".

Pentru a adauga un alt endpoint, putem adauga o noua metoda in controller.

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

Cand serverul web primeste un request la path-ul `/api/products/values` cu verbul PUT, va apela metoda `putProducts` si
va returna string-ul "Hello world".

## Request parameters

### Request Body

Putem trimite si alte informatii la controller prin request body. Putem accesa aceste informatii prin adaugarea unui
parametru la metoda. Parametrul va fi automat populat cu informatiile din request body. Parametrul trebuie sa fie
annotat cu `@RequestBody`.

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

Desi in protocolul HTTP body-ul este un string, deoarece il trimitem in format JSON, Spring va converti automat
string-ul in obiectul corespunzator.

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

Putem trimite si alte informatii la controller prin path-ul request-ului. Putem accesa aceste informatii prin adaugarea
unui parametru la metoda. Parametrul va fi automat populat cu informatiile din path-ul request-ului. Parametrul trebuie
sa fie annotat cu `@PathVariable`.

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

Sa presupunem ca trimitem un request la path-ul `/api/products/123`. Parametrul `id` va fi populat cu valoarea `123` si
metoda `putProducts` va fi apelata. Aceasta constructie este foarte utila cand vrem sa parametrizam path-ul.

Putem adauga si mai multe parametri in path.

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

Putem trimite si alte informatii la controller prin query-ul request-ului. Putem accesa aceste informatii prin adaugarea
unui parametru la metoda. Parametrul va fi automat populat cu informatiile din query-ul request-ului. Parametrul trebuie
sa fie annotat cu `@RequestParam`.

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

Sa presupunem ca trimitem un request la path-ul `/api/products/values?id=123`. Parametrul `id` va fi populat cu valoarea
`123` si metoda `putProducts` va fi apelata.

## Response

Putem returna un raspuns din controller prin adaugarea unei instructiuni de return in metoda. Instructiunea de return va
fi convertita automat in string JSON si va fi trimisa ca raspuns la endpoint.

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

Statusul implicit al raspunsului este 200 OK. Uneori vrem sa returnam un alt cod de status pentru a indica ca ceva a
mers gresit. Putem face asta folosind clasa `ResponseEntity`.

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

Daca o exceptie este aruncata in controller si nu este prinsa, statusul raspunsului va fi 500 Internal Server Error.

## Services

Serviciile sunt clasele care vor gestiona logica de business. Controller-urile vor apela serviciile pentru a procesa
request-urile si pentru a returna raspunsurile.

Pentru a crea un serviciu, trebuie sa adaugam anotatia `@Service` la clasa. Aceasta anotare va spune Spring-ului ca
clasa este un serviciu.

```java
@Service
public class ProductsService {
}
```

Pentru a putea apela serviciul din controller, trebuie sa il injectam in controller. Putem face asta prin adaugarea unui
camp la controller si prin adaugarea anotarii `@Autowired` la camp. Initializarea campului va fi facuta automat de
Spring.

```java
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;
}
```

O alta abordare este sa adaugam un constructor cu serviciul ca parametru. Cand controller-ul este initializat, Spring va
apela automat constructorul si va initializa campul.

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

Clasa `RestTemplate` este folosita pentru a trimite request-uri HTTP catre alte servicii.

### Get

Pentru a trimite un request GET putem folosi metoda `getForEntity`.

```java
ResponseEntity<Product> response = restTemplate.getForEntity("http://localhost:8080/api/products/1", Product.class);
Product product = response.getBody();
```

Pentru a trimite un request GET pentru un array de obiecte putem folosi metoda `getForEntity`.

```java
ResponseEntity<Product[]> response = restTemplate.getForEntity("http://localhost:8080/products", Product[].class);
Product[] products = response.getBody();
```

### Post

Pentru a trimite un request POST putem folosi metoda `postForEntity`.

```java
Product newProduct = new Product("123", "Product 1");
ResponseEntity<ProductResponse> response = restTemplate.postForEntity("http://localhost:8080/api/products", newProduct, ProductResponse.class);
ProductResponse productResponse = response.getBody();
```

## Testing

Testarea aplicatiei SpringBoot poate fi facuta folosind mockito si junit, dar uneori vrem sa testam si aplicatia
intreaga. Pentru asta putem folosi anotatia `@SpringBootTest`.

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

Codul de mai sus va porni aplicatia ca si cum ar fi pornita de la linia de comanda. Va crea controller-ul si il va
initializa cu toate dependintele. Putem apoi testa metodele controller-ului prin apelarea lor directe.

> Anotarea `@SpringBootTest` va porni webserver-ul pe un port si va fi cat mai apropiata de aplicatia reala.

Putem configura portul pe care va rula webserver-ul prin specificarea unor parametri in anotatia `@SpringBootTest`. Daca
vrem sa aflam care port va fi folosit, putem folosi anotatia `@LocalServerPort` pe un camp de tip intreg. Cand testul va
porni, campul va fi populat cu portul pe care va rula webserver-ul.

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ProductsControllerTest {
    @LocalServerPort
    private int serverPort;
}
```

## Lombok

Lombok este o biblioteca care permite generarea codului boilerplate. Lombok este folosita pentru a reduce cantitatea de
cod pe care trebuie sa o scriem.

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

Cand adaugam anotarea `@AllArgsConstructor` la o clasa, un constructor cu toate campurile ca parametri va fi generat.

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

Cand adaugam anotarea `@NoArgsConstructor` la o clasa, un constructor fara parametri va fi generat.

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

Cand adaugam anotarea `@RequiredArgsConstructor` la o clasa, un constructor cu toate campurile finale ca parametri va fi
generat.

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

Cand adaugam anotarea `@Getter` la o clasa, o metoda getter va fi generata pentru fiecare camp.

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

Cand adaugam anotarea `@Setter` la o clasa, o metoda setter va fi generata pentru fiecare camp.

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

Cand adaugam anotarea `@ToString` la o clasa, o metoda `toString` va fi generata.

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

Cand adaugam anotarea `@Data` la o clasa, se va genera acelasi comportament ca si cand am adauga anotarile `@Getter`,
`@Setter`, `@ToString`, `@EqualsAndHashCode` si `@RequiredArgsConstructor`.

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

Cand adaugam anotarea `@Builder` la o clasa, un builder va fi generat.

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

## Resurse

- https://www.codecademy.com/article/what-is-rest
- https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-first-application
- https://www.baeldung.com/spring-boot-starters
- https://www.baeldung.com/spring-boot-testing
- https://projectlombok.org/features/all
- https://www.baeldung.com/rest-template
- https://www.baeldung.com/spring-rest-template-list