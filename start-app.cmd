@echo off
echo ========================================
echo Customs Information System
echo Starting Backend and UI
echo ========================================
echo.

cd /d "%~dp0"

echo Starting Backend (Port 8080)...
start "Customs Backend" cmd /c "cd customs-backend-app && mvn spring-boot:run -Dspring-boot.run.arguments=\"--spring.profiles.active=h2\""

echo Waiting for Backend to initialize...
timeout /t 10 /nobreak > nul

echo Starting UI (Port 9090)...
start "Customs UI" cmd /c "cd customs-ui && mvn spring-boot:run"

echo.
echo ========================================
echo Applications starting in separate windows:
echo   Backend: http://localhost:8080
echo   UI:      http://localhost:9090
echo ========================================
echo.
echo Close the application windows to stop the servers.
pause
