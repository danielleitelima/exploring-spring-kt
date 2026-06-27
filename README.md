## Exploring Spring

Instead of using an in-memory cache, this branch sets up PostgreSQL to be used as persistence.

## Project setup

This branch is based on **layered-architecture**.

## Running the application

Make sure you have SDKMAN installed and the JDK 25 set as the default. The project uses Gradle to build and run the application.

```bash
./gradlew bootRun
```

## Running the tests

```bash
./gradlew test