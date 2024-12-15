# Build stage
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY . .
COPY application.properties /app/config/application.properties
RUN mvn clean package

# Run stage
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/techtribe-0.0.1-SNAPSHOT.jar /app/techtribe-0.0.1-SNAPSHOT.jar

# Ensure the application properties file is accessible
COPY --from=build /app/config/application.properties /app/config/application.properties

# Run the application with the config location specified
CMD ["java", "-jar", "/app/techtribe-0.0.1-SNAPSHOT.jar", "--spring.config.location=file:/app/config/application.properties"]

EXPOSE 8080