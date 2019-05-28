@echo off

:: check javac present
javac -version 2>nul
if errorlevel 1 (
    echo build: javac not found!
    echo.
    pause
    exit %errorlevel%
)

set OLD_PATH=%cd%

set CURRENT_DIR_PATH=%~dp0
set CURRENT_DIR_PATH=%CURRENT_DIR_PATH:~0,-1%
for %%a in ("%CURRENT_DIR_PATH%") do set PROJECT_NAME=%%~na

set OUT_DIR_NAME=out
set OUT_PROJECT_PATH=%OUT_DIR_NAME%\%PROJECT_NAME%
set OUT_BIN_PATH=%OUT_PROJECT_PATH%\bin
set OUT_JRE_PATH=%OUT_PROJECT_PATH%\jre
set OUT_LIB_PATH=%OUT_PROJECT_PATH%\lib

set PACKAGE_NAME=org
set MAIN_JAVA_NAME=Main
set MAIN_JAVA_PATH=src\%PACKAGE_NAME%\%MAIN_JAVA_NAME%.java

cd /d "%CURRENT_DIR_PATH%"

rmdir /s /q "%OUT_DIR_NAME%" 2>nul
mkdir "%OUT_PROJECT_PATH%" "%OUT_BIN_PATH%" "%OUT_JRE_PATH%" "%OUT_LIB_PATH%"
for %%a in (INSTALL LICENSE README) do copy /y %%a "%OUT_PROJECT_PATH%" >nul

:: compile
javac -sourcepath src -encoding UTF-8 "%MAIN_JAVA_PATH%" -d "%OUT_LIB_PATH%"

:: make jar
jar cfe "%OUT_LIB_PATH%\%PROJECT_NAME%".jar "%PACKAGE_NAME%".%MAIN_JAVA_NAME% -C "%OUT_LIB_PATH%" .
rmdir /s /q "%OUT_LIB_PATH%\%PACKAGE_NAME%" 2>nul

cd /d "%OUT_BIN_PATH%"

:: make start script
echo @echo off > "%PROJECT_NAME%".bat
echo.>> "%PROJECT_NAME%".bat
echo java -version ^2^>nul >> "%PROJECT_NAME%".bat
echo if errorlevel 1 ( >> "%PROJECT_NAME%".bat
echo    echo %PROJECT_NAME%: java not found >> "%PROJECT_NAME%".bat
echo.>> "%PROJECT_NAME%".bat
echo    pause >> "%PROJECT_NAME%".bat
echo    exit 1 >> "%PROJECT_NAME%".bat
echo ) >> "%PROJECT_NAME%".bat
echo.>> "%PROJECT_NAME%".bat
echo java -jar ../lib/%PROJECT_NAME%.jar >> "%PROJECT_NAME%".bat
echo.>> "%PROJECT_NAME%".bat
echo pause >> "%PROJECT_NAME%".bat

cd /d "%CURRENT_DIR_PATH%\%OUT_DIR_NAME%"

:: zip project
jar -cMf "%PROJECT_NAME%".zip "%PROJECT_NAME%"

cd /d "%CURRENT_DIR_PATH%"

:: write build date
::echo %PROJECT_NAME% >> README
::echo build date: %date% >> README

:: set old_path
cd /d "%OLD_PATH%"
