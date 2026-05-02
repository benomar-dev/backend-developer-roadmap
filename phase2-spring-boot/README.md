# Phase 2 — Spring Boot & APIs (Months 6–12)

## 🎯 Goals

By the end of this phase you will be able to:
- Build production-ready REST APIs with Spring Boot
- Implement authentication & authorisation with JWT and OAuth2
- Integrate with relational databases using Spring Data JPA
- Document APIs with Swagger/OpenAPI
- Write unit and integration tests

---

## 📚 Topics & Checklist

### 🌱 Spring Framework Core

- [ ] **Inversion of Control (IoC)** — Spring manages object lifecycle
- [ ] **Dependency Injection (DI)** — constructor injection (preferred), field injection, setter injection
- [ ] **Spring Beans & ApplicationContext** — `@Component`, `@Service`, `@Repository`, `@Controller`
- [ ] **Aspect-Oriented Programming (AOP)** — `@Aspect`, `@Before`, `@After`, `@Around`
- [ ] **Spring Configuration** — `@Configuration`, `@Bean`, `@Import`, profiles

**Practical exercises:**
```java
// Constructor injection (preferred over field injection)
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

---

### 🚀 Spring Boot

- [ ] **Spring Initializr** — generate project at [start.spring.io](https://start.spring.io)
- [ ] **Auto-configuration** — how `@SpringBootApplication` works
- [ ] **Starters** — spring-boot-starter-web, -data-jpa, -security, -test
- [ ] **application.properties / application.yml** — configuring your app
- [ ] **Profiles** — `@Profile("dev")`, `spring.profiles.active=prod`
- [ ] **Spring Boot Actuator** — `/actuator/health`, `/actuator/info`, `/actuator/metrics`
- [ ] **DevTools** — live reload during development

**Key configuration example:**
```yaml
# application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: ${DB_USER}
    password: ${DB_PASS}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

server:
  port: 8080

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

---

### 🌐 RESTful APIs

- [ ] **HTTP Methods** — GET (read), POST (create), PUT (replace), PATCH (partial update), DELETE
- [ ] **`@RestController` & `@RequestMapping`**
- [ ] **`@PathVariable` & `@RequestParam`**
- [ ] **`@RequestBody` & `ResponseEntity<T>`**
- [ ] **HTTP Status Codes** — 200, 201, 204, 400, 401, 403, 404, 409, 500
- [ ] **`@ControllerAdvice` + `@ExceptionHandler`** — global error handling
- [ ] **Validation** — `@Valid`, `@NotNull`, `@Size`, `@Email`, `@Min`, `@Max`
- [ ] **HATEOAS** — hypermedia links in responses
- [ ] **API Versioning** — URI (`/v1/users`), header, media type
- [ ] **OpenAPI / Swagger** — `springdoc-openapi` dependency
- [ ] **Pagination** — `Pageable`, `Page<T>`, `@PageableDefault`
- [ ] **CORS** — `@CrossOrigin` or global `CorsConfiguration`

**Controller skeleton:**
```java
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserResponse> getAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        UserResponse created = userService.create(request);
        URI location = URI.create("/api/v1/users/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Global exception handler:**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();
        return ResponseEntity.badRequest().body(new ErrorResponse(400, errors.toString()));
    }
}
```

---

### 💾 Spring Data JPA

- [ ] **JPA & Hibernate basics** — ORM concept, entity lifecycle
- [ ] **`@Entity`, `@Table`, `@Id`, `@GeneratedValue`**
- [ ] **Relationships** — `@OneToMany`, `@ManyToOne`, `@ManyToMany`, `@OneToOne`
- [ ] **`JpaRepository` & `CrudRepository`**
- [ ] **Query methods** — `findByEmailAndActive`, `findByNameContaining`
- [ ] **`@Query` + JPQL**
- [ ] **Native queries** — `@Query(nativeQuery = true)`
- [ ] **`@Transactional`** — propagation, isolation levels
- [ ] **Lazy vs Eager loading** — N+1 problem and how to fix it with `JOIN FETCH`
- [ ] **Cascade types** — `CascadeType.ALL`, `PERSIST`, `MERGE`, `REMOVE`
- [ ] **Flyway or Liquibase** — database migrations

**Entity example:**
```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    // constructors, getters, setters...
}
```

---

### 🔒 Spring Security

- [ ] **Authentication vs Authorisation**
- [ ] **`UserDetailsService`** — load user from database
- [ ] **Password encoding** — `BCryptPasswordEncoder`
- [ ] **JWT** — issuing, validating, refreshing tokens
- [ ] **OAuth2 / OpenID Connect** — social login, Keycloak
- [ ] **Method security** — `@PreAuthorize("hasRole('ADMIN')")`
- [ ] **CSRF protection** — when to enable/disable
- [ ] **Session management** — stateless vs stateful

**JWT filter skeleton:**
```java
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            // validate token, set SecurityContext
        }
        chain.doFilter(request, response);
    }
}
```

---

### 🗃️ Databases

- [ ] **PostgreSQL** — primary relational DB
- [ ] **MySQL** — common alternative
- [ ] **MongoDB** — document database (`spring-boot-starter-data-mongodb`)
- [ ] **Redis** — caching and sessions (`spring-boot-starter-data-redis`)
- [ ] **Schema design** — normalisation, indexes
- [ ] **Connection pooling** — HikariCP (default in Spring Boot)
- [ ] **Transactions ACID** — Atomicity, Consistency, Isolation, Durability

---

## 🗂️ Directory Structure (to be populated)

```
phase2-spring-boot/
├── 01-spring-core/          # IoC, DI, Beans demos
├── 02-rest-api/             # Basic CRUD REST API project
├── 03-spring-data-jpa/      # Entities, repositories, queries
├── 04-spring-security-jwt/  # Auth with JWT
├── 05-openapi-swagger/      # API documentation
└── 06-testing/              # Unit + integration tests
```

---

## 📚 Resources

- [Spring Boot Reference Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Guides](https://spring.io/guides)
- [Baeldung Spring Tutorials](https://www.baeldung.com/spring-tutorial)
- [Spring Initializr](https://start.spring.io/)
- [springdoc-openapi](https://springdoc.org/)

---

**⏱️ Estimated duration: 6 months (3h/day)**
