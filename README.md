## Exploring Spring

The objective of this project is to showcase the implementation and setup of useful techniques and technologies using the Spring framework.

To do so, several proof-of-concept branches will be created, each focusing on a single technical concern.

- initial
    - layered-architecture
        - controller
            - multipart-upload
            - testing-mockmvc
            - openapi-swagger
        - security
            - testing-security
        - couchbase
            - n1ql-queries
            - testcontainers
            - document-versioning
    - minio
    - actuator-logging
    - webflux-coroutines
        - spring-events
    - redis
    - kafka
    - docker-compose

### Branch descriptions

| Branch | What it explores |
|--------|-----------------|
| `initial` | Bare Spring Boot + Kotlin; Gradle Kotlin DSL, health endpoint sanity check |
| `initial>layered-architecture` | Package structure: controller / service / repository / domain layers; sample end-to-end flow with an in-memory store |
| `layered-architecture>controller` | REST endpoints: request/response DTOs, Bean Validation, global `@ExceptionHandler`, error envelope |
| `controller>multipart-upload` | `@RequestPart` multipart endpoint for file upload; content-type validation, size limits |
| `controller>testing-mockmvc` | MockMvc + MockK + JUnit 5: controller layer unit tests, request builders, response assertions |
| `controller>openapi-swagger` | SpringDoc OpenAPI 3 + Swagger UI: schema annotations, bearer auth config, tag grouping |
| `layered-architecture>security` | Spring Security filter chain + JWT: issue, validate, and refresh tokens; route protection |
| `security>testing-security` | `@WithMockUser`, mock JWT filter in `@WebMvcTest`, secured endpoint integration tests |
| `layered-architecture>couchbase` | Spring Data Couchbase: connection config, `@Document` entity, `CouchbaseRepository` CRUD |
| `couchbase>n1ql-queries` | Custom `@Query` N1QL statements: filtering, sorting, cursor-based pagination, index hints |
| `couchbase>testcontainers` | Testcontainers Couchbase module: bucket bootstrap, full repository integration tests against a real DB in Docker |
| `couchbase>document-versioning` | Schema evolution strategy: `schemaVersion` field, read-time migration adapter, batch N1QL migration script |
| `initial>minio` | Minio S3-compatible client: bucket initialisation, object upload/download, pre-signed URL generation |
| `initial>actuator-logging` | Spring Actuator health/metrics/info endpoints + Logback JSON structured logging with MDC correlation IDs |
| `initial>webflux-coroutines` | Spring WebFlux reactive stack with Kotlin coroutines: `suspend` controllers, `Flow` responses, `coRouter` |
| `webflux-coroutines>spring-events` | `ApplicationEventPublisher` with coroutine-aware `@EventListener`: fire-and-forget async side-effects |
| `initial>redis` | Spring Data Redis: `RedisTemplate`, hash/string operations, TTL-based key expiry; use case — refresh token store and JWT blacklist for logout |
| `initial>kafka` | Spring Kafka: producer/consumer setup, topic config, `@KafkaListener`, error handling, dead-letter topic |
| `initial>docker-compose` | `docker-compose.yml` bringing up Couchbase + Minio + Redis + Kafka with bootstrap scripts and health checks |

## Project setup

The project was created through the Spring Initializr [here](https://start.spring.io/) with the following settings:

- Project: Gradle Project
- Language: Kotlin
- Spring Boot: 4.1.0
- Packaging: Jar
- Java: 25
- Dependencies: None

## Running the application

Make sure you have SDKMAN installed and the JDK 25 set as the default. The project uses Gradle to build and run the application.

```bash
./gradlew bootRun
```

## Running the tests

```bash
./gradlew test