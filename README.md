# 🎬 Streaming Platform - Microservices Architecture

## Description
Backend application for video streaming built with Spring Boot microservices and REST APIs.

## Architecture

```
                        ┌─────────────────┐
                        │  Gateway Service │  :8080
                        │ (Spring Gateway) │
                        └────────┬────────┘
                                 │
              ┌──────────────────┼──────────────────┐
              │                                     │
    ┌─────────▼────────┐               ┌────────────▼───────┐
    │  Video Service   │  :8081        │   User Service     │  :8082
    │  - CRUD Videos   │               │   - CRUD Users     │
    │  - Search/Filter │               │   - Watchlist      │
    └─────────┬────────┘               │   - Watch History  │
              │                        │   - Statistics     │
              │ OpenFeign              └────────────────────┘
              └────────────────────────────────────┘

    ┌─────────────────┐        ┌─────────────────┐
    │Discovery Service│  :8761 │  Config Service │  :8888
    │    (Eureka)     │        │  (Spring Cloud) │
    └─────────────────┘        └─────────────────┘

    ┌──────────────┐   ┌──────────────┐
    │   video_db   │   │   user_db    │
    │   (MySQL)    │   │   (MySQL)    │
    └──────────────┘   └──────────────┘
```

## Tech Stack
- **Spring Boot 3.2** - Core framework
- **Spring Cloud 2023** - Microservices infrastructure
- **Spring Data JPA** - Data persistence
- **Spring Cloud Gateway** - API Gateway
- **Netflix Eureka** - Service discovery
- **Spring Cloud Config** - Centralized configuration
- **OpenFeign** - Inter-service communication
- **MySQL** - Relational database (one per service)
- **Docker & Docker Compose** - Containerization
- **Lombok** - Boilerplate reduction
- **Maven** - Build tool

## Design Patterns
- **Repository Pattern** - Data access layer
- **DTO Pattern** - Data transfer objects
- **Mapper Pattern** - Entity <-> DTO conversion
- **Layered Architecture** - Controller → Service → Repository
- **Circuit Breaker** - Resilience with Feign fallback

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- MySQL 8.0 (or use Docker)

### Run with Docker Compose (Recommended)
```bash
# Clone the repository
git clone <your-repo-url>
cd streaming-platform

# Start all services
docker-compose up -d

# Check services
docker-compose ps
```

### Run Locally

**1. Start MySQL databases:**
```bash
docker run -d --name video-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=video_db -p 3306:3306 mysql:8.0
docker run -d --name user-db  -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=user_db  -p 3307:3306 mysql:8.0
```

**2. Setup Config Repo:**
```bash
mkdir ~/streaming-config-repo && cd ~/streaming-config-repo
git init && git branch -M main
cp -r streaming-platform/config-repo/* .
git add . && git commit -m "Initial config"
```

**3. Start services in order:**
```bash
# Terminal 1 - Config Service
cd config-service && mvn spring-boot:run

# Terminal 2 - Discovery Service
cd discovery-service && mvn spring-boot:run

# Terminal 3 - Gateway Service
cd gateway-service && mvn spring-boot:run

# Terminal 4 - Video Service
cd video-service && mvn spring-boot:run

# Terminal 5 - User Service
cd user-service && mvn spring-boot:run
```

## Service URLs

| Service | URL | Description |
|---------|-----|-------------|
| Gateway | http://localhost:8080 | API Entry Point |
| Eureka Dashboard | http://localhost:8761 | Service Registry |
| Config Server | http://localhost:8888 | Config Server |
| Video Service | http://localhost:8081 | Direct access |
| User Service | http://localhost:8082 | Direct access |

## API Endpoints (via Gateway - port 8080)

### 🎥 Video Service (`/api/videos`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/videos` | Create a video |
| GET | `/api/videos` | Get all videos |
| GET | `/api/videos/{id}` | Get video by ID |
| PUT | `/api/videos/{id}` | Update video |
| DELETE | `/api/videos/{id}` | Delete video |
| GET | `/api/videos/type/{type}` | Filter by type (FILM/SERIE) |
| GET | `/api/videos/category/{category}` | Filter by category |
| GET | `/api/videos/search?title=xxx` | Search by title |
| GET | `/api/videos/filter?type=FILM&category=ACTION` | Filter by type+category |

### 👤 User Service (`/api/users`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users` | Register user |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### 📋 Watchlist (`/api/watchlist`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/watchlist/users/{userId}/videos/{videoId}` | Add to watchlist |
| DELETE | `/api/watchlist/users/{userId}/videos/{videoId}` | Remove from watchlist |
| GET | `/api/watchlist/users/{userId}` | Get user's watchlist |
| GET | `/api/watchlist/users/{userId}/videos/{videoId}/check` | Check if in watchlist |

### 📊 Watch History (`/api/history`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/history/users/{userId}` | Record/Update watch progress |
| GET | `/api/history/users/{userId}` | Get watch history |
| GET | `/api/history/users/{userId}/statistics` | Get watch statistics |
| DELETE | `/api/history/users/{userId}` | Clear watch history |

## Postman Examples

### Create a Video
```json
POST http://localhost:8080/api/videos
Content-Type: application/json

{
  "title": "Inception",
  "description": "A thief who steals corporate secrets through dream-sharing technology",
  "thumbnailUrl": "https://example.com/inception.jpg",
  "trailerUrl": "https://www.youtube.com/embed/YoHD9XEInc0",
  "duration": 148,
  "releaseYear": 2010,
  "type": "FILM",
  "category": "SCIENCE_FICTION",
  "rating": 8.8,
  "director": "Christopher Nolan",
  "cast": "Leonardo DiCaprio, Joseph Gordon-Levitt"
}
```

### Register a User
```json
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123"
}
```

### Add to Watchlist
```
POST http://localhost:8080/api/watchlist/users/1/videos/1
```

### Record Watch Progress
```json
POST http://localhost:8080/api/history/users/1
Content-Type: application/json

{
  "videoId": 1,
  "progressTime": 3600,
  "completed": false
}
```

### Get Watch Statistics
```
GET http://localhost:8080/api/history/users/1/statistics
```

## Video Types & Categories

**Types:** `FILM`, `SERIE`

**Categories:** `ACTION`, `COMEDIE`, `DRAME`, `SCIENCE_FICTION`, `THRILLER`, `HORREUR`

## Running Tests
```bash
# Video Service Tests
cd video-service && mvn test

# User Service Tests
cd user-service && mvn test

# All Tests
mvn test
```

## Project Structure
```
streaming-platform/
├── config-service/          # Spring Cloud Config Server
├── discovery-service/       # Eureka Server
├── gateway-service/         # Spring Cloud Gateway
├── video-service/           # Video CRUD + Search
│   └── src/main/java/com/streaming/video/
│       ├── controller/      # REST Controllers
│       ├── service/         # Business Logic
│       ├── repository/      # Data Access Layer
│       ├── entity/          # JPA Entities
│       ├── dto/             # Data Transfer Objects
│       ├── mapper/          # Entity <-> DTO
│       └── exception/       # Error Handling
├── user-service/            # Users + Watchlist + History
│   └── src/main/java/com/streaming/user/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       ├── mapper/
│       ├── feign/           # OpenFeign Clients
│       └── exception/
├── config-repo/             # Git-based Configuration Files
│   ├── video-service.yml
│   ├── user-service.yml
│   └── gateway-service.yml
└── docker-compose.yml
```
