# Phone App

 Phone App challenge implementation

## Technologies
 I used the following libraries and frameworks:
 
 * [Spring Boot](https://projects.spring.io/spring-boot/) - The core framework used
 * [Spring Boot WEB](https://projects.spring.io/spring-boot/) - Mvc framework
 * [Spring Boot JPA](https://projects.spring.io/spring-boot/) - Spring data , persistence api
 * [Spring Boot TEST](https://projects.spring.io/spring-boot/) - Spring boot testing
 * [HSQLDB](http://hsqldb.org/) - Embedded memory database  
 * [LOMBOK](https://projectlombok.org/) - To avoid Boilerplate code 
 * [Gradle](https://gradle.org/) - Dependency Management
 


### Point 1
 Create a endpoint to retrieve the phone catalog, and pricing.

```
GET - http://127.0.0.1:9001/phones
```

### Point 2
Create endpoints to check and create and order. 

```
GET/POST - http://127.0.0.1:8080/purchase_orders
```

And get total(Example : Purchase order id = 1)

```
GET - http://127.0.0.1:8080/purchase_orders/id/total
```

## Dockerizing the app

You can create a app's docker image

```
 gradle clean build buildDocker
``` 
And run

```
 docker run --name="phone-app" --publish 9001:8080 phone/app:0.0.1-SNAPSHOT
```

##  How would you improve the system? 
* Add security, I think it would be a good idea to add one security layer based on OAUTH-JWT 
* Include documentation, additionally to the Java doc it would be necessary a RestApi documentation , Spring REST Docs or Swagger would be a good approach.
* App health checks, I added Spring Actuator for this purpose.

## How would you avoid your order api to be overflow?
We could mount a system based on docker container and add a proxy or load balancer 


