FROM eclipse-temurin:17.0.7_7-jre-alpine

COPY build/*.jar /usr/app/application.jar

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ENTRYPOINT java -jar /usr/app/application.jar
