@echo off
cd customs-backend-app
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=h2" > app-output.log 2>&1
