# Project 3 — Social Media API

## 📖 Description

A feature-rich social media backend offering both REST and GraphQL APIs.
Demonstrates real-time communication, file uploads, and complex query optimization.

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Database | PostgreSQL 15 |
| Caching | Redis 7 |
| Real-time | WebSocket (STOMP) |
| API | REST + GraphQL (spring-graphql) |
| File Storage | AWS S3 / GCP Cloud Storage |
| Authentication | JWT + OAuth2 (Google, GitHub) |
| Search | PostgreSQL full-text search / Elasticsearch |
| Documentation | Swagger + GraphiQL |

## 🏗️ Architecture

```
Client
  ├── REST requests  ──▶ /api/v1/**
  ├── GraphQL queries ─▶ /graphql
  └── WebSocket conn. ─▶ /ws
         │
[Spring Boot Application]
  ├── Security (JWT + OAuth2)
  ├── REST Controllers
  ├── GraphQL Resolvers (Query, Mutation, Subscription)
  ├── WebSocket Message Broker (STOMP)
  └── Services / Repositories
         │
    ┌────┴────┐
    │         │
[PostgreSQL] [Redis]
                 │
           [Cloud Storage]
```

## ✨ Features

### User Profiles
- [ ] Registration (email/password + social OAuth2)
- [ ] Profile: bio, avatar (uploaded to cloud storage), cover photo
- [ ] Follow / Unfollow users
- [ ] Follower / Following counts and lists
- [ ] Suggested users to follow

### Posts
- [ ] Create posts with text, images, videos
- [ ] Edit and delete own posts
- [ ] Like / Unlike posts
- [ ] Comment on posts
- [ ] Reply to comments (nested)
- [ ] Share / Repost
- [ ] Post visibility: PUBLIC, FOLLOWERS_ONLY, PRIVATE

### Feed
- [ ] Personalised feed (posts from followed users)
- [ ] Trending / explore feed
- [ ] Pagination (cursor-based for feed)

### Real-time Features
- [ ] Live notifications via WebSocket:
  - New follower
  - Post liked
  - Comment on your post
  - Direct message received
- [ ] Online presence indicator

### Direct Messaging
- [ ] Send/receive DMs
- [ ] Read receipts
- [ ] Media sharing in DMs

### Search
- [ ] Full-text search for posts
- [ ] User search by name/username

### GraphQL API
- [ ] Query: `user`, `post`, `feed`, `search`
- [ ] Mutation: `createPost`, `likePost`, `followUser`, `sendMessage`
- [ ] Subscription: `newNotification`, `newMessage`

## 🗂️ Data Model (Simplified)

```
User (N) ←──── Follow ────▶ (N) User
User (1) ──── (N) Post (1) ──── (N) Comment
Post (1) ──── (N) Like
Post (1) ──── (N) Media
User (1) ──── (N) Notification
User (N) ←──── Message ────▶ (N) User
```

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Docker & Docker Compose
- AWS/GCP credentials for file storage

### Run Locally

```bash
# Start infrastructure
docker-compose up -d postgres redis

# Run app
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

### Endpoints

| Interface | URL |
|-----------|-----|
| REST API | http://localhost:8080/api/v1/ |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| GraphQL Playground | http://localhost:8080/graphiql |
| WebSocket test | ws://localhost:8080/ws |

## 📊 Skills Demonstrated

- REST + GraphQL APIs in the same application
- WebSocket real-time messaging with STOMP
- OAuth2 social login (Google, GitHub)
- Cursor-based pagination for infinite scroll
- File upload to cloud storage
- Complex PostgreSQL queries with joins
- Redis caching of hot data (trending posts, user sessions)
- N+1 query prevention with DataLoader (GraphQL)
- Full-text search

## 🔧 Implementation Checklist

- [ ] User auth (JWT + Google OAuth2)
- [ ] Follow/Unfollow + Feed generation
- [ ] Post CRUD + media upload
- [ ] Likes and Comments
- [ ] REST API with Swagger
- [ ] GraphQL schema + resolvers
- [ ] WebSocket notifications
- [ ] Direct messaging
- [ ] Redis caching
- [ ] Full-text search
- [ ] Integration tests
- [ ] Docker + CI/CD
- [ ] Deploy to Cloud Run
