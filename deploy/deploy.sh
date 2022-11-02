#!/bin/bash

cd ..

echo Building project
gradle clean bootJar
echo .

echo Starting application
java jar ./build/libs/real-estate-market.jar
echo .

echo Deploy completed