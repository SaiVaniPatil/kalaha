# Kalaha Game
2 player kalaha board game.

## Environment
    - Java
    - Maven version: 3.8.8
    - Spring Boot version: 3.0.6

## Server-Side:
- Spring Boot framework, java an h2 database
- Running on port 8080 -> http://localhost:8090
- Access h2 database : http://localhost:8090/h2-console , user : sa, url=jdbc:h2:mem:testdb

- To start server, use below commands:
    - clone this git repository (or) initiate pull request
    - Install maven if not installed already
    - Navigate to project folder and execute below commands

    # To run application: 
    mvn spring-boot:run 

    # To run test cases:
    mvn clean test


## Getting started

- Ensure that port 8090 is not blocked and server is running

## REST API Endpoints:

 - POST /v1/kalaha : Creater a game with 2 players and 14 pits.
 - PATCH /v1/kalaha/play : updated game , players and pits whenever a pit is selected by any player. Need to pass GameInput as request body containing gameId and selected pitNum.

ex : 

 {
    "gameId":1,
    "pitNum":1
}



