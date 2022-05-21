# Cake manager
### Pre-requisites
#### Server
* Java 17 SDK
* Maven
* Docker (optional)
#### Client
* Npm
* Docker (optional)

### Server
#### Maven build
    mvn clean package

#### Running server from command line
    mvn spring-boot:run

#### Docker - build
    cd cake-manager-server 
    docker build -t cake-manager-server:0.0.1-SNAPSHOT ./

#### Test
* Visit these two addresses to see the cakes list in json in a browser.  Alternatively, use the swagger link below.
    * http://localhost:8080/rest/cakes
    * http://localhost:8080/cakes

#### Swagger
* [Swagger UI](http://localhost:8080/swagger-ui/index.html)

#### Spring actuator
* [Actuator](http://localhost:8080/actuator)

###Client
#### Npm build
    mvn i

#### Running client from command line
    npm start

#### Docker - build
    cd cake-manager-client
    docker build -t cake-manager-client:0.0.1 ./

#### Test
*Note, the server must be running to test the UI*
* Visit http://localhost:3000/ to test the UI 

### Docker (both server and client)
#### start container
    docker compose up -d
#### stop container
    docker compose down

### Changes from original project
#### Server
* Modernised the server solution using Spring Boot and Spring data
* Fixed issue with naming of entity and colum names
* Altered column sizes (description column size the same as title)
* Replaced servlets with RestControllers
* Documented endpoints with Open API
* Added spring actuator - provides operational information about the running application
* Added swagger - helps with testing by providing access to the restful services from the web browser
* Added tests
#### Client
* The UI is completely missing.  Following the guidance to produce a client/server solution with the UI developed using React 