# syntax=docker/dockerfile:1.7

FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /workspace

COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q -DskipTests package

FROM eclipse-temurin:21-jre-alpine AS runtime

RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /app

COPY --from=build --chown=spring:spring \
    /workspace/target/space-operation-center-1.0.0.jar \
    /app/application.jar

USER spring:spring
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/application.jar"]
