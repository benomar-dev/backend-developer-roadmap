# Interview Preparation Guide - Junior Spring Boot Developer in Spain

## 🎯 Complete Interview Preparation

This guide provides comprehensive preparation for technical interviews for Junior Spring Boot Developer positions in Spain.

---

## 📋 Table of Contents

1. [HR Interview Questions (Spanish & English)](#hr-interview-questions)
2. [Java Core Technical Questions](#java-core-technical-questions)
3. [Spring Boot Technical Questions](#spring-boot-technical-questions)
4. [Database & JPA Questions](#database--jpa-questions)
5. [REST API Questions](#rest-api-questions)
6. [Testing Questions](#testing-questions)
7. [Live Coding Exercises](#live-coding-exercises)
8. [System Design Questions (Basic)](#system-design-questions)
9. [Questions to Ask the Interviewer](#questions-to-ask-the-interviewer)

---

## 💼 HR Interview Questions

### Spanish Version / Versión Española

#### 1. Cuéntame sobre ti / Háblame de ti
**Sample Answer:**

"Soy un desarrollador backend junior apasionado por Java y Spring Boot. Durante mi formación en [Universidad], descubrí mi interés por el desarrollo backend mientras trabajaba en un proyecto de API REST. Desde entonces, he dedicado más de [X años/meses] a profundizar en Java, Spring Boot y tecnologías relacionadas.

He completado varios proyectos personales incluyendo [mencionar proyecto destacado], donde implementé [características clave]. Además, realicé prácticas en [Empresa] donde trabajé en [descripción breve].

Busco mi primera oportunidad profesional en España porque [razón: me atrae el ecosistema tech español / quiero trabajar en un entorno internacional / etc.]. Estoy especialmente interesado en trabajar con Spring Boot y contribuir a proyectos que tengan impacto real."

#### 2. ¿Por qué quieres trabajar en España?
**Sample Answer:**

"He elegido España por varias razones. Primero, el ecosistema tecnológico español, especialmente en Madrid/Barcelona, está creciendo rápidamente con muchas empresas innovadoras. Segundo, aprecio la cultura de trabajo-vida equilibrada que existe aquí. Tercero, estoy comprometido a aprender español y sumergirme en la cultura española. Ya tengo nivel [B1/B2] y continúo mejorando. Finalmente, veo muchas oportunidades de crecimiento profesional en el mercado tech español."

#### 3. ¿Por qué quieres trabajar en nuestra empresa?
**Sample Answer:**

"Me interesa [Nombre de la Empresa] por varias razones específicas. Primero, su trabajo en [mencionar proyecto/producto específico] me parece innovador porque [razón]. Segundo, he leído sobre su stack tecnológico que incluye [tecnologías] y estoy entusiasmado por trabajar con estas tecnologías. Tercero, su cultura de [mencionar aspecto cultural: aprendizaje continuo / innovación / trabajo en equipo] se alinea perfectamente con mis valores. Finalmente, veo que ofrecen [oportunidades de crecimiento / formación / etc.] lo cual es importante para mí en esta etapa de mi carrera."

#### 4. ¿Cuáles son tus expectativas salariales?
**Sample Answer:**

"Según mi investigación del mercado para posiciones junior de Spring Boot en Madrid/Barcelona, entiendo que el rango típico es de [X,000 - Y,000]€ anuales. Dada mi experiencia y habilidades, estaría cómodo con un salario dentro de este rango. Sin embargo, también valoro mucho otros aspectos como oportunidades de formación, ambiente de trabajo y crecimiento profesional. Estoy abierto a discutir un paquete completo que refleje tanto mi contribución actual como mi potencial de crecimiento."

#### 5. ¿Cuáles son tus fortalezas?
**Sample Answer:**

"Mi principal fortaleza es mi capacidad de aprendizaje rápido. Por ejemplo, cuando necesité aprender Spring Security para un proyecto, dominé los conceptos en dos semanas y lo implementé exitosamente.

Segunda, soy muy detallista con el código limpio. Siempre sigo principios SOLID y prácticas de clean code, lo que resulta en código mantenible.

Tercera, trabajo bien en equipo. En mi última experiencia participé activamente en code reviews y disfruté colaborar para resolver problemas.

Finalmente, soy proactivo. Si encuentro una mejor forma de hacer algo, propongo mejoras y tomo iniciativa."

#### 6. ¿Cuáles son tus debilidades?
**Sample Answer:**

"Una área donde estoy mejorando es la arquitectura de microservicios. Aunque entiendo los conceptos teóricos y he hecho un proyecto pequeño con Spring Cloud, reconozco que necesito más experiencia práctica en sistemas distribuidos a gran escala. Por eso, estoy estudiando actualmente [curso/libro] y busco oportunidades para trabajar en este tipo de proyectos.

Otra área es el cloud computing. He usado Heroku para despliegues básicos pero me falta experiencia con AWS o GCP. Sin embargo, ya comencé un curso de [AWS/GCP] para mejorar en este aspecto."

#### 7. ¿Dónde te ves en 5 años?
**Sample Answer:**

"En 5 años, me veo como un desarrollador backend senior con sólida experiencia en arquitectura de sistemas. Específicamente, quiero:

1. Dominar profundamente Spring Boot y el ecosistema Java
2. Tener experiencia práctica en microservicios y cloud computing
3. Ser capaz de liderar técnicamente proyectos pequeños
4. Contribuir activamente a decisiones arquitectónicas
5. Mentor de desarrolladores junior

También me gustaría obtener certificaciones relevantes como Spring Professional y [cloud certification]. Veo esta posición como el primer paso en ese camino."

---

### English Version

#### 1. Tell me about yourself
**Sample Answer:**

"I'm a junior backend developer passionate about Java and Spring Boot. During my studies at [University], I discovered my interest in backend development while working on a REST API project. Since then, I've dedicated over [X years/months] to deepening my knowledge in Java, Spring Boot, and related technologies.

I've completed several personal projects including [mention featured project], where I implemented [key features]. Additionally, I completed an internship at [Company] where I worked on [brief description].

I'm seeking my first professional opportunity in Spain because [reason: I'm attracted to the Spanish tech ecosystem / I want to work in an international environment / etc.]. I'm especially interested in working with Spring Boot and contributing to projects with real impact."

#### 2. Why do you want to work in Spain?
**Sample Answer:**

"I've chosen Spain for several reasons. First, the Spanish technology ecosystem, especially in Madrid/Barcelona, is growing rapidly with many innovative companies. Second, I appreciate the work-life balance culture that exists here. Third, I'm committed to learning Spanish and immersing myself in Spanish culture. I already have [B1/B2] level and continue improving. Finally, I see many opportunities for professional growth in the Spanish tech market."

#### 3. What are your salary expectations?
**Sample Answer:**

"Based on my market research for junior Spring Boot positions in Madrid/Barcelona, I understand the typical range is [X,000 - Y,000]€ annually. Given my experience and skills, I would be comfortable with a salary within this range. However, I also greatly value other aspects such as training opportunities, work environment, and professional growth. I'm open to discussing a complete package that reflects both my current contribution and growth potential."

---

## ☕ Java Core Technical Questions

### 1. What is the difference between `==` and `.equals()` in Java?

**Answer:**
- `==` compares **references** (memory addresses) - checks if two objects point to the same memory location
- `.equals()` compares **values** - checks if two objects are logically equal based on their content

```java
String s1 = new String("hello");
String s2 = new String("hello");
String s3 = s1;

System.out.println(s1 == s2);      // false (different objects in memory)
System.out.println(s1.equals(s2)); // true (same value)
System.out.println(s1 == s3);      // true (same reference)
```

### 2. Explain the difference between abstract class and interface

**Answer:**

| Abstract Class | Interface |
|---------------|-----------|
| Can have both abstract and concrete methods | All methods are abstract by default (until Java 8) |
| Can have instance variables | Only constants (static final) |
| Single inheritance only | Multiple inheritance supported |
| Can have constructors | Cannot have constructors |
| Use `extends` keyword | Use `implements` keyword |

**When to use:**
- **Abstract class**: When you have common functionality to share among related classes
- **Interface**: When you want to define a contract that unrelated classes can implement

### 3. What is the difference between ArrayList and LinkedList?

**Answer:**

| ArrayList | LinkedList |
|-----------|-----------|
| Backed by dynamic array | Doubly-linked list |
| Fast random access O(1) | Slow random access O(n) |
| Slow insertion/deletion O(n) | Fast insertion/deletion O(1) |
| Better for search operations | Better for frequent modifications |
| Less memory overhead | More memory (stores pointers) |

### 4. Explain Java Streams and provide an example

**Answer:**

Streams API (Java 8+) allows functional-style operations on collections.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

// Filter even numbers, multiply by 2, and collect
List<Integer> result = numbers.stream()
    .filter(n -> n % 2 == 0)      // Keep even numbers: [2, 4, 6]
    .map(n -> n * 2)               // Multiply by 2: [4, 8, 12]
    .collect(Collectors.toList()); // Collect to list

// result = [4, 8, 12]
```

**Key operations:**
- `filter()`: Select elements
- `map()`: Transform elements
- `reduce()`: Aggregate elements
- `collect()`: Convert to collection

### 5. What is the difference between checked and unchecked exceptions?

**Answer:**

**Checked Exceptions:**
- Must be caught or declared in method signature
- Checked at compile time
- Examples: `IOException`, `SQLException`
- Extend `Exception` class

```java
public void readFile() throws IOException {
    FileReader file = new FileReader("file.txt");
}
```

**Unchecked Exceptions:**
- Don't need to be caught or declared
- Checked at runtime
- Examples: `NullPointerException`, `ArrayIndexOutOfBoundsException`
- Extend `RuntimeException` class

```java
public void divide(int a, int b) {
    int result = a / b; // May throw ArithmeticException
}
```

### 6. Explain the Collections Framework hierarchy

**Answer:**

```
Collection (Interface)
├── List (Interface)
│   ├── ArrayList
│   ├── LinkedList
│   └── Vector
├── Set (Interface)
│   ├── HashSet
│   ├── LinkedHashSet
│   └── TreeSet
└── Queue (Interface)
    ├── PriorityQueue
    └── LinkedList

Map (Interface) - separate hierarchy
├── HashMap
├── LinkedHashMap
├── TreeMap
└── Hashtable
```

### 7. What is multithreading? How do you create threads in Java?

**Answer:**

Multithreading allows concurrent execution of multiple threads.

**Two ways to create threads:**

1. **Extending Thread class:**
```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running");
    }
}
MyThread thread = new MyThread();
thread.start();
```

2. **Implementing Runnable interface (preferred):**
```java
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Thread running");
    }
}
Thread thread = new Thread(new MyRunnable());
thread.start();
```

### 8. What are Lambda Expressions?

**Answer:**

Lambda expressions (Java 8+) are anonymous functions that make code more concise.

**Syntax:** `(parameters) -> expression` or `(parameters) -> { statements; }`

```java
// Without lambda
Comparator<String> comparator = new Comparator<String>() {
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
};

// With lambda
Comparator<String> comparator = (s1, s2) -> s1.compareTo(s2);
```

---

## 🌱 Spring Boot Technical Questions

### 1. What is Spring Boot? What are its advantages?

**Answer:**

Spring Boot is an opinionated framework that simplifies Spring application development.

**Advantages:**
- **Auto-configuration**: Automatically configures Spring based on dependencies
- **Standalone**: Creates executable JAR with embedded server
- **Production-ready**: Built-in features like metrics, health checks
- **Starter dependencies**: Pre-configured dependency sets
- **No XML configuration**: Java-based configuration
- **Fast development**: Quick project setup with Spring Initializr

### 2. Explain Dependency Injection in Spring

**Answer:**

Dependency Injection (DI) is a design pattern where objects receive their dependencies from external sources rather than creating them.

**Types of DI:**

1. **Constructor Injection (Recommended):**
```java
@Service
public class UserService {
    private final UserRepository userRepository;
    
    @Autowired // Optional in Spring 4.3+
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

2. **Setter Injection:**
```java
@Service
public class UserService {
    private UserRepository userRepository;
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

3. **Field Injection (Not recommended):**
```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
```

### 3. Explain Spring Boot annotations

**Answer:**

**Core annotations:**

- `@SpringBootApplication`: Combines @Configuration, @EnableAutoConfiguration, @ComponentScan
- `@RestController`: Combines @Controller + @ResponseBody
- `@Service`: Business logic layer
- `@Repository`: Data access layer
- `@Component`: Generic Spring-managed component
- `@Autowired`: Dependency injection
- `@Configuration`: Java-based configuration
- `@Bean`: Define Spring beans

**REST API annotations:**

- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- `@PathVariable`: Extract from URL path
- `@RequestParam`: Extract from query parameters
- `@RequestBody`: Map HTTP request body to object
- `@ResponseStatus`: Set HTTP status code

### 4. What is the difference between @Component, @Service, and @Repository?

**Answer:**

All are stereotypes that mark classes as Spring beans, but with semantic differences:

- **@Component**: Generic stereotype for any Spring-managed component
- **@Service**: Business logic layer - indicates service classes
- **@Repository**: Data access layer - adds automatic exception translation for persistence exceptions

```java
@Component
public class MyComponent { } // Generic component

@Service
public class UserService { } // Business logic

@Repository
public class UserRepository { } // Data access
```

**Best practice**: Use the most specific annotation for clarity.

### 5. Explain Spring Bean Scopes

**Answer:**

**Common scopes:**

1. **Singleton (default)**: One instance per Spring container
```java
@Component
@Scope("singleton")
public class MySingleton { }
```

2. **Prototype**: New instance every time it's requested
```java
@Component
@Scope("prototype")
public class MyPrototype { }
```

3. **Request**: One instance per HTTP request (web applications)
4. **Session**: One instance per HTTP session (web applications)

### 6. What is application.properties vs application.yml?

**Answer:**

Both configure Spring Boot applications, but with different formats:

**application.properties:**
```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=user
spring.datasource.password=pass
```

**application.yml (preferred for complex configurations):**
```yaml
server:
  port: 8080
  
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: user
    password: pass
```

**YAML advantages**: More readable, hierarchical, less repetitive

### 7. How do you handle exceptions in Spring Boot?

**Answer:**

**1. @ControllerAdvice (Global exception handling):**

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception ex) {
        return new ErrorResponse("Internal server error");
    }
}
```

**2. @ExceptionHandler in controller:**

```java
@RestController
public class UserController {
    
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFound(UserNotFoundException ex) {
        return ex.getMessage();
    }
}
```

### 8. What is Spring Boot Actuator?

**Answer:**

Spring Boot Actuator provides production-ready features like monitoring and management endpoints.

**Common endpoints:**
- `/actuator/health`: Application health status
- `/actuator/info`: Application information
- `/actuator/metrics`: Application metrics
- `/actuator/env`: Environment properties
- `/actuator/loggers`: Logging configuration

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

---

## 💾 Database & JPA Questions

### 1. What is JPA? What is the difference between JPA and Hibernate?

**Answer:**

**JPA (Java Persistence API)**:
- **Specification** for object-relational mapping in Java
- Defines interfaces and annotations
- Vendor-independent

**Hibernate**:
- **Implementation** of JPA specification
- Most popular JPA provider
- Adds additional features beyond JPA

Relationship: JPA is the interface, Hibernate is the implementation.

### 2. Explain JPA Entity relationships

**Answer:**

**1. @OneToOne:**
```java
@Entity
public class User {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
```

**2. @OneToMany / @ManyToOne:**
```java
@Entity
public class Department {
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}

@Entity
public class Employee {
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
```

**3. @ManyToMany:**
```java
@Entity
public class Student {
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
}
```

### 3. What is the difference between Lazy and Eager loading?

**Answer:**

**Eager Loading:**
- Loads related entities immediately with the parent
- Default for @OneToOne and @ManyToOne
- Can cause performance issues (loads unnecessary data)

**Lazy Loading:**
- Loads related entities only when accessed
- Default for @OneToMany and @ManyToMany
- Better performance but can cause LazyInitializationException

```java
@Entity
public class Department {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Employee> employees; // Loaded only when accessed
}
```

### 4. What is the N+1 query problem? How to solve it?

**Answer:**

**Problem**: Executing 1 query to fetch N parent entities, then N additional queries to fetch related child entities.

```java
// N+1 Problem
List<Department> departments = departmentRepository.findAll(); // 1 query
for (Department dept : departments) {
    dept.getEmployees().size(); // N queries (one per department)
}
```

**Solutions:**

1. **Using JOIN FETCH:**
```java
@Query("SELECT d FROM Department d JOIN FETCH d.employees")
List<Department> findAllWithEmployees();
```

2. **Using @EntityGraph:**
```java
@EntityGraph(attributePaths = {"employees"})
List<Department> findAll();
```

### 5. What are ACID properties?

**Answer:**

**ACID** ensures reliable database transactions:

- **Atomicity**: All or nothing - transaction either completes fully or not at all
- **Consistency**: Database moves from one valid state to another
- **Isolation**: Concurrent transactions don't interfere with each other
- **Durability**: Committed changes are permanent

---

## 🌐 REST API Questions

### 1. What is REST? What are RESTful principles?

**Answer:**

**REST (Representational State Transfer)** is an architectural style for designing networked applications.

**RESTful Principles:**

1. **Client-Server**: Separation of concerns
2. **Stateless**: Each request contains all needed information
3. **Cacheable**: Responses must define themselves as cacheable or not
4. **Uniform Interface**: Standard methods and resource identifiers
5. **Layered System**: Client doesn't know if connected to end server
6. **Resource-Based**: Everything is a resource with unique URI

### 2. Explain HTTP Methods and when to use them

**Answer:**

| Method | Purpose | Idempotent | Safe |
|--------|---------|------------|------|
| GET | Retrieve resource | Yes | Yes |
| POST | Create new resource | No | No |
| PUT | Update/Replace resource | Yes | No |
| PATCH | Partial update | No | No |
| DELETE | Delete resource | Yes | No |

**Examples:**
```java
@GetMapping("/users")           // Get all users
@GetMapping("/users/{id}")      // Get user by ID
@PostMapping("/users")          // Create new user
@PutMapping("/users/{id}")      // Update user
@DeleteMapping("/users/{id}")   // Delete user
```

### 3. What are HTTP Status Codes? Give examples

**Answer:**

**2xx Success:**
- `200 OK`: Successful request
- `201 Created`: Resource created successfully
- `204 No Content`: Successful but no content to return

**4xx Client Errors:**
- `400 Bad Request`: Invalid request
- `401 Unauthorized`: Authentication required
- `403 Forbidden`: Authorized but not permitted
- `404 Not Found`: Resource doesn't exist
- `409 Conflict`: Conflict with current state

**5xx Server Errors:**
- `500 Internal Server Error`: Server error
- `503 Service Unavailable`: Server temporarily unavailable

### 4. What is the difference between @PathVariable and @RequestParam?

**Answer:**

**@PathVariable**: Extract values from URI path
```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    // GET /users/123 -> id = 123
}
```

**@RequestParam**: Extract values from query parameters
```java
@GetMapping("/users")
public List<User> getUsers(@RequestParam String name) {
    // GET /users?name=John -> name = "John"
}
```

### 5. How do you implement validation in Spring Boot?

**Answer:**

**1. Add dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

**2. Add validation annotations to DTO:**
```java
public class UserDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50)
    private String name;
    
    @Email(message = "Invalid email")
    private String email;
    
    @Min(value = 18, message = "Must be at least 18")
    private Integer age;
}
```

**3. Use @Valid in controller:**
```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
    // If validation fails, returns 400 Bad Request
}
```

---

## 🧪 Testing Questions

### 1. What types of tests do you know?

**Answer:**

**1. Unit Tests:**
- Test individual methods/classes in isolation
- Use mocking for dependencies
- Fast execution
- Tools: JUnit 5, Mockito

**2. Integration Tests:**
- Test multiple components together
- Test with real database (or test containers)
- Slower than unit tests
- Tools: Spring Boot Test, TestContainers

**3. End-to-End (E2E) Tests:**
- Test complete user flows
- Test through UI or API
- Slowest tests
- Tools: Selenium, REST Assured

### 2. What is Mockito? Give an example

**Answer:**

Mockito is a mocking framework for creating test doubles in unit tests.

```java
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    public void testFindUserById() {
        // Arrange
        User mockUser = new User(1L, "John");
        when(userRepository.findById(1L))
            .thenReturn(Optional.of(mockUser));
        
        // Act
        User result = userService.findById(1L);
        
        // Assert
        assertEquals("John", result.getName());
        verify(userRepository).findById(1L);
    }
}
```

### 3. How do you test Spring Boot applications?

**Answer:**

**1. Unit Test (Service Layer):**
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void testCreateUser() {
        // Test implementation
    }
}
```

**2. Integration Test (Controller):**
```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testGetUser() throws Exception {
        mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("John"));
    }
}
```

**3. Repository Test:**
```java
@DataJpaTest
class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void testFindByEmail() {
        User user = new User("john@example.com");
        userRepository.save(user);
        
        User found = userRepository.findByEmail("john@example.com");
        assertNotNull(found);
    }
}
```

---

## 💻 Live Coding Exercises

### Exercise 1: Create a Simple CRUD API

**Task**: Create a REST API for managing books with the following endpoints:
- GET /books - Get all books
- GET /books/{id} - Get book by ID
- POST /books - Create new book
- PUT /books/{id} - Update book
- DELETE /books/{id} - Delete book

**Expected Implementation:**

```java
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String isbn;
    
    // Getters, setters, constructors
}

public interface BookRepository extends JpaRepository<Book, Long> {
}

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }
    
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        return bookRepository.save(book);
    }
    
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
}

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody Book book) {
        return bookService.createBook(book);
    }
    
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
```

### Exercise 2: Stream API Problems

**Problem 1**: Filter and transform list
```java
// Given a list of integers, return list of even numbers multiplied by 2
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

List<Integer> result = numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * 2)
    .collect(Collectors.toList());
// Result: [4, 8, 12, 16]
```

**Problem 2**: Group and count
```java
// Count frequency of each word
List<String> words = Arrays.asList("apple", "banana", "apple", "cherry", "banana", "apple");

Map<String, Long> frequency = words.stream()
    .collect(Collectors.groupingBy(
        Function.identity(),
        Collectors.counting()
    ));
// Result: {apple=3, banana=2, cherry=1}
```

### Exercise 3: Implement FizzBuzz

```java
public List<String> fizzBuzz(int n) {
    return IntStream.rangeClosed(1, n)
        .mapToObj(i -> {
            if (i % 15 == 0) return "FizzBuzz";
            if (i % 3 == 0) return "Fizz";
            if (i % 5 == 0) return "Buzz";
            return String.valueOf(i);
        })
        .collect(Collectors.toList());
}
```

---

## 🏗️ System Design Questions (Basic)

### Question: Design a URL Shortener (Basic Version)

**Requirements:**
- Shorten long URLs
- Redirect short URLs to original URLs

**Basic Design:**

```java
@Entity
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String shortCode;
    
    private String originalUrl;
    private LocalDateTime createdAt;
    
    // Getters, setters
}

@Service
public class UrlShortenerService {
    @Autowired
    private UrlMappingRepository repository;
    
    public String shortenUrl(String originalUrl) {
        String shortCode = generateShortCode();
        UrlMapping mapping = new UrlMapping();
        mapping.setShortCode(shortCode);
        mapping.setOriginalUrl(originalUrl);
        mapping.setCreatedAt(LocalDateTime.now());
        repository.save(mapping);
        return shortCode;
    }
    
    public String getOriginalUrl(String shortCode) {
        return repository.findByShortCode(shortCode)
            .orElseThrow(() -> new ResourceNotFoundException("URL not found"))
            .getOriginalUrl();
    }
    
    private String generateShortCode() {
        return RandomStringUtils.randomAlphanumeric(6);
    }
}
```

**Discussion Points:**
- How to generate unique short codes?
- How to handle collisions?
- How to track analytics (click count)?
- How to expire old URLs?

---

## ❓ Questions to Ask the Interviewer

### About the Role

1. "Can you describe a typical day/week for this position?"
2. "What are the immediate priorities for the person in this role in the first 3 months?"
3. "What does success look like in this position?"
4. "What technologies and tools does the team currently use?"
5. "Is there a roadmap to move from junior to mid-level developer?"

### About the Team

6. "How is the development team structured?"
7. "How many developers are on the team? What are their experience levels?"
8. "How does the team handle code reviews?"
9. "What is the team's approach to testing?"
10. "Do you practice pair programming or mentoring?"

### About the Company

11. "What is the company's approach to professional development and training?"
12. "What are the company's tech stack and architecture decisions?"
13. "How does the company balance technical debt with new features?"
14. "What is the company culture like?"
15. "What are the company's growth plans?"

### About the Work Environment

16. "What is the work schedule? Is there flexibility in hours?"
17. "What is the policy on remote work / hybrid work?"
18. "How does the team communicate (Slack, email, daily standups)?"
19. "What tools do you use for project management?"
20. "How often does the team deploy to production?"

### About Growth

21. "What learning and development opportunities are available?"
22. "Does the company support attending conferences or taking courses?"
23. "Are there opportunities to work on different projects or technologies?"
24. "How is performance evaluated?"

---

## 📚 Final Preparation Checklist

### Technical Preparation
- [ ] Review Java Core concepts (collections, streams, exceptions)
- [ ] Study Spring Boot annotations and concepts
- [ ] Practice JPA relationships and queries
- [ ] Understand REST API principles and HTTP methods
- [ ] Practice live coding on LeetCode (Easy level, 20-30 problems)
- [ ] Review your personal projects - be ready to explain architecture and decisions

### Language Preparation
- [ ] Practice Spanish interview questions (if required)
- [ ] Prepare "tell me about yourself" in Spanish and English
- [ ] Practice technical terminology in Spanish

### Practical Preparation
- [ ] Test your internet connection and webcam
- [ ] Prepare questions to ask the interviewer
- [ ] Have your resume and portfolio links ready
- [ ] Prepare examples of challenges you've faced and how you solved them

### Day Before Interview
- [ ] Get good sleep
- [ ] Review company website and recent news
- [ ] Test your development environment (for live coding)
- [ ] Prepare notebook for taking notes

---

**¡Buena suerte con tus entrevistas! Good luck with your interviews!** 🍀🚀
