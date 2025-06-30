
# Use Eclipse Temurin Java 21 JDK as base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy project JAR to container
COPY target/*.jar app.jar

# Run JAR file
ENTRYPOINT ["java","-jar","app.jar"]