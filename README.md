## Exploring Spring

This branch sets up Spring Actuator. It provides endpoints for checking the health of the service and changing log levels at runtime.

## Project setup

This branch is based on **logging**.

## Running the application

Make sure you have SDKMAN installed and the JDK 25 set as the default. The project uses Gradle to build and run the application.

```bash
./gradlew bootRun
```

## Running the tests

```bash
./gradlew test
```

## Actuator endpoints

The following endpoints are exposed at `/actuator` and can be exercised with the `.http` files under `src/test/http/actuator/`.

| Endpoint | URL | Description |
|---|---|---|
| Health | `GET /actuator/health` | Overall status and component details |
| Info | `GET /actuator/info` | Application metadata (build version, git commit) |
| Loggers | `GET /actuator/loggers` | All loggers and their current levels |
| Metrics | `GET /actuator/metrics` | JVM, HTTP, and system metrics |

### Changing log levels at runtime

Log levels can be changed without restarting the application by posting to the `loggers` endpoint:

```bash
# Raise to DEBUG
curl -X POST http://localhost:8080/actuator/loggers/com.danielleitelima \
  -H "Content-Type: application/json" \
  -d '{"configuredLevel": "DEBUG"}'

# Raise to TRACE for deeper inspection
curl -X POST http://localhost:8080/actuator/loggers/com.danielleitelima \
  -H "Content-Type: application/json" \
  -d '{"configuredLevel": "TRACE"}'

# Reset to the default level from application.yaml
curl -X POST http://localhost:8080/actuator/loggers/com.danielleitelima \
  -H "Content-Type: application/json" \
  -d '{"configuredLevel": null}'
```

Any logger name can be used — the package prefix `com.danielleitelima` applies to all classes under it. A more specific name like `com.danielleitelima.exploring.spring.kt.feature.user.domain.service.UserServiceImpl` targets a single class.
