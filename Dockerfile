FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/mini-erp-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
