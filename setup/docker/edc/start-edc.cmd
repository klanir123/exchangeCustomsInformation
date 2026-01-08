@echo off
echo ============================================
echo  Customs KIT - EDC Connector Setup
echo ============================================
echo.

REM Zum Verzeichnis wechseln
cd /d "%~dp0"

echo Starte EDC Connectoren...
echo.

REM Pruefen ob Docker laeuft
docker info > nul 2>&1
if errorlevel 1 (
    echo FEHLER: Docker ist nicht gestartet!
    echo Bitte starten Sie Docker Desktop und versuchen Sie es erneut.
    pause
    exit /b 1
)

REM Starten der Container
docker compose up -d

if errorlevel 1 (
    echo FEHLER beim Starten der Container!
    echo Bitte pruefen Sie die Logs mit: docker compose logs
    pause
    exit /b 1
)

echo.
echo ============================================
echo  EDC Connectoren gestartet!
echo ============================================
echo.
echo  Connector A (OEM):
echo    UI:  http://localhost:11000
echo    API: http://localhost:11002/api/management
echo.
echo  Connector B (Supplier):
echo    UI:  http://localhost:22000
echo    API: http://localhost:22002/api/management
echo.
echo  API-Key: ApiKeyDefaultValue
echo.
echo  Warte auf Container-Start (30 Sekunden)...
timeout /t 30 /nobreak > nul

echo.
echo  Pruefe Health Status...
echo.

curl -s -o nul -w "  Connector A: %%{http_code}\n" http://localhost:11001/api/check/health
curl -s -o nul -w "  Connector B: %%{http_code}\n" http://localhost:22001/api/check/health

echo.
echo  Druecken Sie eine Taste um das Fenster zu schliessen...
pause > nul
