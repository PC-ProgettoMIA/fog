#!/bin/bash
#Running MondoDB
#docker run -d  --rm --name vertx-mongo -p 27016:27017 mongo
#sleep 30
#Download code
#wget https://github.com/PC-ProgettoMIA/fog/archive/refs/heads/main.zip
#unzip main.zip
#wget -P /Users/enricognagnarella/Documents/ https://github.com/PC-ProgettoMIA/edge-fog/archive/refs/heads/main.zip
#unzip /Users/enricognagnarella/Documents/main.zip
#Deploy service
cd fog/
./gradlew :build
./gradlew :subscriber:run &
./gradlew :server:run &
