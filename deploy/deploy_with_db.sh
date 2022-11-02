#!/bin/bash

echo Init data base

cd ./database
/bin/bash ./postgresql_init.sh
echo .

cd ..
cd ..

echo Building project
gradle clean bootJar
echo .

echo Starting application
java -jar ./build/libs/real-estate-market.jar
echo .
