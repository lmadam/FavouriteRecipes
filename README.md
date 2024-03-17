# FavouriteRecipes

FavouriteRecipes is a microservice application designed to manage the favourite recipes of the user. with the help of this microservice we have exposed REST API's which will allow the user to add, update, fetch, delete and also search the recipes based on mutiple search parameters. 

## Architecture Overview

This is an application built using microservices architecture with Java, Springboot, REST webservices and Mongo DB. Below is the architectural system design UML diagram explaining the end to end flow.

  ![recipes_application_uml](https://user-images.githubusercontent.com/39440188/179500795-5c79b4fe-9efb-4d2b-aeff-84de928024cf.png)
  
  
## Tech stack and plugins used for Devlopment


- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17) - Used java 17 to leverage functional programming, Lambda Expression, Streams, Optional and record classes
- [Springboot version 2.7.3](https://spring.io/projects/spring-boot) - Server side framework
- [Apache Maven](https://maven.apache.org) - Managing dependencies and the build lifecycle of application.
- [Mongo DB](https://www.mongodb.com) - Used as the data for recipes will be unstructured and it will be faster to make complex queries
- [REST webservices](https://spring.io/guides/gs/rest-service/) - provide a standardized way for applications to exchange data over HTTP.
- [JWT](https://jwt.io/) - used for securely transmitting information between parties as a compact, self-contained set of claims
- [MAPSTRUCT](https://mapstruct.org/) - For mapping DTOs and Entities
- [LOMBOK](https://projectlombok.org) - used for reducing boiler code
- [JACOCO Plugin](https://www.eclemma.org/jacoco/) - used for measuring code coverage in Java applications to identify untested sections
- [OpenAPI-3](https://swagger.io/specification/) -  used for visualizing and interacting with a documented RESTful API. 


## API Layer and Authentication Mechanism

* For API authetication we are using Json Web Token (JWT).
* We are accepting username and password from the exposed authentication API and generate JWT based on shared credentials.
* Generated JWT will be passed in Authorization header as Bearer + JWT, and will be validated against the user for each request and is mandatory.
* The JWT is valid for 30 minutes and the value is configurable.

## Business Logic and Persistance

* All the business logic is handled at the service layer of the Application
* Mongo DB is used for persisting the Data

## API documentation
* OpenAPI-3 is used for the documentation of the API's.
* Once the Application is started the API documentation can be accessed using the below localhost URL
- [Swagger Documentation](http://localhost:8080/assignment/swagger-ui/index.html#/)
* Swagger documentation view appears as below

<img width="1650" alt="recipes_swagger" src="https://user-images.githubusercontent.com/39440188/179609947-98347f72-3140-4781-ae55-71a2f93f9144.png">


## API Endpoint Details

Method.      | Endpoint Details | Remarks | 
------------ | ------------- | ------------- |
POST | /assignment/favourites/v1/authenticate | JWT token will returned in response | 
POST | /assignment/favourites/v1/recipes | Create a new recipe and recipe id will returned in the location response header | 
PUT | /assignment/favourites/v1/recipes/{id} | Updates a recipe based on recipe id and no response will be returned with status code 204 | 
GET | /assignment/favourites/v1/recipes/{id} | Fetches a recipe based on recipe id  with details| 
DELETE | /assignment//favourites/v1/recipes/{id} | Deletes a recipe based on recipe id and no response will be returned with status code 204| 
GET | /assignment/favourites/v1/recipes | Searches recipes based on query params type, name, servings, ingredient, includeIngredient and instruction | 
## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java11)
- [Apache Maven](https://maven.apache.org)
- [Mongo DB](https://www.mongodb.com)

## Running the application in local environment

* Clone the code from GIT using below command
```shell
git clone https://github.com/lmadam/FavouriteRecipes.git
```
* Download all the dependencies , run tests and build the project using the below command
```shell
mvn clean install
```
* The jar file name FavouriteRecipes-1.0.jar will be created under the target folder
* Local mongo DB is needed to run the application in local, you can use the default localhost mongo URI connection string as below to connect to the local mongo DB, i have created a database named RecipesDB for my local environment.
```shell
mongodb://localhost:27017/RecipesDB
```
* Refer below link for mongo installation and setup in local
- [Mongo DB Local setup](https://www.prisma.io/dataguide/mongodb/setting-up-a-local-mongodb-database#:~:text=Open%20up%20MongoDB%20Compass%20to%20begin.&text=If%20you%20click%20Connect%20without,MongoDB%20server%20you%20are%20running.)
* You can run the application as a java application by running the main method of class RecipesApplication.java
* Alternatively you can use below command to run the application
```shell
mvn spring-boot:run
```
* The application will be started on default port 8080

## Test cases execution and code coverage

* Test case were available for all the business logic with 100 % code coverage.
* You can execute the junit test cases with below command
```shell
mvn test
```
* Jacoco plugin is used to calculate the coverage and by executing below maven command the code coverage files will be generated under the Application path /target/jacoco-report/index.html to view the coverage in web and /target/jacoco-report/jacoco.csv as a .csv file
```shell
mvn clean install
```
* sample Jacoco code coverage appears as below

<img width="1680" alt="recipes_coverage" src="https://user-images.githubusercontent.com/39440188/179615032-8cb0fd98-f87c-4432-8321-6628fe5dea2e.png">

## Deploying the application to Production

* The jar file name FavouriteRecipes-1.0.jar will be created under the target folder
* Below mongo DB configuration needs to be updated in Production environment
```shell
mongbdb://username:password@hostname:portname/dbname
```
* All the secrets and sensitive keys can be stored in vault.
* JWT secret will be stored as .key file.
* Cloud containarization frameworks can be used to deploy the Application

## API Postman curls 

* You can import the postman curls for testing from below loaction

- [Postman Curls](https://github.com/lmadam/FavouriteRecipes/blob/main/src/main/resources/Recipes_postman_collection.json)


## Future Enhancements

* API authentication can be replaced with Oauth2 by using clientid, client secret and Authorization server for generating the token.
* A Mobile Application and Webiste can be built and can consume the exposed API's for Recipes Management.
* Role based Access can also be implemented where the user with specific admin roles can make changes to the menu and other users can be read only.
* CICD pipeline can be configured and deploy with Containarization frameworks in cloud.

## Open for suggestions

Any Improvements and suggestions are always welcome you can reach me at my Email : laskhmi.madam91@gmail.com



 
