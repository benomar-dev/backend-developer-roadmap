# Phase 4 — Advanced Practices (Months 18–24)

## 🎯 Goals

By the end of this phase you will be able to:
- Design and implement microservices architectures
- Apply advanced testing strategies (TDD, BDD, performance testing)
- Monitor distributed systems with ELK, Prometheus, and Grafana
- Work confidently in an Agile/Scrum team
- Write clean, maintainable, and SOLID code

---

## 📚 Topics & Checklist

### 🏗️ Architecture

#### Microservices
- [ ] **Service boundaries** — identify bounded contexts (DDD)
- [ ] **Inter-service communication** — REST (sync) vs Messaging (async)
- [ ] **API Gateway Pattern** — single entry point (Spring Cloud Gateway)
- [ ] **Service Discovery** — Eureka, Consul
- [ ] **Circuit Breaker Pattern** — Resilience4j (`@CircuitBreaker`, `@Retry`)
- [ ] **Saga Pattern** — distributed transactions without 2PC

#### Event-Driven Architecture
- [ ] **Apache Kafka** — topics, producers, consumers, consumer groups
- [ ] **RabbitMQ** — exchanges, queues, routing keys
- [ ] **Spring Cloud Stream** — abstraction over Kafka/RabbitMQ
- [ ] **Outbox Pattern** — reliable event publishing

#### CQRS & Event Sourcing
- [ ] **CQRS** — separate read and write models
- [ ] **Event Sourcing** — persist state as a sequence of events
- [ ] **Axon Framework** — CQRS/ES framework for Java

#### Domain-Driven Design (DDD)
- [ ] **Ubiquitous Language** — shared vocabulary between devs and domain experts
- [ ] **Aggregates** — consistency boundaries
- [ ] **Repositories** — collection-like interfaces for aggregates
- [ ] **Domain Events**

**Circuit Breaker example:**
```java
@Service
public class PaymentService {

    @CircuitBreaker(name = "paymentGateway", fallbackMethod = "fallbackPayment")
    @Retry(name = "paymentGateway", fallbackMethod = "fallbackPayment")
    public PaymentResult processPayment(PaymentRequest request) {
        return externalGateway.charge(request);
    }

    public PaymentResult fallbackPayment(PaymentRequest request, Exception ex) {
        log.warn("Payment gateway unavailable, queuing payment: {}", ex.getMessage());
        paymentQueue.queue(request);
        return PaymentResult.queued(request.orderId());
    }
}
```

---

### 🧪 Testing

- [ ] **JUnit 5** — `@Test`, `@BeforeEach`, `@AfterEach`, `@ParameterizedTest`
- [ ] **Mockito** — `@Mock`, `@InjectMocks`, `when(...).thenReturn(...)`, `verify(...)`
- [ ] **Spring Boot Test** — `@SpringBootTest`, `@WebMvcTest`, `@DataJpaTest`
- [ ] **TestContainers** — real DB in Docker for integration tests
- [ ] **Test-Driven Development (TDD)** — Red → Green → Refactor cycle
- [ ] **BDD with Cucumber** — Gherkin `Given/When/Then` scenarios
- [ ] **JMeter / Gatling** — load and performance testing
- [ ] **JaCoCo** — code coverage reports
- [ ] **Mutation Testing with PIT** — measure test quality

**TDD example (Red → Green → Refactor):**
```java
// 1. RED — write a failing test first
@Test
void shouldCalculateDiscountForPremiumCustomer() {
    DiscountService service = new DiscountService();
    double price = 100.0;

    double discounted = service.apply(price, CustomerType.PREMIUM);

    assertThat(discounted).isEqualTo(85.0);  // 15% discount
}

// 2. GREEN — implement just enough to pass
public class DiscountService {
    public double apply(double price, CustomerType type) {
        return type == CustomerType.PREMIUM ? price * 0.85 : price;
    }
}

// 3. REFACTOR — improve design while keeping tests green
```

**TestContainers integration test:**
```java
@SpringBootTest
@Testcontainers
class UserRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUser() {
        User user = new User("alice@example.com", "Alice");
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("alice@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Alice");
    }
}
```

---

### 📊 Monitoring & Observability

- [ ] **SLF4J + Logback** — structured logging
- [ ] **Structured logs (JSON)** — Logstash encoder
- [ ] **ELK Stack** — Elasticsearch + Logstash + Kibana
- [ ] **Prometheus** — time-series metrics scraping
- [ ] **Grafana** — dashboards and alerting
- [ ] **Distributed Tracing** — Zipkin / Jaeger (OpenTelemetry)
- [ ] **Micrometer** — Spring Boot metrics integration
- [ ] **Alerting** — alert when error rate exceeds threshold

**application.yml for Micrometer + Prometheus:**
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  metrics:
    export:
      prometheus:
        enabled: true
    tags:
      application: ${spring.application.name}
      environment: ${spring.profiles.active}
```

**Structured logging:**
```java
@Slf4j
@Service
public class OrderService {

    public Order createOrder(CreateOrderRequest request) {
        log.info("Creating order for customer={} items={}",
                 request.customerId(), request.items().size());
        // ...
        log.info("Order created orderId={} customerId={}",
                 order.getId(), request.customerId());
        return order;
    }
}
```

---

### 🏃 Agile Methodology

#### Scrum
- [ ] **Sprints** — 1-4 week iterations
- [ ] **Daily Standup** — What did I do? What will I do? Any blockers?
- [ ] **Sprint Planning** — select items from Product Backlog
- [ ] **Sprint Review** — demo to stakeholders
- [ ] **Sprint Retrospective** — improve the process
- [ ] **Product Backlog** — prioritised list of work
- [ ] **User Stories** — "As a [user], I want [feature], so that [benefit]"
- [ ] **Story Points** — relative effort estimation
- [ ] **Definition of Done (DoD)** — criteria for "finished"

#### Tools
- [ ] **Jira** — issue tracking and sprint management
- [ ] **Confluence** — team wiki and documentation

---

### ✨ Clean Code & SOLID

#### SOLID Principles
- [ ] **S** — Single Responsibility Principle: one reason to change
- [ ] **O** — Open/Closed Principle: open for extension, closed for modification
- [ ] **L** — Liskov Substitution: subtypes must be substitutable
- [ ] **I** — Interface Segregation: prefer small focused interfaces
- [ ] **D** — Dependency Inversion: depend on abstractions, not concretions

#### Design Patterns (GoF)

**Creational:**
- [ ] Singleton — single instance (use sparingly; prefer Spring beans)
- [ ] Factory Method — create objects without specifying exact class
- [ ] Builder — construct complex objects step by step

**Structural:**
- [ ] Adapter — make incompatible interfaces work together
- [ ] Decorator — add behaviour dynamically
- [ ] Proxy — control access to an object

**Behavioral:**
- [ ] Observer — event/listener pattern
- [ ] Strategy — algorithm family, interchangeable at runtime
- [ ] Template Method — define skeleton, let subclasses fill in steps

**Builder pattern example:**
```java
// Instead of new User(name, email, age, address, phone, ...)
User user = User.builder()
    .name("Alice")
    .email("alice@example.com")
    .age(30)
    .city("Paris")
    .build();
```

#### Other Practices
- [ ] **DRY** — Don't Repeat Yourself
- [ ] **KISS** — Keep It Simple, Stupid
- [ ] **YAGNI** — You Aren't Gonna Need It
- [ ] **Code Smells & Refactoring** — long methods, feature envy, god classes
- [ ] **SonarQube** — static analysis and technical debt tracking
- [ ] **OWASP Top 10** — SQL Injection, XSS, broken auth, etc.

---

## 🗂️ Directory Structure (to be populated)

```
phase4-pratiques-avancees/
├── 01-architecture/         # Microservices, Event-Driven demos
├── 02-testing/              # JUnit5, Mockito, TestContainers examples
├── 03-monitoring/           # Prometheus, Grafana, ELK configs
├── 04-design-patterns/      # GoF pattern implementations
└── 05-clean-code/           # SOLID, refactoring exercises
```

---

## 📚 Resources

- [Resilience4j Docs](https://resilience4j.readme.io/)
- [TestContainers Java](https://testcontainers.com/guides/getting-started-with-testcontainers-for-java/)
- [Micrometer Docs](https://micrometer.io/docs)
- [Martin Fowler — Microservices](https://martinfowler.com/articles/microservices.html)
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)

---

**⏱️ Estimated duration: 6 months (2–3h/day)**
