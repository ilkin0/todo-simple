#!/bin/bash
number=${number:-'42'}
fromvm=${fromvm:-'no'}
while [ $# -gt 0 ]; do

  if [[ $1 == *"--"* ]]; then
    param="${1/--/}"
    declare $param="$2"
  fi

  shift
done

if [[ $fromvm == 'yes' ]]; then
  echo "Pull simple-todo app image"
  aws ecr get-login-password --region {region} | docker login --username {AWS} --password-stdin {aws_account_id}.dkr.ecr.{us-west-2}.amazonaws.com 2>&1
  docker pull {aws_account_id}.dkr.ecr.{us-west-2}.amazonaws.com/{amazonlinux}:latest 2>&1
fi

if [[ $fromvm == 'no' ]]; then
  echo "Remove old db"
  docker container rm -f db 2>&1
  echo "Pull postgres image"
  docker pull postgres:12.3-alpine
  echo "Run postgres..."
  docker container run -d --name db --publish 5433:5432 -e POSTGRES_PASSWORD=admin postgres:12.3-alpine

  echo "Create db"
  sleep 5
  docker exec db bash -c "
psql -U postgres -c '
CREATE DATABASE tododb
    WITH
    OWNER = postgres
    ENCODING = "UTF8"
    CONNECTION LIMIT = -1;
    '"
fi
if [[ $fromvm == 'yes' ]]; then
  echo "create network"
  checkNetwork="$(docker network ls | grep 'booking')"
  [[ -z "${checkNetwork// /}" ]] && docker network create booking
  echo "Remove old web"
  docker container rm -f web 2>&1
  echo "Run web"
  docker container run -d --name web --network=booking --mount type=bind,source=/easybook/files,target=/usr/local/tomcat/files --publish 80:8080 gcr.io/easy-book-278822/easy-book:$number
fi
