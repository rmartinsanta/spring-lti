#!/bin/bash
while true; do
  git pull
  awk '!/DEBUG/' docker-compose.yml > temp && mv temp docker-compose.yml
  awk '!/5005/' docker-compose.yml > temp && mv temp docker-compose.yml
  docker-compose up --build --abort-on-container-exit
  git clean -xdf
  git reset --hard
done
