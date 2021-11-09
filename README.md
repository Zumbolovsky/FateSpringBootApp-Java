# FateSpringBootApp

This is a base repository for my digital playground and future reference for development.

In this repo, there are the following branches and their use:
- `/master`: for kotlin general development;
- `/feature/java`: for java general development;
- `/feature/mockito-junit5`: for examples and explanation unit testing feat. JUnit5 and Mockito.

The FateSpringBootApp is a Maven project created with Spring Boot as a starting framework.

## Running

In order to run the application, the `docker-compose` file was created to ease the execution.

To run the application, simply access the directory that contains the `docker-compose.yml` file in your 
preferred command terminal application and execute the following commands:

```shell
docker-compose up postgresql
docker-compose up fate-spring-boot-app
```

These commands will create and run the dependant services along with the actual project application.

In addition, for ease of use, another service is present in the `docker-compose.yml` file: 
a pgadmin service, used for database navigation. 

Note that, for database navigation, you will need to connect to the postgresql server
specified using either the docker container IP in the network created, or the declared **testname** hostname.

Also, all credentials needed to access the pgadmin console and postgresql server declared in the pgadmin navigation UI are exposed 
in the same `docker-compose.yml` file.

## Acessing the API

Once the project is up and running, the API can be accessed via SwaggerUI on [this address](http://localhost:8080/fate/swagger-ui.html).

## HotSwapping code

The docker-compose created project application has the following ports enabled: 8080, 8000.

The 8080 port is used for web access (requests).

The 8000 port is used for remote access.

This enables the creation of configurations using modern IDEs which can use the remote debugging tools to remotely evaluate
process executions, and more importantly, hotswap code.

To create a config for remote debugging in IntelliJ use the following template in "Run" > "Edit configurations...":
![img.png](img.png)

Using this configuration with the running docker-compose project application, once a file is altered, just right-click the
file and select "Compile and reload file" from the context menu.