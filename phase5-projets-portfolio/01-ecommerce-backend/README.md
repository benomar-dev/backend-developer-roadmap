# Project 1 — E-commerce Backend

## 📖 Description

A production-ready e-commerce backend REST API built with Spring Boot.
Handles product management, shopping cart, order processing, and payment integration.

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Database | PostgreSQL 15 |
| Caching | Redis 7 |
| Authentication | JWT (Spring Security) |
| Payments | Stripe API |
| Documentation | Swagger / OpenAPI 3 |
| Testing | JUnit 5, Mockito, TestContainers |
| Container | Docker + Docker Compose |
| CI/CD | GitHub Actions |
| Cloud | Google Cloud Run |

## 🏗️ Architecture

```
Client
  │
  ▼
[API Gateway / Nginx]
  │
  ▼
[Spring Boot Application]
  ├── AuthController      → /api/v1/auth
  ├── ProductController   → /api/v1/products
  ├── CategoryController  → /api/v1/categories
  ├── CartController      → /api/v1/cart
  ├── OrderController     → /api/v1/orders
  └── PaymentController   → /api/v1/payments
         │
    ┌────┴────┐
    │         │
[PostgreSQL] [Redis]
```

## ✨ Features

### Product Management
- [ ] CRUD operations for products
- [ ] Category hierarchy (parent/child)
- [ ] Product images (URL references)
- [ ] Inventory tracking
- [ ] Search and filtering (name, price range, category)
- [ ] Pagination and sorting

### Authentication & Authorization
- [ ] User registration and login
- [ ] JWT access token + refresh token
- [ ] Roles: `CUSTOMER`, `ADMIN`
- [ ] Email verification

### Shopping Cart
- [ ] Add / remove / update cart items
- [ ] Cart stored in Redis (session-based)
- [ ] Cart merging on login

### Order Management
- [ ] Create order from cart
- [ ] Order status lifecycle: `PENDING → CONFIRMED → SHIPPED → DELIVERED`
- [ ] Order history per user
- [ ] Admin: view and update all orders

### Payment
- [ ] Stripe checkout session integration
- [ ] Webhook handling for payment events
- [ ] Refund support

### Notifications
- [ ] Email notifications on order confirmation
- [ ] Email on shipping update (Spring Mail)

## 🗂️ Data Model

```
User (1) ──── (N) Order (1) ──── (N) OrderItem (N) ──── (1) Product
                                                               │
                                                         (N) ──── (1) Category
```

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Docker & Docker Compose
- Stripe account (test mode)

### Run Locally

```bash
# 1. Clone and enter directory
git clone https://github.com/benomar-dev/backend-developer-roadmap.git
cd phase5-projets-portfolio/01-ecommerce-backend

# 2. Copy environment file
cp .env.example .env
# Edit .env with your Stripe test keys

# 3. Start infrastructure
docker-compose up -d postgres redis

# 4. Run the app
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

### API Documentation

Once running, open: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Run Tests

```bash
./mvnw test                    # unit tests
./mvnw verify                  # all tests including integration
```

## 📊 Skills Demonstrated

- REST API design with proper HTTP semantics
- JWT authentication + refresh token flow
- Redis caching for session data
- Stripe payment gateway integration
- Transaction management (`@Transactional`)
- Exception handling (`@ControllerAdvice`)
- API pagination and filtering
- Unit + integration tests with TestContainers
- Docker containerisation
- GitHub Actions CI/CD

## 🔧 Implementation Checklist

- [ ] Project setup (Spring Initializr, dependencies)
- [ ] Database schema + Flyway migrations
- [ ] User entity + JWT auth
- [ ] Product + Category CRUD
- [ ] Cart (Redis)
- [ ] Order management
- [ ] Stripe integration
- [ ] Email notifications
- [ ] Swagger documentation
- [ ] Unit tests (>70% coverage)
- [ ] Integration tests (TestContainers)
- [ ] Dockerfile + docker-compose.yml
- [ ] GitHub Actions workflow
- [ ] Deploy to Cloud Run
- [ ] Final README with badge
