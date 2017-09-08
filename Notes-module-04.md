## Module 04: Spring Configuration

### Spring Configuration Options
**XML, Annotations, Java, Groovy DSL**

Spring Conﬁguration Options
- XML Based Conﬁguration
- Introduced in Spring Framework 2.0
- Common in legacy Spring Applications
- Still supported in Spring Framework 5.x

Annotation Based Conﬁguration
- Introduced in Spring Framework 3
- Picked up via ‘Component Scans’
- Refers to class level annotations
	- @Controller, @Service, @Component, @Repository

Java Based Conﬁguration
- Introduced in Spring Framework 3.0
- Uses Java Classes to deﬁne Spring Beans
- Conﬁguration classes are deﬁned with @Conﬁguration annotation
- Beans are declared with @Bean annotation

Groovy Bean Deﬁnition DSL Conﬁguration
- Introduced in Spring Framework 4.0
- Allows you to declare beans in Groovy
- Borrowed from Grails

Which to Use?
- You can use a combination of all methods
- They will work seamlessly together to deﬁne beans in the Spring Context
- Industry trend is to favor Java based conﬁguration

### Spring Framework Stereotypes
- Stereotype - a ﬁxed general image or set of characteristics which represent a particular type of person or thing.
- Spring Stereotypes are used to deﬁne Spring Beans in the Spring context
- Available Stereotypes - @Component, @Controller, @RestController, @Repository, @Service

**Hierarchy**
- @Component - highest level, all others descend from component
- @Controller - MCV controller
	- @RestController - convenience annotation representing  @Controller and @ResponseBody
- @Repository - access to data layer
    - Spring will detect platform speciﬁc persistence exceptions and re-throw them as Spring exceptions
- @Service

### Component Scan
Component scans look for beans annotated with Spring’s Stereotype annotations. @Controller, @Service, @Component, @Repository.

`@SpringBootApplication` - SpringBootApplication make automatically a component scan in the package where SpringBootApplication is defined.

Change the default behavior of Spring Boot
````java
@SpringBootApplication
@ComponentScan(basePackages = {"pc.services", "pc.springframework"})
public class Spring5webappApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring5webappApplication.class, args);
	}
}
````

With this, spring boot will look for beans only in the packages defined in _@ComponentScan_

### Java Configuration Example
When we have an external jar, and we want define a bean, it is possible do this.

Example:
```java
@Configuration
public class ChuckConfiguration {

    @Bean
    public ChuckNorrisQuotes chuckNorrisQuotes(){
        return new ChuckNorrisQuotes();
    }
}
```

Summary:
- create a class with the annotation `@Configuration`
- create a method that return an instance of the object we want to be created
- annotate the method with `@Bean`

### Spring XML Configuration Example
Create a file in the folder ./resources/chuck-config.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean name="chuckNorrisQuotes" class="guru.springframework.norris.chuck.ChuckNorrisQuotes"/>
</beans>
```

And it is necessary setup the Spring Application class
```java
@SpringBootApplication
@ImportResource("classpath:chuck-config.xml")
public class JokeappApplication {
	public static void main(String[] args) {
		SpringApplication.run(JokeappApplication.class, args);
	}
}
```

Summary:
- create a xml configuration file in the resources folder
- in the xml file define the beans
- int the Spring Application class setup the annotation `@ImportResource`

### Spring Boot Conﬁguration
**Dependency Management**
- Maven or Gradle are supported for curated dependencies
- Each version of Spring Boot is conﬁgured to work with a speciﬁc version of Spring Framework
- Overriding the Spring Framework Version is not recommended
- Other build systems such as Ant can be used, but not recommended

**Maven Support**
- Maven projects inherit from a Spring Boot Parent POM
- When possible, do not specify versions in your POM. Allow the versions to inherit from the parent
- The Spring Boot Maven Plugin allows for packaging the executable jar

**Spring Boot Starters**
- A Spring Boot Starter is a POM which declares a common set of dependencies. Spring Boot Starters are available for most Java projects.
- Starters are top level dependencies for popular Java libraries
- Will bring in dependencies for the project and related Spring components
- Starter ‘spring-boot-starter-data-jpa’ brings in:
	- Hibernate
	- Spring Data JPA - and related Spring deps

**Spring Boot Annotations**
- `@SpringBootApplication` - main annotation to use
- Includes 3 annotations:
	- @Conﬁguration - Declares class as Spring Conﬁguration
	- @EnableAutoConﬁguration - Enables auto conﬁguration
	- @ComponentScan - Scans for components in current package and all child packages

**Spring Bean Scope**
- Singleton - (default) Only one instance of the bean is created in the IoC container.
- Prototype - A new instance is created each time the bean is requested.
- Request - A single instance per http request. Only valid in the context of a web-aware Spring ApplicationContext.
- Session - A single instance per http session. Only valid in the context of a web-aware Spring ApplicationContext
- Global-session - A single instance per global session.
	- Typically Only used in a Portlet context.
	- Only valid in the context of a web-aware Spring ApplicationContext.
- Application - bean is scoped to the lifecycle of a ServletContext. Only valid in the context of a web aware.
- Websocket - Scopes a single bean deﬁnition to the lifecycle of a WebSocket. Only valid in the context of a web-aware Spring ApplicationContext.
- Custom Scope - Spring Scopes are extensible, and you can deﬁne your own scope by implementing Spring’s ‘Scope” interface.
	- You cannot override the built in Singleton and Prototype Scopes

**Declaring Bean Scope**
- No declaration needed for singleton scope
- In Java conﬁguration use @Scope annotation
- In XML conﬁguration scope is an XML attribute of the ‘bean’ tag