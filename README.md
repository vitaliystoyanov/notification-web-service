# Notification web service
Spring MVC 4 RESTFul Web Service with MySQL Database.

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

Use this url:  
```
  http://localhost/v1/requests?token=110c8a30c16070bf2813480d9492a1a1
```
  
#### Create a request
  
  Something about the request
  
##### Example:

  - `HTTP Method: POST`
  - `Parameters: token`
  - `Content-Type: application/json`
  - `Body: JSON`
  
Use this url:
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
**Note:** Value levelDanger and typeRequest should be added in a database before the request.

#### Get all requests

  Get all requests

##### Example:

  - `HTTP Method: GET`
  - `Parameters: token`
  
Use this url:
```
  http://localhost/v1/requests?token=110c8a30c16070bf2813480d9492a1a1
```

#### Get a request

  Something about getting a request by id.
##### Example:

  - `HTTP Method: GET`
  - `Parameters: token`
  
Use this url:
```
  http://localhost/v1/requests/1?token=110c8a30c16070bf2813480d9492a1a1
``` 

#### Delete a request

  Something about delete.
  
##### Example:

  - `HTTP Method: DELETE`
  - `Parameters: token`
  
Use this url:
```
  http://localhost/v1/requests/1?token=110c8a30c16070bf2813480d9492a1a1
```

#### Get all events

  Get all events

##### Example:

  - `HTTP Method: GET`
  - `Parameters: token`
  
Use this url:
```
  http://localhost/v1/events?token=110c8a30c16070bf2813480d9492a1a1
```

#### Get a event

  Something about getting a event by id.
##### Example:

  - `HTTP Method: GET`
  - `Parameters: token`
  
Use this url:
```
  http://localhost/v1/events/1?token=110c8a30c16070bf2813480d9492a1a1
``` 
