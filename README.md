# Notification web service
Spring MVC 4 RESTFul Web Service with MySQL Database. Web service receives requests from users, processes and displays on the map.

## Dependencies
  - [Java 1.7](http://www.oracle.com/technetwork/java/javase/archive-139210.html)
  - [Spring Framework 4.1.1](https://spring.io/)
  - [MySQL Connector/J](http://www.mysql.com/)
  - [FasterXML 2.6.4](https://github.com/FasterXML/jackson)
  - [Log4j 2.5](http://logging.apache.org/log4j/2.x/)
  - [JUnit 4](http://junit.org/junit4/)
  
## Installation instructions
  - Set properly fields such as **password, user, host and default database**. See **MysqlDAO.java**.
  - Run SQL script to create a database with tables. See directory **doc** in root this project.
  
## Rest API

#### Versions API
  Before executing HTTP request you should specify **version of the API**. Current version of the API 1.
##### Example:
  ```
  http://localhost/v1/events
  ```
  v1 - version of the API
#### Token
  A token is a unique value of length 32 and you should specify token in requests. 
  Also, the token is generated in one instance.
  The token allows you to collect requests user's for managing them.
  
##### Example:

  - `HTTP Method: GET`
  - `Parameters: token`
  - `Resource: requests`

Use following address:
```
  http://localhost/v1/requests?token=110c8a30c16070bf2813480d9492a1a1
```
  
#### Create a request
  
  If you want to create a query, you should specify **a body** of a request and a header **Content-Type**.
  
##### Example:

  - `HTTP Method: POST`
  - `Parameters: token`
  - `Content-Type: application/json`
  - `Body: JSON`
  
Use following address:
```
  http://localhost/v1/requests?token=110c8a30c16070bf2813480d9492a1a1
``` 
and this body:
```json
  {  
   "location":{  
      "radius":5,
      "longitude":30.5115341,
      "latitude":50.4378261
   },
   "levelDanger":{  
      "level":5,
      "name":"low"
   },
   "typeRequest":{  
      "name":"steal"
   }
}
```
**Note:** Values **levelDanger** and **typeRequest** should be added in a database before the request.

#### Get all requests

  Get all requests of a user. 

##### Example:

  - `HTTP Method: GET`
  - `Parameters: token`
  
Use following address:
```
  http://localhost/v1/requests?token=110c8a30c16070bf2813480d9492a1a1
```

#### Get a request
  Getting one request by id.
##### Example:

  - `HTTP Method: GET`
  - `Parameters: token`
  
Use following address:
```
  http://localhost/v1/requests/1?token=110c8a30c16070bf2813480d9492a1a1
``` 

#### Delete a request

  Delete one request by id.
  
##### Example:

  - `HTTP Method: DELETE`
  - `Parameters: token`
  
Use following address:
```
  http://localhost/v1/requests/1?token=110c8a30c16070bf2813480d9492a1a1
```

#### Get all events

  Get all events this web service.

##### Example:

  - `HTTP Method: GET`
  - `Parameters: token`
  
Use following address:
```
  http://localhost/v1/events
```

#### Get a event

  Getting one event by id.
##### Example:

  - `HTTP Method: GET`
  - `Parameters: token`
  
Use following address:
```
  http://localhost/v1/events/1
``` 
