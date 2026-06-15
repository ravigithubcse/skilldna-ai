# SkillDNA AI — Release Notes

---

## v1.0.0 — Initial Release

**Release Date:** June 15, 2026
**Codename:** Genesis
**Author:** Ravikumar — Ravi Future Labs
**License:** MIT

---

### 🚀 Overview

SkillDNA AI v1.0.0 is the inaugural release of the world's first **AI-Powered Career Digital Twin Platform**. This release delivers the complete backend microservices architecture, REST APIs with Swagger documentation, Kafka-based event streaming, PostgreSQL persistence with Flyway migrations, Redis caching, Docker Compose local stack, and GitHub Actions CI/CD pipeline.

---

### ✨ New Features

#### Core Platform
- **Career Digital Twin Engine** — Full CRUD API for creating and managing a user's Career Digital Twin, including skill graph, work history, education, and certifications
- **AI Scoring Engine** — Automation risk score (0–100), career health score (0–100), and profile completeness percentage calculated on every profile update
- **JWT Authentication** — Stateless JWT-based auth across all services with BCrypt password hashing (cost factor 12)
- **Unified API Gateway** — Spring Cloud Gateway on port 8080 routing to all downstream services with JWT pre-validation

#### Microservices (8 total)
| Service | Port | Description |
|---------|------|-------------|
| `api-gateway` | 8080 | Reactive Spring Cloud Gateway |
| `user-service` | 8081 | Registration, login, profile management |
| `career-twin-service` | 8082 | Career Digital Twin core engine |
| `prediction-service` | 8083 | AI career path & salary prediction |
| `simulation-service` | 8084 | What-if career scenario engine |
| `job-market-service` | 8085 | Real-time job market intelligence |
| `learning-service` | 8086 | Personalised skill gap & learning paths |
| `notification-service` | 8087 | Multi-channel notification delivery |

#### Infrastructure
- **PostgreSQL 16** — Two isolated databases (users, careertwin) with Flyway schema migrations
- **Redis 7** — Distributed caching layer
- **Apache Kafka 3.6** — Async event streaming between services
- **Docker Compose** — Full local stack with health checks and dependency ordering
- **Kafka UI** — Web-based Kafka topic browser on port 9093

#### Developer Experience
- **Swagger UI** available on every service at `/swagger-ui.html`
- **OpenAPI 3.0** specs at `/v3/api-docs`
- **Spring Boot Actuator** — `/actuator/health`, `/actuator/info`, `/actuator/metrics`
- **JaCoCo** code coverage with 80% line coverage gate
- **MapStruct** entity-to-DTO mapping (zero reflection overhead)
- **Lombok** — Boilerplate-free Java
- **Testcontainers** — Integration tests with real PostgreSQL containers

#### CI/CD
- GitHub Actions pipeline: lint → build → unit tests → integration tests → Docker build → security scan
- Trivy vulnerability scanning on every push
- Per-service parallel build matrix
- Build summary published on main branch merges

---

### 🏗️ Architecture

```
Client → API Gateway (8080)
           ├── user-service       (8081) → PostgreSQL (users DB)
           ├── career-twin-service (8082) → PostgreSQL (careertwin DB)
           ├── prediction-service  (8083) → Redis
           ├── simulation-service  (8084) → Redis
           ├── job-market-service  (8085) → Redis
           ├── learning-service    (8086) → Redis
           └── notification-service (8087) → Kafka
                    ↕
               Kafka Event Bus ← all services publish events
                    ↕
               Redis Cache ← shared session + result cache
```

---

### 📦 Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 21 (LTS) |
| Framework | Spring Boot 3.2.5 |
| API Gateway | Spring Cloud Gateway 2023.0.1 |
| Security | Spring Security 6 + JWT (jjwt 0.12.5) |
| Persistence | Spring Data JPA + Hibernate 6 |
| Database | PostgreSQL 16 |
| Migrations | Flyway |
| Cache | Redis 7 |
| Messaging | Apache Kafka 3.6 |
| Mapping | MapStruct 1.5.5 |
| Documentation | SpringDoc OpenAPI 2.5.0 |
| Build | Maven 3.9 |
| Containers | Docker + Docker Compose |
| CI/CD | GitHub Actions |
| Testing | JUnit 5 + Mockito + Testcontainers |
| Coverage | JaCoCo (80% minimum) |

---

### 🔐 Security

- Stateless JWT authentication on all protected endpoints
- BCrypt password hashing with cost factor 12
- CORS configured at gateway level
- Non-root Docker container user (`skilldna`)
- JVM container memory limits (`-XX:MaxRAMPercentage=75`)
- No secrets in source code — all via environment variables

---

### 📋 API Endpoints (v1)

#### User Service (`/api/v1`)
| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/auth/register` | Public | Register new user |
| POST | `/auth/login` | Public | Login + get JWT |
| GET | `/users/me` | JWT | Get my profile |
| PATCH | `/users/me` | JWT | Update my profile |
| GET | `/users/{id}` | JWT | Get user by ID |
| PATCH | `/users/{id}/tier` | JWT | Update subscription tier |

#### Career Twin Service (`/api/v1/career-twin`)
| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/` | JWT | Create Career Digital Twin |
| GET | `/` | JWT | Get my Career Twin |
| PUT | `/` | JWT | Update Career Twin |
| DELETE | `/` | JWT | Delete Career Twin |
| GET | `/{userId}/admin` | JWT | Get profile by userId |

#### Prediction Service (`/api/v1/predictions`)
| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/career-path` | JWT | Predict optimal career paths |
| POST | `/salary` | JWT | Predict salary range |
| GET | `/user/{userId}/latest` | JWT | Get latest prediction |

---

### 🐛 Known Issues / Limitations

- `simulation-service`, `job-market-service`, `learning-service` are scaffolded with stub responses — full AI integration arrives in v1.1
- Redis caching of prediction results is mocked — Redis integration completes in v1.1
- Kafka consumers not yet active in v1.0 — event publishing is ready, consumers ship in v1.1
- Frontend Angular application is scaffolded but not yet functional — ships in v1.2

---

### 🔮 Roadmap

| Version | Target | Feature |
|---------|--------|---------|
| v1.1 | Q3 2026 | Python AI service (FastAPI + LLM), Redis caching activated, Kafka consumers |
| v1.2 | Q4 2026 | Angular 17 frontend, resume parser, LinkedIn import |
| v1.3 | Q1 2027 | Enterprise SSO, multi-tenant isolation, Kubernetes Helm charts |
| v2.0 | Q2 2027 | Real-time job matching, LLM interview coach, peer benchmarking |

---

### 👥 Contributors

| Role | Name |
|------|------|
| Founder & Lead Engineer | Ravikumar |
| Platform | Ravi Future Labs |
| Repository | https://github.com/ravigithubcse/skilldna-ai |

---

### 📄 License

MIT License — Copyright (c) 2026 Ravikumar, Ravi Future Labs

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
