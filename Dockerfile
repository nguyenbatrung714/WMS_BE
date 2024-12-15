## build stage ##
FROM maven:3.8.6-openjdk-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests=true

## run stage ##
FROM openjdk:17-jdk-alpine

RUN adduser -D techtribe

WORKDIR /run
COPY --from=build /app/target/techtribe-0.0.1-SNAPSHOT.jar /run/techtribe-0.0.1-SNAPSHOT.jar

RUN chown -r techtribe:techtribe /run

USER techtribe

EXPOSE 8080

ENTRYPOINT java -jar /run/techtribe-0.0.1-SNAPSHOT.jar
