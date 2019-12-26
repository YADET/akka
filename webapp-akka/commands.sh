#!/usr/bin/env bash
sudo apt-get install postgresql-9.5 postgresql-contrib-9.5 pgadmin3
/usr/bin/sudo -u postgres psql --command "CREATE USER scalauser WITH SUPERUSER PASSWORD 'scalapass'";
/usr/bin/sudo -u postgres createdb -O scalauser scaladb
/usr/bin/sudo -u postgres psql -d scaladb --command "SELECT user_code,password FROM users;"


sbt playGenerateSecret
sbt clean compile stage
cd target/universal/stage/
./bin/my-web-app

./bin/my-web-app -Dhttp.port=8080
./bin/my-web-app -Dhttp.port=8080 -Dapp.home=./target/universal/stage -Dhttp.port=8081 -Dpidfile.path=./server1.pid

stest/password123