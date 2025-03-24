FROM openjdk:23-jdk-slim

WORKDIR /app

COPY /saerojinro-admin/build/libs/saerojinro-admin-0.0.1-SNAPSHOT.jar saerojinro-admin.jar

EXPOSE 8082

ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "saerojinro-admin.jar", "--spring.profiles.active=prod"]