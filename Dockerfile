# ── Stage 1: build ────────────────────────────────────────────────────────────
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /workspace

COPY gradle/ gradle/
COPY gradlew settings.gradle.kts build.gradle.kts ./
RUN ./gradlew dependencies --no-daemon --quiet

COPY src/ src/
RUN ./gradlew bootJar --no-daemon --quiet

# ── Stage 2: extract layers ───────────────────────────────────────────────────
FROM eclipse-temurin:25-jdk-alpine AS extract
WORKDIR /workspace

COPY --from=build /workspace/build/libs/*.jar app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --launcher

# ── Stage 3: runtime ──────────────────────────────────────────────────────────
FROM eclipse-temurin:25-jre-alpine AS runtime

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

COPY --from=extract /workspace/app/dependencies/           ./
COPY --from=extract /workspace/app/spring-boot-loader/     ./
COPY --from=extract /workspace/app/snapshot-dependencies/  ./
COPY --from=extract /workspace/app/application/            ./

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "org.springframework.boot.loader.launch.JarLauncher"]
