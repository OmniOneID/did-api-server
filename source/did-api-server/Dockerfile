FROM eclipse-temurin:21-jdk-jammy

ARG JAR_FILE=build/libs/*.jar
WORKDIR /app

COPY ${JAR_FILE} server.jar

RUN mkdir -p /app/config

CMD ["java", "-jar", "/app/server.jar", "--spring.config.additional-location=/app/config/"]