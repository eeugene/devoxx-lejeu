# devoxx-lejeu

## arena-api
* requires Mysql on localhost:3306
* requires leaderboard-app to be started on localhost:8081

## hero/app
* requires npm3 and nodejs (v6.4.0+) installed
* To run application:
* execute 'npm i' then 'npm run api' in a terminal
* execute 'npm start' in another terminal
* the application is running on "localhost:8040/"

## Docker
* requires docker installed
* To build application (ex arena-api)
* execute mvn package docker:build
* then docker run -t --name arena -p 8080:8080 arena-api
