@echo off
chcp 936 >nul
setlocal
set "CATALINA_HOME=E:\apache-tomcat-9.0.118-windows-x64\apache-tomcat-9.0.118"
set "JAVA_HOME=E:\jdk\jdk-17"
set "PROJECT_DIR=%~dp0"
set "CATALINA_BASE=%PROJECT_DIR%deploy"
echo 正在停止 云借阅-图书管理系统 ...
call "%CATALINA_HOME%\bin\shutdown.bat"
echo 已发送停止指令。
