# Start with a base image that includes Java, for building the application
# Updated from openjdk to eclipse-temurin for better stability
FROM eclipse-temurin:17-jdk-jammy AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle configuration files and source code
# The Gradle Wrapper files are needed to run the build command
COPY gradlew gradlew.bat build.gradle settings.gradle .
COPY gradle ./gradle
COPY src ./src

# Use Gradle to package the application into a JAR file
# We are skipping tests to speed up the build, but you can remove -x test if needed
RUN ./gradlew build -x test

# --- Second Stage: Create the final, lightweight runtime image ---
# This is a much smaller image that only contains what's needed to run the app
FROM eclipse-temurin:17-jre-jammy

# Copy the built WAR file from the 'builder' stage. We're now building a WAR file!
# Note: Gradle's default output directory is build/libs
COPY --from=builder /app/build/libs/*.war app.war

# Expose the port that your Spring Boot application runs on (default is 8080)
EXPOSE 8080

# Define the command to run your application when the container starts
# We use the WAR file as the entrypoint
ENTRYPOINT ["java", "-jar", "app.war"]