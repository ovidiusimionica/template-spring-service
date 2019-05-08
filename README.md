# Template Spring Service

It is a sample service that one can copy and start experimenting concepts of service discovery and centralized configuration management.

It contains the following particularities:
  * Spring Boot Starter latest 2.x 
  * JUnit 5 library dependencies at test scope and respective pom plugins
  * A enhanced Spring Service Discovery library that supports both Zookeeper and Openshift
  * Spring Cloud Config library for centralized configuration management  that supports both Zookeeper and Openshift

* Swagger Rest API documentation setup as a sample, you need to adapt it

## Getting Started


### Prerequisites

* Java SDK version 8.
* a correctly setup maven settings xml. Use the sample file _maven_settings.xml_


### Building

*Basic Compile and Run*

This project uses Maven for most build-related activities, and you should be able to get off the ground quite quickly by typing

```
$ ./mvnw -s maven_settings.xml -Dmaven.repo.local=.mvn/.m2 spring-boot:run
```



it will start an http server at http://localhost:8081

### Docker infrastructure 

You get a dockerfile as template for further automatic deployment.

Play with it locally:

```
$ docker build  . -t template-spring-service

$ docker run -p 8081:8080 template-spring-service
```

Supported build arguments:

Variable Name | Default Value | Description
---|---|---
VERSION|0.0.1-SNAPSHOT|**Must** be given as build argument and must match the artifact version from _pom.xml_. Even though has a default value, the current version has changed. At the time of this writing version is 0.0.5-SNAPSHOT
DOCKER_REGISTRY_MIRROR|_empty_|May be given as build argument when you do not have direct internet access and you use a docker registry proxy

Supported environment (runtime) arguments:

Variable Name | Default Value | Description
---|---|---
SPRING_PROFILE|_empty_|May be given a specific configuration profile name from the application/bootstrap.yml and it will enable a specific service discovery integration scenario (devops, test...)
JAVA_OPTS|-Xmx512M|May be given to tailor specific jvm properties like heap size
SPRING_ARGS|_empty_|May be given to overwrite specific Spring properties Keep in mind that multiple properties must be space separated and prefixed with -- 

### Spring profiles

A spring boot application may feature multiple configuration profiles (aka spring profiles) but for
the focus of this document we present the most relevant ones.

#### Default profile
This profile disables any external infrastructure dependency and therefor service discovery feature
will not be available.
To use the default profile one just leaves the SPRING_PROFILE env variable not set.


#### Devops profile
This profile enables service discovery towards the local developer docker Zookeeper setup.

**Prerequisite** 

to launch the local docker with Zookeeper service discovery:
```
$ docker-compose -f docker-compose.yml up -d 
```
after that the  [http://master.devops:5050](http://master.devops:5050)


**Run it**

```
$ docker run -p 8081:8080 -e SPRING_PROFILE=devops template-spring-service
```

And check that service discovery was set up successfully:
```
http://localhost:8080/management
```
you must see a JSON containing discovery information.


#### Test profile
This profile enables service discovery towards the Openshift cluster.

**Prerequisite** 

The test Openshift cluster must be directly accessible from your machine and use the oc command to login in before running the application
```
$ oc login <openshift__login_url>
```



**Run it**

```
$ docker run -p 8081:8080 -e SPRING_PROFILE=test template-spring-service
```

And check that service discovery was set up successfully:
```
http://localhost:8080/management
```
you must see a JSON containing discovery information.
 

## Built With

* [Java OpenJDK](http://openjdk.java.net/) - the free as in free beer Java implementation



## Acknowledgments

*  Template Service is based on FLOSS libraries and free to use and modify



