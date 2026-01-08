@echo off
echo ============================================
echo  Customs KIT - EDC Connector Stop
echo ============================================
echo.

REM Zum Verzeichnis wechseln
cd /d "%~dp0"

echo Stoppe EDC Connectoren...
docker compose down

echo.
echo EDC Connectoren gestoppt.
echo.
echo Um die Daten zu loeschen, fuehren Sie aus:
echo   docker compose down -v
echo.
pause
