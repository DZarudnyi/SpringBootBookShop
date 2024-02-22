# SpringBootBooks
BookShop is a Spring boot application created as a backend base for an online book shop service. It uses Spring tools to provide easy and safe access to a defined set of endpoints, which can be easily expanded further.

## Used technologies
Spring Boot 

Spring Data Jpa

Spring Security

JWT

Swagger

Docker

Lombok

Mapstruct

## Features
**Soft delete** - any time you use delete endpoint it doesn't remove the record from the database, but changes record’s field ‘is_deleted’ to true, so data can be easily restored

**Docker support** - project has all required files to create docker images and run it in a docker container and can easily be modified to meet your needs

**JWT authorization** - after authorization JWT token will be used to make user experience secure and convenient

**Role-based access control (RBAC)** - new users can gain necessary permissions automatically, while “dangerous” zones will remain secure and accessible only for selected group of users

## Prerequisites
Right after you clone git project to your computer, open it in the IDE, right-click on the project and choose **Maven > Update(Reload) Project**. 
![alt text](https://github.com/DZarudnyi/SpringBootBookShop/blob/book-entity-branch/images/maven-reload.png "Maven reload")

This is done to make sure that Maven dependencies folder won’t be missing.

### Running app locally
Once you have this project locally, and you don’t want to use Docker, what you need to do is to comment or delete docker dependency in the pom.xml file. Navigate to projects directory and open pom.xml. In the <dependencies> group search for this:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-docker-compose</artifactId>
    <version>3.1.3</version>
    <scope>runtime</scope>
</dependency>
```

Either comment this block of code or delete it (I recommend commenting if you're planning on using it with Docker). Then again navigate to the Maven tab and reload the project.
Reason for this operation is that when you have a docker dependency, your project will always try to run using docker and will give you the NullPointerException.

After these operations are done, you can Run SpringBootBooksApplication 


### Run app in Docker container
If you intend on running this app in Docker container, or deploying it to some hosting services, then the first thing you need to make sure is that you have required dependency in the pom.xml file. In the <dependencies> group look for:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-docker-compose</artifactId>
    <version>3.1.3</version>
    <scope>runtime</scope>
</dependency>
```

Then you should **Maven > Update(Reload) Project**, so this dependency will be used in the next build. Next it’s time to build the Docker image. So open terminal in projects working directory and run
mvn clean package to check if project builds and to make sure that **target** folder will be here for our image.

The reason we need it is our Dockerfile, in which you can see that Docker will use .jar files from the **target** directory.
![alt text](https://github.com/DZarudnyi/SpringBootBookShop/blob/book-entity-branch/images/dockerfile.png "Dockerfile")

Next command to run in terminal is

`docker build -t {image-name} .`

Make sure you add the .(dot) in the end! Replace the {image-name} with the name, that you want this image to have.

When image is ready, you can run it by using

`docker run -p 8081:8080 {image-name}`

Here the {image-name} is the name you specified on the previous step, 8080 is the port that Docker will use internally in the container and 8081 is the port that will be visible from outside.
So, for example if your container is already running and you want to access it, instead of port 8080 you should use
`http://localhost:8081/api/cart`

## Endpoints
To see detailed list of app's endpoints, examples of their usage and response click [here](Endpoints.md)