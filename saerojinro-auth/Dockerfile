FROM openjdk:23-jdk-slim

WORKDIR /app

COPY /build/libs/saerojinro-auth-0.0.1-SNAPSHOT.jar saerojinro-auth.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "saerojinro-auth.jar"]