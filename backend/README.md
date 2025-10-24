# StudyU Flow — Backend

Servicio backend de StudyU Flow para la gestión del rendimiento académico universitario. Construido con Spring Boot 3, expone APIs REST seguras con JWT, persiste en PostgreSQL y documenta el contrato con OpenAPI/Swagger. Incluye integración con Spring AI (Google GenAI) para funciones de asistencia.

## Stack técnico
- Lenguaje: Java 21
- Framework: Spring Boot 3.5.6 (Web, Security, Validation, Data JPA)
- Base de datos: PostgreSQL
- Seguridad: JWT (jjwt 0.11.5)
- Mapeo: MapStruct 1.6.3
- Documentación: springdoc-openapi (Swagger UI)
- IA: Spring AI (Google GenAI: gemini-2.5-flash) + chat memory JDBC
- Build: Maven, Docker multi-stage

## Arquitectura y módulos
- Configuración: `config/` (Security, Beans)
- Seguridad/JWT: `jwt/` (filtro y servicio de tokens)
- Dominio: `domain/` (DTOs, enums, excepciones, servicios)
- Persistencia: `persistence/` (entities, repositorios JPA, mappers)
- Web/API: `web/controller/` (controladores REST) y `web/exception/`

Controladores principales detectados:
- `AuthController` — Registro y login (`/auth/**`)
- `StudentController`, `DegreeController`, `SubjectController`, `EvaluationController`, `StudentDegreeController`
- `GradeStatisticsController` — estadísticas de notas
- `DocumentController` — documentos
- `AIChatController` — endpoints de chat IA

## Requisitos
- Java 21 (JDK) y Maven 3.9+
- Docker y Docker Compose (opcional, recomendado para entorno con Postgres)

## Configuración
Propiedades base: `src/main/resources/application.properties`
- Perfil activo por defecto: `dev`
- Driver PostgreSQL: `org.postgresql.Driver`
- Spring AI: requiere `GOOGLE_GENAI_API_KEY`

Perfil `dev`: `src/main/resources/application-dev.properties`
- JDBC URL por defecto (red Docker): `jdbc:postgresql://postgres:5432/studyu_flow_db`
- Usuario: `azocar` — Password: `studyu_flow_2025_admin`
- `spring.jpa.hibernate.ddl-auto=update`

Si ejecutas la API sin Docker para Postgres, cambia la URL a `jdbc:postgresql://localhost:5432/studyu_flow_db` o exporta `SPRING_DATASOURCE_URL`.

Variables de entorno relevantes:
- `GOOGLE_GENAI_API_KEY` (obligatoria para endpoints de IA)

## Seguridad de credenciales y variables
- Las credenciales definidas en `application-*.properties` y `docker-compose.yaml` son para desarrollo local.
- No las uses en producción. En entornos públicos, usa variables de entorno y/o secretos del proveedor (GitHub Actions, Docker, Kubernetes).
- Ejemplos de sobrescritura por entorno:
  - `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
  - `GOOGLE_GENAI_API_KEY`
- Recomendado: archivo `.env` (no versionado) para Compose y export de variables en tu shell.

## Ejecución
### Opción A: Docker Compose (API + Postgres)
Repositorio incluye `docker-compose.yaml` y `Dockerfile` multi-stage.

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

### Opción B: Local con Maven (Postgres aparte)
1) Inicia un Postgres local (o con Docker):
```bash
docker run --name studyu-postgres -e POSTGRES_DB=studyu_flow_db -e POSTGRES_USER=azocar -e POSTGRES_PASSWORD=studyu_flow_2025_admin -p 5432:5432 -d postgres:latest
```
2) Ajusta la URL si no usas Compose (localhost) y exporta la API key (opcional IA).
3) Compila y ejecuta:
```bash
mvn clean package
java -jar target/StudyU_Flow-0.0.1-SNAPSHOT.jar
# o
mvn spring-boot:run
```

## Documentación de API
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Estos endpoints están expuestos sin autenticación según `SecurityConfig`.

## Autenticación y seguridad
- Esquema: JWT stateless con filtro `JwtAuthenticationFilter`
- Endpoints públicos: `/auth/**`, Swagger (`/swagger-ui/**`, `/v3/api-docs/**`), webjars
- Resto de endpoints: requieren `Authorization: Bearer <token>`

Flujo básico:
1) Registro: `POST /auth/register`
2) Login: `POST /auth/login` → devuelve token JWT en `AuthResponseDto`
3) Usar token en llamadas subsecuentes via header `Authorization`

Ejemplo de login:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"UserNameExample","password":"0123456789"}'
```

## Endpoints destacados (no exhaustivo)
- Auth: `POST /auth/register`, `POST /auth/login`
- Estudiantes, Carreras (Degrees), Asignaturas, Evaluaciones, Estadísticas de notas, Documentos, Chat IA
Consulta Swagger para el detalle de rutas, cuerpos y respuestas.

## Pruebas
Ejecuta pruebas unitarias:
```bash
mvn test
```
Ubicación de tests: `src/test/java/com/api/StudyU_Flow/` (incluye servicios como `EvaluationServiceTest`, `GradeStatisticsServiceTest`).

## Mapeo y DTOs
- MapStruct para mapeos entre entidades y DTOs
- Validación con `jakarta.validation` en payloads de entrada

## Diagrama del modelo de datos
Inserta aquí el diagrama ER de la base de datos cuando esté disponible.
Sugerencia de ubicación del archivo:
- `docs/db/er-diagram.png`

Referencia en Markdown (cuando exista):
```markdown
![Diagrama ER](docs/db/er-diagram.png)
```

## Despliegue
- Imagen construida con `Dockerfile` (OpenJDK 21 runtime)
- Puerto expuesto: `8080`
- Dependencia de Postgres vía Compose (`postgres:latest`)

## Resolución de problemas
- Error de conexión a BD: verifica `spring.datasource.url`, usuario/password y que Postgres esté escuchando en `5432`
- Swagger no carga: confirma que la app está en `8080` y que `springdoc` está presente
- IA falla: define `GOOGLE_GENAI_API_KEY` y revisa el modelo `gemini-2.5-flash`

## Contribución
- Estilo: mantiene convenciones existentes (paquetes por capa, DTOs inmutables donde aplique)
- Pull requests con tests y descripción técnica

## Licencia
Este repositorio no declara licencia explícita. Añade un archivo `LICENSE` antes de publicar si deseas abrir el código bajo una licencia OSS (p. ej., MIT/Apache-2.0).
