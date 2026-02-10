FROM eclipse-temurin:25-jdk-alpine

LABEL authors="Дима Полегенький"

COPY target/*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "/app.jar"]
