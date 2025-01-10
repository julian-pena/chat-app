FROM openjdk:17-jdk AS build

# Set working directory
WORKDIR /app

# Copy the app directory contents
COPY app/pom.xml ./
COPY app/src ./src
COPY app/mvnw ./
COPY app/.mvn ./.mvn

# Set execution permission for Maven wrapper and run build
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080