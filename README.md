<div align="center">

# 🧬 SkillDNA AI

### World's First AI-Powered Career Digital Twin Platform

**Built by [Ravikumar](https://github.com/ravigithubcse) — Ravi Future Labs**

[![CI Pipeline](https://github.com/ravigithubcse/skilldna-ai/actions/workflows/ci.yml/badge.svg)](https://github.com/ravigithubcse/skilldna-ai/actions)
[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/version-1.0.0-purple)](RELEASE_NOTES.md)

</div>

---

## 📌 What Is SkillDNA AI?

SkillDNA AI creates a **living AI digital twin of your career** — a dynamic model that continuously analyses your skills, work history, market trends, and industry shifts to give you personalised, data-driven career intelligence.

| Feature | Description |
|---------|-------------|
| 🧬 **Career Digital Twin** | A rich AI model of your skills, experience, and trajectory |
| 🔮 **Career Predictions** | AI-scored optimal paths and salary bands |
| 🎮 **What-If Simulations** | Explore "what if I learn X" before committing |
| 📊 **Job Market Intel** | Real-time demand signals for your skill set |
| 🎯 **Learning Paths** | Personalised skill-gap closure with ROI estimates |
| ⚠️ **Automation Risk** | Know your automation exposure before it's too late |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                      Clients (Web / Mobile)                  │
└─────────────────────────┬───────────────────────────────────┘
                          │ HTTPS
┌─────────────────────────▼───────────────────────────────────┐
│          API Gateway :8080  (Spring Cloud Gateway)           │
│          JWT validation · CORS · Rate limiting · Routing     │
└──┬──────────┬──────────┬──────────┬──────────┬─────────────┘
   │          │          │          │          │
:8081      :8082      :8083      :8084      :8085  :8086  :8087
User    CareerTwin  Prediction Simulation JobMarket Learning Notif
 │           │
PG-users  PG-twin        ← PostgreSQL 16
             │
         Redis 7 ←──────────────── All services share Redis
             │
         Kafka 3.6 ←────────────── Event streaming bus
```

---

## 🚀 Quick Start

### Prerequisites

| Tool | Version |
|------|---------|
| Docker | 24+ |
| Docker Compose | 2.24+ |
| Java (for local dev) | 21 |
| Maven (for local dev) | 3.9+ |

### 1. Clone

```bash
git clone https://github.com/ravigithubcse/skilldna-ai.git
cd skilldna-ai
```

### 2. Start the full stack

```bash
docker-compose up -d
```

This starts:
- PostgreSQL (users + careertwin databases)
- Redis
- Kafka + Zookeeper + Kafka UI
- All 8 microservices

### 3. Verify health

```bash
# Gateway
curl http://localhost:8080/actuator/health

# User service
curl http://localhost:8081/actuator/health

# Career Twin
curl http://localhost:8082/actuator/health
```

### 4. Register a user

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "roy@example.com",
    "password": "Secret@123",
    "firstName": "Roy",
    "lastName": "Ravi",
    "countryCode": "IND"
  }'
```

### 5. Login & get JWT

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"roy@example.com","password":"Secret@123"}'
```

### 6. Create Career Digital Twin

```bash
TOKEN="<your-jwt-from-step-5>"

curl -X POST http://localhost:8080/api/v1/career-twin \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "headline": "Java Full Stack Developer | IoT & AI",
    "currentRole": "Associate Software Engineer",
    "currentCompany": "Trinity Mobility",
    "yearsExperience": 2,
    "countryCode": "IND",
    "city": "Bengaluru",
    "skills": [
      {"skillName":"Java","level":"ADVANCED","category":"PROGRAMMING_LANGUAGE","yearsUsed":2,"isPrimary":true},
      {"skillName":"Spring Boot","level":"ADVANCED","category":"FRAMEWORK","yearsUsed":2,"isPrimary":true},
      {"skillName":"Apache Kafka","level":"INTERMEDIATE","category":"DATA_ENGINEERING","yearsUsed":1},
      {"skillName":"Angular","level":"INTERMEDIATE","category":"FRAMEWORK","yearsUsed":1},
      {"skillName":"PostgreSQL","level":"INTERMEDIATE","category":"DATABASE","yearsUsed":2}
    ],
    "workExperiences": [
      {
        "companyName": "Trinity Mobility",
        "roleTitle": "Associate Software Engineer",
        "startDate": "2024-07-01",
        "isCurrent": true,
        "description": "Built real-time IoT data pipelines using Apache Kafka and Spring Boot 3.x"
      }
    ]
  }'
```

---

## 📖 API Documentation (Swagger)

| Service | Swagger UI |
|---------|------------|
| User Service | http://localhost:8081/swagger-ui.html |
| Career Twin | http://localhost:8082/swagger-ui.html |
| Prediction | http://localhost:8083/swagger-ui.html |
| Simulation | http://localhost:8084/swagger-ui.html |
| Job Market | http://localhost:8085/swagger-ui.html |
| Learning | http://localhost:8086/swagger-ui.html |
| Notification | http://localhost:8087/swagger-ui.html |

---

## 🗂️ Project Structure

```
skilldna-ai/
├── backend/
│   ├── api-gateway/              # Spring Cloud Gateway :8080
│   ├── user-service/             # Auth + profiles :8081
│   │   └── src/main/java/
│   │       └── com/ravifl/skilldna/user/
│   │           ├── controller/   # AuthController, UserController
│   │           ├── service/      # UserService
│   │           ├── entity/       # User, SubscriptionTier
│   │           ├── repository/   # UserRepository
│   │           ├── dto/          # Request/Response DTOs
│   │           ├── security/     # JwtService, JwtAuthFilter
│   │           ├── config/       # SecurityConfig, OpenApiConfig
│   │           ├── exception/    # GlobalExceptionHandler
│   │           └── mapper/       # UserMapper (MapStruct)
│   ├── career-twin-service/      # Career Digital Twin :8082
│   ├── prediction-service/       # AI predictions :8083
│   ├── simulation-service/       # What-if engine :8084
│   ├── job-market-service/       # Job market intel :8085
│   ├── learning-service/         # Learning paths :8086
│   └── notification-service/     # Notifications :8087
├── ai-services/
│   ├── nlp-service/              # Python FastAPI NLP (v1.1)
│   └── llm-gateway-service/      # LLM abstraction layer (v1.1)
├── frontend/                     # Angular 17 SPA (v1.2)
├── infrastructure/
│   ├── docker/
│   ├── kubernetes/
│   └── terraform/
├── docs/
│   ├── api/
│   └── architecture/
├── .github/
│   └── workflows/
│       └── ci.yml
├── docker-compose.yml
├── pom.xml                       # Maven multi-module parent
├── RELEASE_NOTES.md
└── README.md
```

---

## 🔑 Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `JWT_SECRET` | `SkillDNA-AI-Super-...` | JWT signing secret (min 32 chars) |
| `JWT_EXPIRATION_MS` | `3600000` | Token TTL in milliseconds |
| `DB_HOST` | `localhost` | PostgreSQL host |
| `DB_NAME` | service-specific | Database name |
| `DB_USER` | `skilldna` | Database user |
| `DB_PASSWORD` | `skilldna_pass` | Database password |
| `REDIS_HOST` | `localhost` | Redis host |
| `KAFKA_SERVERS` | `localhost:9092` | Kafka bootstrap servers |

> ⚠️ **Never commit real secrets.** Use `.env` files or a secrets manager in production.

---

## 🧪 Running Tests

```bash
# All services
mvn test --no-transfer-progress

# Single service
cd backend/user-service && mvn test

# With coverage report
cd backend/user-service && mvn verify
open target/site/jacoco/index.html
```

---

## 🏛️ Design Principles

- **Clean Code** — SonarQube-ready, <15 cyclomatic complexity per method
- **Single Responsibility** — Each class has one reason to change
- **SOLID** — Interfaces for every service, dependency injection throughout
- **Fail Fast** — Validation at the API boundary, meaningful error codes
- **Observability** — Actuator, structured logging, Kafka audit trail
- **Security by Default** — JWT everywhere, non-root containers, no secrets in code

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit: `git commit -m "feat: add your feature"`
4. Push: `git push origin feature/your-feature`
5. Open a Pull Request against `develop`

---

## 📄 License

MIT License — Copyright (c) 2026 Ravikumar, Ravi Future Labs

See [LICENSE](LICENSE) for full text.

---

<div align="center">

**Built with ❤️ by [Ravikumar](https://github.com/ravigithubcse) — Ravi Future Labs**

*Empowering every professional with an AI career co-pilot*

</div>
