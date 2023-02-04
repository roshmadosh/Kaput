# Kaput: A Simple Web Interface for Tracking Job Applications
---

The app is named after the sound my car makes, which I'm hoping to replace once 
I no longer need to use such an app.  

Requirements:
- Java 11 or Newer
- PostgreSQL v14+ (will replace once Docker set up)

You will have to first create a database named `kaput` in PostgreSQL, then update the DB credentials 
in `build.gradle` and `application.properties`. PostgreSQL's init scripts are a pain to write compared 
to MySQL, which is why I don't include them atm.  

With this the application should run locally by executing the command `gradlew runBoot` from 
the project root directory. Once the app is up and running, go to `localhost:8080` 
from a browser.  

Only two entites are needed for this application.  
![entity-relationship diagram](./erd.png)  


