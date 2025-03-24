FROM openjdk:23-jdk-slim

WORKDIR /app

COPY /saerojinro-auth/build/libs/saerojinro-auth-0.0.1-SNAPSHOT.jar saerojinro-auth.jar

EXPOSE 8081

ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "saerojinro-auth.jar", "--spring.profiles.active=prod"]