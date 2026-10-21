@echo off
setlocal
cd /d "%~dp0"
where mvn >nul 2>nul
if errorlevel 1 (
  echo No se encontro Maven en PATH.
  echo Instale Apache Maven o abra este proyecto desde un IDE compatible con Maven, como NetBeans.
  echo Consulte README.md para instrucciones de configuracion.
  pause
  exit /b 1
)
echo Ejecutando TiendaControl...
mvn clean compile exec:java
if errorlevel 1 (
  echo.
  echo Revise que MySQL este iniciado y que haya ejecutado el archivo SQL.
  echo Configure MYSQL_USER, MYSQL_PASSWORD y MYSQL_URL si su configuracion es diferente.
  pause
  exit /b 1
)
pause
