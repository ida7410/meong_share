# Build stage
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar -x test && ls -l build/libs

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/meong_share-0.0.1-SNAPSHOT.jar app.jar
RUN ls -l /app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]