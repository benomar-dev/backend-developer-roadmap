# Project 2 — Task Management System (Microservices)

## 📖 Description

A distributed task management platform built using a microservices architecture.
Demonstrates service decomposition, asynchronous messaging, and Kubernetes deployment.

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Databases | MongoDB (tasks/projects), PostgreSQL (users) |
| Messaging | RabbitMQ |
| Gateway | Spring Cloud Gateway |
| Discovery | Netflix Eureka / Kubernetes DNS |
| Container | Docker + Kubernetes (Helm) |
| CI/CD | GitHub Actions + GitLab CI |
| Cloud | GKE (Google Kubernetes Engine) |

## 🏗️ Microservices Architecture

```
                 ┌─────────────┐
   Client ──────▶│ API Gateway │
                 └──────┬──────┘
                        │  routes requests
         ┌──────────────┼──────────────────┐
         │              │                  │
   ┌─────▼─────┐ ┌──────▼──────┐ ┌────────▼────────┐
   │  User     │ │  Project    │ │  Task           │
   │  Service  │ │  Service    │ │  Service        │
   │           │ │             │ │                 │
   │ PostgreSQL│ │  MongoDB    │ │  MongoDB        │
   └─────┬─────┘ └──────┬──────┘ └────────┬────────┘
         │              │                  │
         └──────────────┴──────────────────┘
                        │ events via RabbitMQ
                 ┌──────▼──────┐
                 │Notification │
                 │  Service    │
                 └─────────────┘
```

## ✨ Services

### User Service (`/api/users`)
- [ ] Registration, login (JWT)
- [ ] Profile management
- [ ] Role-based access (ADMIN, MEMBER)

### Project Service (`/api/projects`)
- [ ] Create and manage projects
- [ ] Team member management (add/remove)
- [ ] Project statistics

### Task Service (`/api/tasks`)
- [ ] Full CRUD for tasks
- [ ] Assign tasks to team members
- [ ] Status: `TODO → IN_PROGRESS → REVIEW → DONE`
- [ ] Kanban board data endpoint
- [ ] Due dates and priorities
- [ ] Labels/tags

### Notification Service
- [ ] Listens to RabbitMQ events
- [ ] Email notifications for: task assigned, due soon, overdue
- [ ] In-app notifications (stored in MongoDB)

### API Gateway
- [ ] Route all requests to appropriate services
- [ ] JWT validation at gateway level
- [ ] Rate limiting
- [ ] Request logging

## 🗓️ Event Flow Example

```
TaskService publishes: { type: "TASK_ASSIGNED", taskId, userId, dueDate }
         ↓
  RabbitMQ exchange
         ↓
NotificationService consumes → sends email to assignee
```

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Docker & Docker Compose
- kubectl (for Kubernetes)
- Helm 3

### Run Locally (Docker Compose)

```bash
docker-compose up -d
```

### Deploy to Kubernetes

```bash
# Apply all manifests
kubectl apply -f k8s/

# Or use Helm
helm install task-management ./helm/task-management
```

## 📊 Skills Demonstrated

- Microservices decomposition (bounded contexts)
- Event-Driven Architecture with RabbitMQ
- API Gateway pattern
- Service Discovery
- MongoDB (document store) + PostgreSQL
- Kubernetes deployment (Deployments, Services, Ingress, HPA)
- Helm chart creation
- Distributed JWT validation
- End-to-end testing of distributed system

## 🔧 Implementation Checklist

- [ ] User Service (Spring Boot + PostgreSQL)
- [ ] Project Service (Spring Boot + MongoDB)
- [ ] Task Service (Spring Boot + MongoDB)
- [ ] Notification Service (Spring Boot + RabbitMQ consumer)
- [ ] API Gateway (Spring Cloud Gateway)
- [ ] RabbitMQ event publishing in Task/Project services
- [ ] Docker Compose for local dev
- [ ] Kubernetes manifests for each service
- [ ] Helm chart
- [ ] GitHub Actions CI for each service
- [ ] Integration tests
- [ ] Deploy to GKE
