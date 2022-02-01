# CRUD App - BACKEND

Spring Boot application built using Spring Initializr consisting of REST APIs for user management.

## Launch the Spring Boot application 

Clone the code into any IDE and execute the main method in the com.assessment.demo.DemoApplication class. The embedded tomcat server starts running in the default port number 8080.

```bash
http://localhost:8080
```
## Database Support

This application makes use of in-memory H2 Database for data storage.
##### NOTE:
File-based storage has not been implemented in this project. Therefore, the in-memory database is volatile, and results in data loss after application restart.

## Usage

Import the collection file maria_demo.postman_collection present in the root folder to Postman and execute the APIs for testing.


## Auto generating Swagger Specifications 
```.
Access http://localhost:8080/swagger-ui/
```




Execute APIs using Postman. Import the collection file maria_demo.postman_collection present in the root folder.Swagger specifications in JSON format is present in the root folder. (swagger-autogenerated_JSON.json)

