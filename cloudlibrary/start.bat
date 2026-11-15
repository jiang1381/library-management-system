@echo off
chcp 936 >nul
setlocal
rem ============================================================
rem  云借阅-图书管理系统  启动脚本（Tomcat 9）
rem  如需更换 Tomcat / JDK，请修改下面两行
rem ============================================================
set "CATALINA_HOME=E:\apache-tomcat-9.0.118-windows-x64\apache-tomcat-9.0.118"
set "JAVA_HOME=E:\jdk\jdk-17"

set "PROJECT_DIR=%~dp0"
set "CATALINA_BASE=%PROJECT_DIR%deploy"

if not exist "%CATALINA_BASE%\webapps\cloudlibrary.war" (
    if exist "%PROJECT_DIR%target\cloudlibrary.war" (
        copy /Y "%PROJECT_DIR%target\cloudlibrary.war" "%CATALINA_BASE%\webapps\" >nul
    ) else (
        echo [错误] 没有找到 cloudlibrary.war，请先运行 build.bat 打包。
        pause
        exit /b 1
    )
)

echo ============================================================
echo   云借阅-图书管理系统
echo   启动中... 访问地址： http://localhost:8080/cloudlibrary/
echo   管理员账号： root / 123456
echo   关闭本窗口即停止服务
echo ============================================================
set "JAVA_OPTS=-Dfile.encoding=UTF-8"
call "%CATALINA_HOME%\bin\catalina.bat" run
