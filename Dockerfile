FROM maven:3.8.6-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:8-jdk-alpine
LABEL authors="HP"
WORKDIR /app
COPY --from=build /app/target/smail-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9123
ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "app.jar"]