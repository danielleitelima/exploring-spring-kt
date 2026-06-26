## Exploring Spring

This branch focuses on showcasing the implementation of a project using layered clean architecture to keep the code decoupled and scalable.

### Structure

Using this approach, a project can be divided into features, that can be either packages or Gradle modules. Shared code between features could be kept into their own packages or modules, like "logging" or "persistence".

This is an example of how the structure can look like:

- src
  - feature
    - [feature_name]
      - data
        - repository: Repository implementation
        - local: Data Access objects and entities.
        - remote: Data transfer objects and remote requests
      - domain
        - model: Model classes used by the services and repositories.
        - repository: Repository interfaces used by the services.
        - service: Business logic implementation using Spring services
      - presentation: Controllers and mappers
  - shared
    - [shared]: Custom setup and implementation of shared feature code.

## Project setup

This branch is based on **main**.

## Running the application

Make sure you have SDKMAN installed and the JDK 25 set as the default. The project uses Gradle to build and run the application.

```bash
./gradlew bootRun
```

## Running the tests

```bash
./gradlew test