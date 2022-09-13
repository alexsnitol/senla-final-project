@echo off

cd database

echo Init data base
call postgresql_init.bat
echo .

cd ..
cd ..

echo Building project on WAR file
call gradle war
echo .

echo Copy WAR to Tomcat web application folder
copy "%cd%\build\libs\senla-final-project-1.0-SNAPSHOT.war" "%CATALINA_HOME%\webapps\real-estate-market.war"
echo .

echo Start up Tomcat server
call "%CATALINA_HOME%\bin\startup.bat"
echo .

echo Deploy completed
pause