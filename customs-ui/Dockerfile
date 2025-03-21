FROM eclipse-temurin:21.0.4_7-jre
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/application.jar"]