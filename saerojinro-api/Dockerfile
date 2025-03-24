FROM openjdk:23-jdk-slim

WORKDIR /app

COPY /saerojinro-api/build/libs/saerojinro-api-0.0.1-SNAPSHOT.jar saerojinro-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "saerojinro-api.jar", "--spring.profiles.active=prod"]