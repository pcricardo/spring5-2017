## Module 05: External Properties

### Property Source
**Steps:**
- create property file in resources folder
- create configuration spring file
    - add annotation `@PropertySource("classpath:file_name.properties")` to the class
    - declare bean PropertySourcesPlaceholderConfigurer
    - declare properties in the class that match with the property file. Example: `@Value("${pc.username}")`
    - declare a bean that return the property values

**Bean PropertySourcesPlaceholderConfigurer**

This bean will mach the values in the property file with the properties in the configuration class

```java
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties(){
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =new PropertySourcesPlaceholderConfigurer();
        return  propertySourcesPlaceholderConfigurer;
    }
```

**Note:**
- it is possible use multiple properties files, but the properties names __must be unique__.
- for multiple properties files, use one of the following annotations
    - `@PropertySource({"classpath:...","classpath:..."})`
    - `@PropertySources(@PropertySource("classpath:..."), @PropertySource("classpath:..."))`

### Spring Environment Properties (OS properties)
**Override properties**

In InteliJ - setup Edit Configurations.

Add properties in Environment Properties section.

It is possible use uppercase and underscore (_) or dot (.)
Example `PC_USERNAME`

**Get access to environment properties**

Declare property
```java
    @Autowired
    Environment env;
```
To access, use the code `env.getProperty("USERNAME")`

### Spring Boot Application.properties
Spring Boot have a default behavior that is simpler to setup external properties.
To refactor the previous examples to use the default behavior of Spring Boot, simple follow the following steps:
- move the properties from the properties files to the _application.properties_ file.
- in the Configuration class
    - remove annotations @PropertySources/@PropertySource
    - remove Bean PropertySourcesPlaceholderConfigurer

### Property Hiarchy
 1. **Devtools global settings properties on your home directory (~/.spring-boot-devtools.properties when devtools is active).**
 2. **@TestPropertySource annotations on your tests.**
 3. @SpringBootTest#properties annotation attribute on your tests.
 4. **Command line arguments.**
 5. Properties from SPRING_APPLICATION_JSON (inline JSON embedded in an environment variable or system property)
 6. ServletConfig init parameters.
 7. ServletContext init parameters.
 8. JNDI attributes from java:comp/env.
 9. Java System properties (System.getProperties()).
 10. **OS environment variables.**
 11. A RandomValuePropertySource that only has properties in random.*.
 12. Profile-specific application properties outside of your packaged jar (application-{profile}.properties and YAML variants)
 13. **Profile-specific application properties packaged inside your jar (application-{profile}.properties and YAML variants)**
 14. Application properties outside of your packaged jar (application.properties and YAML variants).
 15. **Application properties packaged inside your jar (application.properties and YAML variants).**
 16. @PropertySource annotations on your @Configuration classes.
 17. Default properties (specified using SpringApplication.setDefaultProperties).