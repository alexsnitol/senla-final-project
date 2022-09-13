@echo off

echo change CHCP to UTF-8
chcp 65001

echo Drop database if exist
"%programfiles%\PostgreSQL\10\bin\psql.exe" -U postgres -c "DROP DATABASE IF EXISTS real_estate_market"
echo.

echo Create database
"%programfiles%\PostgreSQL\10\bin\psql.exe" -U postgres -c "CREATE DATABASE real_estate_market ENCODING = 'UTF8'"
echo.

echo Insert data in database
"%programfiles%\PostgreSQL\10\bin\psql.exe" -U postgres -d "real_estate_market" -f "%cd%\postgresql_init.sql"
echo.
echo Database setup is complete
pause