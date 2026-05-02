# Project 4 — Banking Application

## 📖 Description

A secure, production-grade banking backend demonstrating the highest standards of
security, auditability, and compliance. This is the most technically demanding project
and the centrepiece of a senior backend developer portfolio.

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Database | PostgreSQL 15 (primary data + audit) |
| Event Stream | Apache Kafka |
| Secrets | HashiCorp Vault |
| Security | Spring Security, JWT, 2FA (TOTP) |
| Cloud | GCP (Cloud Run, Cloud SQL, Secret Manager) |
| Monitoring | Prometheus + Grafana + Cloud Logging |
| Testing | JUnit 5, TestContainers, ArchUnit |

## 🏗️ Architecture

```
Client
  │
  ▼
[Spring Boot App]
  ├── AuthController    — login, 2FA, JWT
  ├── AccountController — CRUD accounts
  ├── TransactionController — transfers, deposits, withdrawals
  ├── AuditController   — read-only audit log
  └── ReportController  — account statements
         │
    ┌────┴────────┐
    │             │
[PostgreSQL]   [Kafka]
   ├── accounts     └── transaction.created
   ├── transactions     transaction.failed
   └── audit_log        fraud.detected
         │
   [Vault]  (secrets: DB password, Kafka creds)
```

## ✨ Features

### Account Management
- [ ] Create savings and checking accounts
- [ ] Account status: ACTIVE, FROZEN, CLOSED
- [ ] Account details and balance inquiry

### Transactions
- [ ] Deposit (cash-in)
- [ ] Withdrawal (with balance check)
- [ ] Account-to-account transfer
- [ ] Transaction idempotency (prevent duplicate submissions)
- [ ] All transactions are immutable (append-only ledger)

### Security
- [ ] JWT authentication with short-lived tokens (15 min)
- [ ] Refresh token rotation
- [ ] Two-Factor Authentication (TOTP — Google Authenticator compatible)
- [ ] Rate limiting per user/IP
- [ ] Encryption at rest for sensitive fields (AES-256)
- [ ] HTTPS only in production
- [ ] OWASP Top 10 mitigations

### Audit Trail
- [ ] Every action recorded: who, what, when, from where (IP)
- [ ] Immutable audit log (no UPDATE/DELETE on audit table)
- [ ] Admin can query audit log by user, date range, action type

### Fraud Detection (basic)
- [ ] Unusual transaction amount alert (>3× average)
- [ ] Multiple failed login attempts → account lock
- [ ] Transactions from new geography → flag for review
- [ ] Events published to Kafka for downstream processing

### Reporting
- [ ] Monthly account statement (PDF generation)
- [ ] Transaction history with filters
- [ ] Running balance per transaction

## 🔒 Security Architecture

```
Request
  │
  ▼
Rate Limiter (Bucket4j)
  │
  ▼
JWT Filter (validate token, check expiry)
  │
  ▼
2FA Check (if TOTP enabled for user)
  │
  ▼
@PreAuthorize checks (method-level)
  │
  ▼
Service Layer
  │
  ▼
Audit Interceptor (records every action)
```

## 🗂️ Data Model

```sql
-- Immutable ledger (never UPDATE or DELETE)
CREATE TABLE transactions (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type        VARCHAR(20) NOT NULL,        -- DEPOSIT, WITHDRAWAL, TRANSFER
    from_account UUID,
    to_account  UUID,
    amount      NUMERIC(19,4) NOT NULL,
    currency    CHAR(3) NOT NULL DEFAULT 'EUR',
    status      VARCHAR(20) NOT NULL,        -- PENDING, COMPLETED, FAILED
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    metadata    JSONB
);

-- Append-only audit log
CREATE TABLE audit_log (
    id          BIGSERIAL PRIMARY KEY,
    user_id     UUID,
    action      VARCHAR(100) NOT NULL,
    entity_type VARCHAR(50),
    entity_id   VARCHAR(100),
    ip_address  INET,
    user_agent  TEXT,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    details     JSONB
);
```

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Docker & Docker Compose
- HashiCorp Vault (or use GCP Secret Manager)

### Run Locally

```bash
# Start all infrastructure
docker-compose up -d postgres kafka vault

# Initialise Vault with secrets (first time)
./scripts/init-vault.sh

# Run app
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

### Environment Variables

```bash
DB_URL=jdbc:postgresql://localhost:5432/banking
DB_USERNAME=banking_user
DB_PASSWORD=<from vault>
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
JWT_SECRET=<from vault>
VAULT_TOKEN=<your vault token>
```

## 📊 Skills Demonstrated

- Financial transaction integrity with `@Transactional` and idempotency keys
- Immutable audit logging
- HashiCorp Vault integration for secrets management
- Apache Kafka for event streaming
- 2FA implementation (TOTP)
- Rate limiting (Bucket4j)
- Encryption of PII fields
- ArchUnit tests for architectural constraints
- Production monitoring (Prometheus + Grafana dashboards)
- GCP deployment with Secret Manager

## 🔧 Implementation Checklist

- [ ] Project setup + security baseline (Spring Security + JWT)
- [ ] Account CRUD (no real money yet)
- [ ] Transaction engine (deposit, withdraw, transfer)
- [ ] Idempotency middleware
- [ ] 2FA (TOTP)
- [ ] Audit log (interceptor + table)
- [ ] Kafka event publishing
- [ ] Fraud detection rules
- [ ] Vault integration
- [ ] Account statements / reports
- [ ] Rate limiting
- [ ] Prometheus metrics + Grafana dashboard
- [ ] ArchUnit architecture tests
- [ ] Full test suite (>80% coverage)
- [ ] Docker + CI/CD
- [ ] Deploy to GCP (Cloud Run + Cloud SQL)
- [ ] Security review (OWASP checklist)
