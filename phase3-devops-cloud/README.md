# Phase 3 — DevOps & Cloud (Months 12–18)

## 🎯 Goals

By the end of this phase you will be able to:
- Containerise Spring Boot applications with Docker
- Build and run CI/CD pipelines (GitLab CI + GitHub Actions)
- Deploy to Google Cloud Platform (Cloud Run, GKE)
- Orchestrate containers with Kubernetes
- Monitor applications in production

---

## 📚 Topics & Checklist

### 🐳 Docker

- [ ] **Dockerfile** — writing optimised Dockerfiles for Spring Boot
- [ ] **Docker Compose** — multi-container local development
- [ ] **Multi-stage builds** — smaller production images
- [ ] **Volumes & Networks** — persistent storage, container networking
- [ ] **Docker Registry** — push/pull to Docker Hub or GCP Artifact Registry
- [ ] **Image optimisation** — layer caching, `.dockerignore`

**Spring Boot Dockerfile (multi-stage):**
```dockerfile
# Build stage
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Docker Compose for local dev:**
```yaml
# docker-compose.yml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/myapp
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=secret
    depends_on:
      - db
      - redis

  db:
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: myapp
      POSTGRES_PASSWORD: secret
    volumes:
      - postgres_data:/var/lib/postgresql/data

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

volumes:
  postgres_data:
```

---

### 🔄 CI/CD

#### GitLab CI/CD

- [ ] **`.gitlab-ci.yml`** — pipeline definition
- [ ] **Stages** — build → test → security-scan → deploy
- [ ] **Variables & Secrets** — CI/CD settings, masked variables
- [ ] **Runners** — shared vs self-hosted
- [ ] **Artifacts & Cache** — speed up pipelines
- [ ] **Environments** — dev, staging, production

```yaml
# .gitlab-ci.yml
stages:
  - build
  - test
  - docker
  - deploy

variables:
  MAVEN_OPTS: "-Dmaven.repo.local=$CI_PROJECT_DIR/.m2/repository"

cache:
  paths:
    - .m2/repository/

build:
  stage: build
  image: maven:3.9-eclipse-temurin-17
  script:
    - mvn package -DskipTests
  artifacts:
    paths:
      - target/*.jar

test:
  stage: test
  image: maven:3.9-eclipse-temurin-17
  services:
    - postgres:15-alpine
  variables:
    POSTGRES_DB: test_db
    POSTGRES_PASSWORD: test
  script:
    - mvn test

docker-build:
  stage: docker
  image: docker:24
  services:
    - docker:dind
  script:
    - docker build -t $CI_REGISTRY_IMAGE:$CI_COMMIT_SHA .
    - docker push $CI_REGISTRY_IMAGE:$CI_COMMIT_SHA
  only:
    - main

deploy-staging:
  stage: deploy
  script:
    - gcloud run deploy myapp --image $CI_REGISTRY_IMAGE:$CI_COMMIT_SHA --region europe-west1
  environment:
    name: staging
  only:
    - main
```

#### GitHub Actions

- [ ] **Workflow YAML** — `.github/workflows/ci.yml`
- [ ] **Events** — `push`, `pull_request`, `schedule`, `workflow_dispatch`
- [ ] **Jobs & Steps**
- [ ] **Secrets** — `${{ secrets.MY_SECRET }}`
- [ ] **Reusable workflows**

```yaml
# .github/workflows/ci.yml
name: CI

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  build-and-test:
    runs-on: ubuntu-latest
    services:
      postgres:
        image: postgres:15
        env:
          POSTGRES_PASSWORD: postgres
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: maven
      - run: mvn verify
      - name: Upload coverage to Codecov
        uses: codecov/codecov-action@v4
```

---

### ☸️ Kubernetes

- [ ] **Pods, Services, Deployments** — core abstractions
- [ ] **ConfigMaps & Secrets** — externalise configuration
- [ ] **Ingress & Load Balancing** — route external traffic
- [ ] **Persistent Volumes** — stateful workloads
- [ ] **Helm Charts** — package manager for Kubernetes
- [ ] **Health Checks** — `livenessProbe`, `readinessProbe`
- [ ] **Horizontal Pod Autoscaling (HPA)** — scale based on CPU/memory

**Spring Boot Deployment manifest:**
```yaml
# k8s/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
  namespace: production
spec:
  replicas: 2
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
        - name: myapp
          image: gcr.io/my-project/myapp:latest
          ports:
            - containerPort: 8080
          env:
            - name: DB_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: db-secret
                  key: password
          livenessProbe:
            httpGet:
              path: /actuator/health/liveness
              port: 8080
            initialDelaySeconds: 30
            periodSeconds: 10
          readinessProbe:
            httpGet:
              path: /actuator/health/readiness
              port: 8080
            initialDelaySeconds: 20
            periodSeconds: 5
          resources:
            requests:
              cpu: "250m"
              memory: "512Mi"
            limits:
              cpu: "500m"
              memory: "1Gi"
```

---

### ☁️ Google Cloud Platform (GCP)

- [ ] **Compute Engine** — VMs, startup scripts
- [ ] **Cloud Run** — serverless containers (easiest for Spring Boot)
- [ ] **Google Kubernetes Engine (GKE)** — managed Kubernetes
- [ ] **Cloud SQL** — managed PostgreSQL/MySQL
- [ ] **Cloud Storage** — file/object storage (like AWS S3)
- [ ] **Cloud Functions** — serverless event-driven functions
- [ ] **Pub/Sub** — asynchronous messaging
- [ ] **Cloud Load Balancing** — global load balancer
- [ ] **IAM** — roles, service accounts, least-privilege principle
- [ ] **Cloud Logging & Monitoring** — structured logs, dashboards, alerts
- [ ] **Cloud Build** — GCP-native CI/CD

**Deploy to Cloud Run (one-liner):**
```bash
gcloud run deploy myapp \
  --image gcr.io/MY_PROJECT/myapp:latest \
  --region europe-west1 \
  --platform managed \
  --allow-unauthenticated \
  --set-env-vars "SPRING_PROFILES_ACTIVE=prod" \
  --set-secrets "DB_PASSWORD=db-password:latest"
```

---

## 🗂️ Directory Structure (to be populated)

```
phase3-devops-cloud/
├── 01-docker/               # Dockerfile examples, compose files
├── 02-gitlab-ci/            # .gitlab-ci.yml templates
├── 03-github-actions/       # GitHub Actions workflows
├── 04-kubernetes/           # k8s manifests and Helm charts
└── 05-gcp/                  # GCP deployment scripts and configs
```

---

## 📚 Resources

- [Docker Documentation](https://docs.docker.com/)
- [GitLab CI/CD Docs](https://docs.gitlab.com/ee/ci/)
- [GitHub Actions Docs](https://docs.github.com/en/actions)
- [Kubernetes Documentation](https://kubernetes.io/docs/home/)
- [Google Cloud Skills Boost (free labs)](https://www.cloudskillsboost.google/)
- [Spring Boot on GCP](https://spring.io/projects/spring-cloud-gcp)

---

**⏱️ Estimated duration: 6 months (3h/day)**
