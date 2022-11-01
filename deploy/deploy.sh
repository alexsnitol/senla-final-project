cd ..

echo Building project on WAR file
gradle war
echo .

echo Copy WAR to Tomcat web application folder
copy "./build/libs/real-estate-market.war" "$CATALINA_HOME/webapps/real-estate-market.war"
echo .

echo Deploy completed