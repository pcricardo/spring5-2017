## Docker in Windows 10
**How to install Docker in Windows 10**

https://docs.docker.com/docker-for-windows/install/#start-docker-for-windows

**Get Start**

https://docs.docker.com/docker-for-windows/

- show how to user command line - PowerShell
- run the basic commands
	- run containers
- to access to Docker Hub Containers it is necessary a access count in https://hub.docker.com/

**Kitmatic**

It is a windows App to manage containers


==============================================
==============================================


## Module 13: Run Mongo BD Docker Container

- search and download the latest mongo image
	- `docker run mongo`
	- by default mongo will use the port 27017
- run
	- `docker run -d mongo`
- stop
	- `docker stop "container name"`
- run and expose the mongo to the applications
	- `docker run -p 27017:27017 -d mongo`

### Assignment - Run demo Mongo DB Application
- download spring-boot-mongodb with Git Clone
- start docker mongo container
	- `docker run -p 27017:27017 -d mongo`
	- every time that run this command the data base is clean, because it creates a new clean container
- get info
	- docker ps
- start view logs
	- `docker logs -f "container name"`
- start the application in InteliJ
	- go to http://localhost:8080/product/list
	- test add, list, view, edit, delete

### Docker Images
**Docker Commands**

Get images layers info:
- `docker ps`, `docker ps -a`
- `docker images` - list of images
- `docker images -q` - list of ID images
- `docker images -q --no-trunc` - list hash sha256
- `docker image inspect mongo`

**What is a Docker Image?**
- An Image defines a Docker Container.
	- Similar in concept to a snapshot of a VM.
	- Or a class vs an instance of the class.
- Images are immutable.
	- Once built, the files making up an image do not change.

**Image Layers**
- Images are built in layers.
- Each layer is an immutable file, but is a collection of files and directories.
- Layers receive an ID, calculated via a SHA 256 hash of the layer contents.
	- Thus, if the layer contents change, the SHA 256 hash changes also

**Image Ids**
- Image Ids are a SHA 256 hash derived from the layers.
	- Thus if the layers of the image changes, the SHA 256 hash changes
- The Image ID listed by docker commands (ie ‘docker images’) is the first 12 characters of the hash.

**Image Tag Names**
- The hash values of images are referred to by ‘tag’ names.
- This concept is very confusing at first.
- The format of the full tag name is: [REGISTRYHOST/][USERNAME/]NAME[:TAG]
- For Registry Host ‘registry.hub.docker.com' is inferred
- For ‘:TAG’ - ‘latest’ is default, and inferred.
- Full tag example: registry.hub.docker.com/mongo:latest

### Mongo DB Assigning Storage
Every time that run this command the data base is clean, because it creates a new clean container
To change this behavior, follow this steps:
- create a disk folder to save the changes
- `docker run run -p 27017:27017 -v /my/own/datadir:/data/db -d mongo`
	- where "/my/own/datadir" is the folder created
	- in windows `docker run -p 27017:27017 -v /userlogin/<path>:/data/db -d mongo`

### Run Rabbit MQ Container
`docker run -d --hostname guru-rabbit --name some-rabbit -p 8080:15672 -p 5671:5671 -p 5672:5672 rabbitmq:3-management`

- "-d" - run in the background
- "-p 8080:15672" - port mapping

### Run MySQL in a Container
- create MSql Container
- mapping port 3306
- persist storage
- environment variable

`docker run -d --name guru-mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -v /my/own/datadir:/var/lib/mysql -p 3306:3306  mysql`
	- where "/my/own/datadir" is the folder created
	- in windows "-v /userlogin/<path>/db:/var/lib/mysql"

### Docker House Keeping
- once a volume is no longer associated with a container, it is considered "dangling"

**Commands**
- `docker kill $(docker ps -q)`
	- kill all running dockers containers
- `docker rm $(docker ps -a -q)`
	- delete all stoped dockers containers
- `docker rmi <image name>`
	- remove a docker image
- docker rmi $(docker images -q -f dangling=true)
	- delete untagged (dangling) image
- `docker rmi $(docker images -q)`
	- delete all images`
- `docker volume rm $(docker volume ls -f dangling=true -q)`
	- remove all dangling volumes
	- this do _NOT_ remove the files from host system in shared volumes

==============================================
==============================================

## Module 14: Running Spring Boot in a CentOs Image

### Preparing CentOS for Java Development
**Start centos container**

Steps in PowerShell:
- `docker run -d centos tail -f /dev/null`
- `docker ps`
	- show the centos container is running
- `docker exec -it gifted_mahavira bash`
	- shell in into centos
	- the result is the now whe are in centos shell
- `whoami`
	- who am I command
	- the result is root
- `ps -ef`
	- process running in centos

**Install Java in CentOS container**

Steps in CentOS shell:
- `java -version`
- `yum install java`
- `java -version`
	- result: openjdk version "1.8.0_144"

Notes:
- `docker run -d centos`
	- do not run centos
	- it needs to add a command like `tail -f /dev/null`
- openjdk
	- there were some problems with old version
	- 1.7 and 1.8 is ok
- this do not save the changes (add java) make in the image


### Running Spring Boot from Docker

Taking a Spring Boot Jar and putting into a docker CentOS image.

Steps:
- create Spring Boot Artifact with the demo application
- create a docker file
    - include java
    - include spring boot application
- bring up Spring Boot Application inside docker container

Notes:
- By default Spring Boot generate a fat jar.
- This is a jar that include all the maven dependencies.
- This is good to use in docker, because it does not need setup dependencies.


**create Spring Boot Artifact - Generate jar in InteliJ**
- run the package Lifecycle from Maven

**create a docker file - "Dockerfile" with capital 'D'**
```
FROM centos

RUN yum install -y java

VOLUME /tmp
ADD /spring-boot-web-0.0.1-SNAPSHOT.jar myapp.jar
RUN sh -c 'touch /myapp.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/myapp.jar"]
```

**bring up Spring Boot Application inside docker container**
- `docker build -t spring-boot-docker .`
	- run the comman where there are the Docker file and the jar file
	- "docker build" - create image
	- "-t" - tag this image spring-boot-docker
	- "." - look to the local directory for the docker file
- `docker run -d -p 8080:8080 spring-boot-docker`
	- "-d" - run in debug
	- "-p" - mapping port
	- "spring-boot-docker" - name image
- docker logs <CONTAINER ID>
	- show logs.
	- the result information should contian "Tomcat started on port(s): 8080 (http)"
- go to browser http://localhost:8080/
	- it should present que java application

