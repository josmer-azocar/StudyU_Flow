# 🍃📚 StudyU Flow (Mentor Academico con IA)
<img width="600" height="600" alt="Generated Image October 20, 2025 - 5_04PM - Edited" src="https://github.com/user-attachments/assets/9ad5483a-b433-48a1-b654-5a2df90cae5b" />

Backend de StudyU Flow para la gestión del rendimiento académico universitario. Construido con Spring Boot 3, expone APIs REST seguras con JWT, persiste en PostgreSQL y documenta el contrato con OpenAPI/Swagger. Incluye capacidades de IA (Spring AI + Google GenAI) para soporte conversacional.

- Estado: En desarrollo activo
- Lenguaje: Java 21
- Objetivo: Plataforma para registrar estudiantes, carreras, asignaturas, evaluaciones y obtener estadísticas/insights de notas. 

## Características clave
- API REST con Spring Boot 3.5.6 (Web, Security, Validation, Data JPA)
- Autenticación JWT stateless
- Persistencia en PostgreSQL con JPA/Hibernate
- Mapeo entre entidades y DTOs con MapStruct 1.6.3
- Documentación de API con springdoc-openapi (Swagger UI)
- IA conversacional con Spring AI (Google GenAI) y chat memory JDBC
- Empaquetado con Maven y soporte Docker/Docker Compose

## Arquitectura (capas y módulos)
- Configuración (`config/`): Beans, configuración de seguridad, etc.
- Seguridad (`jwt/`): Filtro y servicio de emisión/validación de tokens JWT
- Dominio (`domain/`): DTOs, enums, excepciones, servicios de dominio
- Persistencia (`persistence/`): Entities, repositorios JPA, mappers MapStruct
- Web/API (`web/controller/`, `web/exception/`): Controladores REST y manejo de errores

## Diagrama del modelo de datos
<img width="800" height="1000" alt="StudyU Flow DB" src="https://github.com/user-attachments/assets/11961c23-9dad-4979-8b7d-46133026b244" />

  
Controladores principales:
- `AuthController` — Registro y login (`/auth/**`)
- `StudentController`, `DegreeController`, `SubjectController`, `EvaluationController`, `StudentDegreeController`
- `GradeStatisticsController` — Estadísticas de notas
- `DocumentController` — Documentos
- `AIChatController` — Endpoints de chat IA

## Stack técnico
- Java 21
- Spring Boot 3.5.6
- Spring Data JPA, Spring Security, Validation
- PostgreSQL (driver oficial)
- JWT (jjwt 0.11.5)
- MapStruct 1.6.3
- springdoc-openapi 2.8.13
- Spring AI (BOM 1.1.0-M1, Google GenAI + chat-memory JDBC)
- Maven, Docker/Docker Compose

## Inicio rápido
Prerequisitos:
- Java 21 (JDK) y Maven 3.9+
- Docker y Docker Compose (recomendado para entorno local)
- (Opcional IA) Clave de Google GenAI: `GOOGLE_GENAI_API_KEY`

### Opción A: Docker Compose (API + Postgres)
El repositorio incluye `docker-compose.yaml` y `Dockerfile` multi-stage.

1) Exporta la API key si usarás IA:
```bash
# PowerShell
$env:GOOGLE_GENAI_API_KEY="<tu_api_key>"
# Bash
export GOOGLE_GENAI_API_KEY="<tu_api_key>"
```

2) Levanta servicios:
```bash
docker compose up --build
```

3) API disponible en: http://localhost:8080  
4) Swagger UI: http://localhost:8080/swagger-ui/index.html

### Opción B: Local con Maven (Postgres aparte)
1) Inicia un Postgres local (o en Docker) con tu usuario/contraseña preferidos:
```bash
docker run --name studyu-postgres \
  -e POSTGRES_DB=studyu_flow_db \
  -e POSTGRES_USER=<usuario> \
  -e POSTGRES_PASSWORD=<password> \
  -p 5432:5432 -d postgres:latest
```

2) Configura las variables de entorno (ver sección “Configuración”) y, si usarás IA:
```bash
export GOOGLE_GENAI_API_KEY="<tu_api_key>"
```

3) Compila y ejecuta:
```bash
mvn clean package
java -jar target/StudyU_Flow-0.0.1-SNAPSHOT.jar
# o durante desarrollo
mvn spring-boot:run
```

## Configuración
Archivos base: `src/main/resources/application.properties`  
Perfil por defecto: `dev` (sobre-escribible con `SPRING_PROFILES_ACTIVE`)

- Driver PostgreSQL: `org.postgresql.Driver`
- JPA/Hibernate: `spring.jpa.hibernate.ddl-auto=update` (en dev)
- Spring AI: requiere `GOOGLE_GENAI_API_KEY` si se usan endpoints de IA

Variables de entorno recomendadas (no incluir credenciales reales en archivos versionados):
- `SPRING_DATASOURCE_URL` (ej. `jdbc:postgresql://localhost:5432/studyu_flow_db`)
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `GOOGLE_GENAI_API_KEY`

Sugerencia: usar un archivo `.env` local (excluido del VCS) para Compose o exportar variables en tu shell.

## Seguridad y autenticación
- Esquema: JWT (stateless) con `JwtAuthenticationFilter`
- Endpoints públicos: `/auth/**`, Swagger (`/swagger-ui/**`, `/v3/api-docs/**`)
- Resto de endpoints: requieren `Authorization: Bearer <token>`

Flujo:
1) Registro: `POST /auth/register`
2) Login: `POST /auth/login` → devuelve `token` JWT
3) Consumir endpoints protegidos con header: `Authorization: Bearer <token>`

Ejemplo de login:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"UserNameExample","password":"0123456789"}'
```

## Documentación de API
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Consulta Swagger para ver rutas, cuerpos y ejemplos de respuesta.

## Pruebas
- Ejecutar pruebas: `mvn test`
- Ubicación de tests: `src/test/java/com/api/StudyU_Flow/`  
  Incluye pruebas de servicios como `EvaluationServiceTest`, `GradeStatisticsServiceTest`.

## Estructura del proyecto (resumen)
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

## Decisiones técnicas
- DTOs y mappers: MapStruct para reducir boilerplate y asegurar contratos estables hacia el exterior.
- Validación: `jakarta.validation` en payloads de entrada para robustez.
- Seguridad: JWT para sesiones stateless y escalabilidad horizontal.
- Observabilidad/doc: springdoc-openapi para describir y probar la API desde Swagger UI.
- IA: Spring AI con proveedor Google GenAI para enriquecer la experiencia con chat contextual y memoria vía JDBC.

## Resolución de problemas
- Conexión a BD: revisa `SPRING_DATASOURCE_*` y que Postgres escuche en `5432`
- Swagger no carga: confirma que la app corre en `8080` y `springdoc` está incluido
- IA falla: define `GOOGLE_GENAI_API_KEY` y valida el modelo (ej. `gemini-2.5-flash`) en configuración

## Contribución
- Mantén el estilo por capas (config/jwt/domain/persistence/web)
- Acompaña tus PRs con descripción técnica y tests
