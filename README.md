# 🍃📚 StudyU Flow — AI-Powered Academic Mentor

<p align="center">
<img width="600" height="600" alt="StudyU Flow — Generated Image" src="https://github.com/user-attachments/assets/9ad5483a-b433-48a1-b654-5a2df90cae5b" />
</p>
<p align="center">
  <a href="#overview">Overview</a> •
  <a href="#key-features">Key Features</a> •
  <a href="#architecture">Architecture</a> •
  <a href="#data-model-diagram">Data Model</a> •
  <a href="#getting-started">Getting Started</a> •
  <a href="#security--auth">Security</a> •
  <a href="#api-documentation">API Docs</a> •
  <a href="#testing">Testing</a> •
  <a href="#troubleshooting">Troubleshooting</a> •
  <a href="#contributing">Contributing</a>
</p>

<p align="center">
  <img alt="Status" src="https://img.shields.io/badge/Status-Active%20Development-3fb950?style=flat-square" />
  <img alt="Java" src="https://img.shields.io/badge/Java-21-007396?style=flat-square&logo=java&logoColor=white" />
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-3.5.6-6DB33F?style=flat-square&logo=springboot&logoColor=white" />
  <img alt="Build" src="https://img.shields.io/badge/Build-Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white" />
  <img alt="Database" src="https://img.shields.io/badge/Database-PostgreSQL-336791?style=flat-square&logo=postgresql&logoColor=white" />
  <img alt="Docker" src="https://img.shields.io/badge/Docker-Ready-2496ED?style=flat-square&logo=docker&logoColor=white" />
</p>

## Overview

Backend for StudyU Flow, a platform to manage university academic performance. Built with Spring Boot 3, it exposes secure JWT-based REST APIs, persists data in PostgreSQL, and documents the contract with OpenAPI/Swagger. It also integrates an AI chat assistant powered by Spring AI (Google GenAI) with JDBC chat memory.

- Status: Active development
- Language: Java 21
- Goal: Register students, degrees, subjects, evaluations, and retrieve grade statistics/insights.

## Key Features

- REST API with Spring Boot 3.5.6 (Web, Security, Validation, Data JPA)
- Stateless JWT authentication
- PostgreSQL persistence with JPA/Hibernate
- Entity-to-DTO mapping via MapStruct 1.6.3
- API documentation using springdoc-openapi (Swagger UI)
- Conversational AI with Spring AI (Google GenAI) + JDBC chat memory
- Maven build; Docker/Docker Compose support

## Architecture

- Configuration (`config/`): Beans, security configuration, etc.
- Security (`jwt/`): JWT filter and token issuance/validation services
- Domain (`domain/`): DTOs, enums, exceptions, domain services
- Persistence (`persistence/`): Entities, JPA repositories, MapStruct mappers
- Web/API (`web/controller/`, `web/exception/`): REST controllers and error handling

### Controllers (main endpoints)

- `AuthController` — Registration and login (`/auth/**`)
- `StudentController`, `DegreeController`, `SubjectController`, `EvaluationController`, `StudentDegreeController`
- `GradeStatisticsController` — Grade statistics and insights
- `DocumentController` — Documents
- `AIChatController` — AI chat endpoints

## Data Model Diagram
<img width="800" height="1000" alt="StudyU Flow DB (1)" src="https://github.com/user-attachments/assets/7906a427-3e4a-4ec6-9c71-dc0784404c69" />



## Tech Stack

- Java 21
- Spring Boot 3.5.6
- Spring Data JPA, Spring Security, Bean Validation
- PostgreSQL (official driver)
- JWT (jjwt 0.11.5)
- MapStruct 1.6.3
- springdoc-openapi 2.8.13
- Spring AI (BOM 1.1.0-M1, Google GenAI + JDBC chat memory)
- Maven, Docker/Docker Compose

## Getting Started

Prerequisites:
- Java 21 (JDK) and Maven 3.9+
- Docker and Docker Compose (recommended for local setup)
- Optional AI: Google GenAI API Key `GOOGLE_GENAI_API_KEY`

### Option A: Docker Compose (API + PostgreSQL)

The repository includes a multi-stage `Dockerfile` and `docker-compose.yaml`.

1) Export your API key if you will use AI:
```bash
# PowerShell
$env:GOOGLE_GENAI_API_KEY="<your_api_key>"
# Bash
export GOOGLE_GENAI_API_KEY="<your_api_key>"
```

2) Start services:
```bash
docker compose up --build
```

3) API: http://localhost:8080  
4) Swagger UI: http://localhost:8080/swagger-ui/index.html

### Option B: Local with Maven (PostgreSQL external)

1) Start a local PostgreSQL (or via Docker) with your preferred credentials:
```bash
docker run --name studyu-postgres \
  -e POSTGRES_DB=studyu_flow_db \
  -e POSTGRES_USER=<user> \
  -e POSTGRES_PASSWORD=<password> \
  -p 5432:5432 -d postgres:latest
```

2) Configure environment variables (see “Configuration”). If you’ll use AI:
```bash
export GOOGLE_GENAI_API_KEY="<your_api_key>"
```

3) Build and run:
```bash
mvn clean package
java -jar target/StudyU_Flow-0.0.1-SNAPSHOT.jar
# or during development
mvn spring-boot:run
```

## Configuration

Base files: `src/main/resources/application.properties`  
Default profile: `dev` (override with `SPRING_PROFILES_ACTIVE`)

- PostgreSQL Driver: `org.postgresql.Driver`
- JPA/Hibernate: `spring.jpa.hibernate.ddl-auto=update` (in dev)
- Spring AI: requires `GOOGLE_GENAI_API_KEY` if AI endpoints are used

Recommended environment variables (avoid committing real credentials):
- `SPRING_DATASOURCE_URL` (e.g., `jdbc:postgresql://localhost:5432/studyu_flow_db`)
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_PROFILES_ACTIVE` (e.g., `dev`)
- `GOOGLE_GENAI_API_KEY`

Tip: Use a local `.env` (excluded from VCS) for Compose or export variables in your shell.

## Security & Auth

- Scheme: JWT (stateless) with `JwtAuthenticationFilter`
- Public endpoints: `/auth/**`, Swagger (`/swagger-ui/**`, `/v3/api-docs/**`)
- All other endpoints: require `Authorization: Bearer <token>`

Flow:
1) Register: `POST /auth/register`  
2) Login: `POST /auth/login` → returns JWT `token`  
3) Call protected endpoints with header: `Authorization: Bearer <token>`

Login example:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"UserNameExample","password":"0123456789"}'
```

## API Documentation

- Swagger UI: http://localhost:8080/swagger-ui/index.html  
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Use Swagger to view routes, request bodies, and response examples.

## Testing

- Run tests: `mvn test`
- Tests location: `src/test/java/com/api/StudyU_Flow/`  
  Includes service tests like `EvaluationServiceTest`, `GradeStatisticsServiceTest`.

## Project Structure (summary)

```
backend/
 ├─ src/main/java/com/api/StudyU_Flow/...
 │   ├─ config/
 │   ├─ jwt/
 │   ├─ domain/
 │   ├─ persistence/
 │   └─ web/
 ├─ src/main/resources/
 │   ├─ application.properties
 │   └─ application-dev.properties
 ├─ src/test/java/...
 ├─ pom.xml
 ├─ Dockerfile
 └─ docker-compose.yaml
```

## Technical Decisions

- DTOs and mappers: MapStruct reduces boilerplate and stabilizes external contracts.
- Validation: `jakarta.validation` for robust input payloads.
- Security: JWT for stateless sessions and horizontal scalability.
- Observability/docs: springdoc-openapi to describe and test the API via Swagger UI.
- AI: Spring AI with Google GenAI to enable contextual chat with JDBC-backed memory.

## Troubleshooting

- Database connection: verify `SPRING_DATASOURCE_*` and that PostgreSQL listens on `5432`.
- Swagger not loading: confirm the app runs on port `8080` and `springdoc` is included.
- AI errors: set `GOOGLE_GENAI_API_KEY` and ensure the configured model (e.g., `gemini-2.5-flash`) is valid.

## Contributing

- Keep the layered style (config/jwt/domain/persistence/web).
- Accompany your PRs with a technical description and tests.
