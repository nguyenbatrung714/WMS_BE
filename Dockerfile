## build stage ##
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build

WORKDIR /app
COPY . .

RUN mvn install -DskipTests=true

## run stage ##
FROM openjdk:17-jdk-alpine

RUN adduser -D techtribe

WORKDIR /run
COPY --from=build /app/target/WMS_BE-0.0.1-SNAPSHOT.jar /run/WMS_BE-0.0.1-SNAPSHOT.jar

RUN chown -r techtribe:techtribe /run

USER techtribe

EXPOSE 8080

ENTRYPOINT java -jar /run/WMS_BE-0.0.1-SNAPSHOT.jar
