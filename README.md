# Kaput: A Simple Web Interface for Tracking Job Applications
---

The app is named after the sound my car makes, which I'm hoping to replace once 
I no longer need to use such an app.  

Requirements:
- Java 11 or Newer
- PostgreSQL v14+ (will replace once Docker set up)

You will have to first create a database named `kaput` in PostgreSQL, then update the DB credentials 
in `build.gradle` (under the liquibase task) and `application.properties`.  

With the DB created and credentials updated, Liquibase should read `changelog-root.xml` to initialize the 
tables and data for you.

Run locally by executing the command `gradlew runBoot` from the project root directory. Once the app is up and 
running, go to `localhost:8080` from a browser.  

Only two entites are needed for this application.  
![entity-relationship diagram](./erd.png)  

Exception handling is composed of two parts:
1. Providing a custom exception response body
2. Keeping the `RestControllerAdvice` class DRY by creating a `CustomException` class

The `CustomException` class is an abstract class that needs a `getStatusCode` method implemented. This 
allows for single "joinpoint" for a general group of exceptions, and not having to define one for each 
status code.  

One pain-point with this strategy is the inheritance tree is a bit long, where `Exception` -> `CustomException` -> 
`UserException` -> `UserNotFoundException`. I didn't know how to pass down every overloaded constructor from `Exception` 
down to the implemenation classes without having to define them in the intermediate classes, which feels very repetitve.  
