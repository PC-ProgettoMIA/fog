#!/bin/bash
cd fog/
./gradlew :build
./gradlew :subscriber:run &
./gradlew :server:run &
