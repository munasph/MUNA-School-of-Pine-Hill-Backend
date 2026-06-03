# openjdk:17-jdk-slim was removed from Docker Hub — Temurin is the official replacement
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory in the container
WORKDIR /app

# Copy the project files to the container
COPY . .

# Grant execute permissions for the mvnw script
RUN chmod +x ./mvnw

# Build the application
RUN ./mvnw clean install -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/spring-boot-jpa-postgresql-0.0.1-SNAPSHOT.jar"]
