FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/techtribe-0.0.1-SNAPSHOT.jar app.jar
CMD [ "java", "-jar", "app.jar" ]
EXPOSE 8080