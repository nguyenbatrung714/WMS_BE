## build stage ##
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build

WORKDIR /app
COPY . .

RUN mvn install -DskipTests=true

## run stage ##
FROM openjdk:17-jdk-alpine

RUN adduser -D vanguard

WORKDIR /run
COPY --from=build /app/target/vanguard-0.0.1-SNAPSHOT.jar /run/vanguard-0.0.1-SNAPSHOT.jar

RUN chown -r vanguard:vanguard /run

USER vanguard

EXPOSE 8080

ENTRYPOINT java -jar /run/vanguard-0.0.1-SNAPSHOT.jar
