@echo off

cd database

echo Init data base
call postgresql_init.bat
echo .

cd ..
cd ..

echo Building project
call gradle clean bootJar
echo .

echo Starting application
call "java -jar %cd%\build\libs\real-estate-market.jar"
echo .

echo Deploy completed
pause