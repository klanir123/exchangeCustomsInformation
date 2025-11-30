@echo off
echo ========================================
echo Customs Information System
echo Starting Backend Application with H2 Database
echo ========================================
echo.

cd /d "%~dp0\customs-backend-app"

echo Starting application on http://localhost:8080
echo Press CTRL+C to stop the server
echo.

mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=h2"

pause
