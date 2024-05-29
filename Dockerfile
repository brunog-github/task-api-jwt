FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN ./gradlew build --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /build/libs/dev.bruno.task-api-jwt-0.0.1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]