# Use the official OpenJDK 22 image as the base image
FROM openjdk:22-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
COPY target/security-0.0.1-SNAPSHOT.jar /app/security-0.0.1-SNAPSHOT.jar

# Expose the port the Spring Boot application runs on
EXPOSE 9000

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "security-0.0.1-SNAPSHOT.jar"]

