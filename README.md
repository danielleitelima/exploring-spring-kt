## Exploring Spring

The focus on this branch is to showcase the implementation of structured logging. It implements a Logger that can be injected on any bean and logs every request received as well with Spring's **OncePerRequest**. 

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