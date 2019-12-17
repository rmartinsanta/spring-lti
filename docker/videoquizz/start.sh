#!/bin/bash
#if [ "$(id -u)" != "0" ]; then
#   echo "This script must be run as root" 1>&2
#   exit 1
#fi
echo "[Videoquizz Launcher v1]"
echo "This script will remain active even if the main app crashes"
echo "-----------------------------------------------------------"
while true; do
        echo "Looking for container updates..."
        docker pull extremoblando/videoquizz:latest
        echo "Starting app..."
        # docker run -p 8080:80 extremoblando/videoquizz
        docker-compose up --abort-on-container-exit
        echo "[Videoquizz Launcher v1]"
        echo "App finished execution, checking lock file..."
        if [ ! -f keepRunning.lock ]; then
        echo "Lock file not present, exiting..."
        exit 0
        fi
        echo "Lock file exists, restarting app"
        echo "RESTARTING APP IN 10 SECONDS, use CTRL+C to STOP"
        sleep 5
        echo "RESTARTING APP IN 5"
        sleep 1

        echo "RESTARTING APP IN 4"
        sleep 1
        echo "RESTARTING APP IN 3"
        sleep 1
        echo "RESTARTING APP IN 2"
        sleep 1
        echo "RESTARTING APP IN 1"
        sleep 1
        echo "RESTARTING..."

done
