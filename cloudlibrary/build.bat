@echo off
chcp 936 >nul
setlocal
set "JAVA_HOME=E:\jdk\jdk-17"
set "PROJECT_DIR=%~dp0"
cd /d "%PROJECT_DIR%"
echo 正在编译打包...
call mvn -o clean package
if errorlevel 1 (
    echo [错误] 打包失败，请检查上面的输出。
    pause
    exit /b 1
)
if exist "%PROJECT_DIR%deploy\webapps\cloudlibrary" rmdir /S /Q "%PROJECT_DIR%deploy\webapps\cloudlibrary"
copy /Y "%PROJECT_DIR%target\cloudlibrary.war" "%PROJECT_DIR%deploy\webapps\" >nul
echo.
echo 打包完成： target\cloudlibrary.war
echo 已复制到： deploy\webapps\cloudlibrary.war
echo 重新执行 start.bat 即可生效（若 Tomcat 正在运行需先 stop.bat）。
