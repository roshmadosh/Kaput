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


