# syntax=docker/dockerfile:1

FROM azul/zulu-openjdk:11.0.11-11.48.21

WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]