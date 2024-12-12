# Stage 1: Build the Spring Boot application
FROM maven:3.9-eclipse-temurin-21-jammy AS build

# Set the working directory and copy the pom.xml and source code
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build the application (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Stage 2: Run the Spring Boot application
FROM eclipse-temurin:21-jammy AS runtime

# Set the working directory and copy the built JAR from the build stage
WORKDIR /app
COPY --from=build /app/target/*.jar /app/bookstore.jar

# Expose the port the Spring Boot app runs on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "bookstore.jar"]
