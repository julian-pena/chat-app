FROM openjdk:17-jdk AS build
WORKDIR /app

# Copy files from the local directory (where Dockerfile is)
COPY src ./src
COPY .mvn ./.mvn
COPY pom.xml .
COPY mvnw .

# Set execution permission for Maven wrapper and run build
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk
VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080