# Stage 1: Build the JAR using Maven
FROM openjdk:17-alpine AS build
WORKDIR /app

# Install Maven
RUN apk add --no-cache maven

# Copy pom.xml and source code to build the project
COPY app/pom.xml .
COPY app/src ./src

# Build the project and create the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Use the JAR in a lightweight OpenJDK image
FROM openjdk:17-alpine
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /app/target/app-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080