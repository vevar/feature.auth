# Feature of authorization   
This feature has been created for reuse logic of authorization(by login & password) and identification (by token) users.
You could add your own implementations of data sources like in the sample. 

## How to run sample
### Requirements: 
- Docker
### Steps for build and run 
1. You need build the **web-client** with the command:     
```gradlew :sample:microservice:auth:web-client:browserProductionWebpack```
2. You need build the **backend application** with the command:  
```gradlew :sample:microservice:fatJar```
3. Build and run docker container with the command:  
```cd sample && docker-compose up```
4. Open browser and go to the http://localhost/auth
