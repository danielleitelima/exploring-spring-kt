## Exploring Spring

This branch implements the Dockerfile, allowing the Spring application to be easily deployed in a Docker container.


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