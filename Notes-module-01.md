## Module 01: Building a Spring Boot Web App

### Spring Initializer
Create a new project with Spring Boot Initializer.
Go to Spring Boot Initializer - http://start.spring.io/

Setup
+ Generate a **Maven Project** with  **Java**
+ Spring Boot: 2.0.0 M3
+ Packaging: jar
+ Java Version: 1.8
+ Language: java
+ Web: web
+ SQL: jpa, h2
+ Template Engines: Thymeleaf
+ Ops: Actuator

The result is a zip file with the standard java project with Spring Boot.
The zip contains a wrapper file to execute maven (mvnw.cmd), it is not necessary have maven installed.

**Note:** 
- Spring Boot 2 uses Spring 5
- Spring 5 uses Hibernate 5

### Open project in InteliJ
+ File -> New -> Project from existing sources
+ Select the folder that have the pom file
+ Select the default options (maven, java 1.8)
+ Affect InteliJ will take a few minutes to setup the project.

Important files 
+ .gitignore
+ pom.xml


Run app with maven in terminal window of InteliJ (this will user the maven wrapper).
Open terminal window (in InteliJ) and run the commands
- `mvnw spring-boot:run` - run the app
- `ctrl+c` - terminate app

Note: for linux - `.mvnw spring-boot:run`

### Using JPA Entities
**JPA 2.0**
- Java Community Process as JSR 317
- Approved in December of 2009
- Added support for embedded objects and ordered lists
- Added criteria query API
- Added SQL Hints
- Added Validation

**JPA 2.1**
- Java Community Process as JSR 338
- Approved in December of 2013
- Added custom type converters
- Criteria Update/Delete for bulk updates & deletes
- Schema Generation
- Queries against stored procedures

**Hibernate 5**
- Hibernate 5 released in September 2015
- Commercial support available from Red Hat
- Adopted by Spring Framework in version 4.2 (July 2015)
- Adopted by Spring Boot in version 1.4 (January 2016)

**Create Entities**
```Java
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    private Set<Book> books  = new HashSet<>();
}
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @ManyToMany
    private Set<Author> authors = new HashSet<>();
}
```

In ManyToMany, by default hibernate, create 2 relation tables.
To change this behavior it is necessary setup ManyToMany and JoinTable annotations.

```Java
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books  = new HashSet<>();
}
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @ManyToMany
    @JoinTable(name = "author_book",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();
}
```

**H2 database**

By default the access to the H2 data base is disabled.

Setup application.properties
- add `spring.h2.console.enabled=true`

Access in browser
- url: http://localhost:8080/h2-console
- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:testdb
- User Name: sa
- Password: <none>



### Using GitHub
What is GitHub? https://www.youtube.com/watch?v=w3jLJU7DT5E
Play List GitHub and Git Foundations - https://www.youtube.com/watch?v=HwrPhOp6-aM&list=PL0lo9MOBetEHhfG9vJzVCTiDYcbhAiEqL

### Spring Data Repositories
How to use Spring Data JPA
- You extend a Java Repository Interface
- Spring Data JPA provides the implementation at run time
- No SQL Required

Example
```Java
public interface BookRepository extends CrudRepository<Book, Long> {
}
```
where:
- _Book_ - represents the entity we want to be persist in the database
- _Long_ - represents the type of ID property defined in Book class

Notes - good practices:
- the classes are placed in a package called _repositories_
- the name of class end with _repository_

### Initializing Data with Spring
When the application start, it is possible create data to test the application.

This is useful when we are developing and want some information to test the aplication.

With Spring Boot it is easy. Just create a class like the following example.

```java
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {
@Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData(){
        //create data
    }
}
```

Summarise:
- Create a class that implements **ApplicationListener**
- Override **onApplicationEvent** method
- annotate the class with @Component

### Configuring Spring Controllers
- Annotate Controller Class with @Controller.
This will register the class as a Spring Bean and as a Controller in Spring MVC
- To map methods to http request paths use @RequestMapping

**Notes** - good practices:
- the classes are placed in a package called _controllers_
- the name of class end with _controller_

### Thymeleaf Templates
- Thymeleaf is a Java template engine
- First stable release in July 2011
- Rapidly gaining popularity in the Spring Community
- Thymeleaf is a natural template engine.
Natural meaning you can view templates in your browser

Thymeleaf with Spring Boot
- the template files are html 5 files, and are located in _./resources/templates/_

**Using Thymeleaf**

importing namespace - `<html lang="en" xmlns:th="http://www.thymeleaf.org">`

html - all html tags should have the close tag (/>)

**Code**
Foreach
```java
    <tr th:each="book : ${books}">
        <td th:text="${book.id}">123</td>
        <td th:text="${book.title}">Spring in Action</td>
        <td th:text="${book.publisher.name}">Wrox</td>
    </tr>
```

