## Module 08: Project Lombok

### Features
**Annotations**
- @Getter - create getter methods for all properties
- @Setter - create setter methods for all non-final properties
- @ToString
    - generate string of classname, and each field
    - optional parameters for include filed names
- @EqualsAndHashCode
    - generate implementation of equals() and hashCode()
    - optional can exclude specific properties
- @NoArgsConstructor - generate no argument constructor
- @RequiredArgsConstructor
    - generates a constructor for all fields that are final or marked @NotNull
    - constructor will throw a NullPointException if any @NotNull fields  are null
- @Data
    - Combines: @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
    - generates typical boilerplate code for POJOs
    - no constructor is generated if constructors have been explicitly declared
- @Value
    - the immutable variant of @Data
    - all fields are made private and final by default
- @NotNull
    - set on parameter method or constructor
    - will throw a NullPointException if parameter is null
- @builder
    - implements the 'builder' pattern dor object creation
- @SneakThrows
- @Syncronized
- @Log
    - create a Java util logger
    - NOT recommend to use
- @Slf4j
    - recommended
    - creates a SLF4J logger
    - SLF4J is a generic logging facade
    - Spring Boot's default logger is LogBack

Note - who to setup log level in the applcation
- add `logging.level.{package}={LEVEL}` to the _application.properties_
- Example `logging.level.pc.springframework.spring5recipeapp=DEBUG`
    - where 'pc.springframework.spring5recipeapp' is the package
    - and 'DEBUG' is the level

### Setup InteliJ
**steps**
- add maven dependency
- add plugin to InteliJ
    - Settings -> Plugins - Browser Repository
    - search for 'Lombok' plugin

**POM dependency**
Project Lombok is under _spring-boot-starter_
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

**After install plugin**
- there are some options under the refactor menu
- if there is some problem, clear the InteliJ cache
    - File -> Invalidate Caches / restart

### Using Project Lombok
**It can be used in 2 ways**

- refactor the existing code
    - the InteliJ will make the changes automatically
    - it is necessary open the class end put the cursor inside the class
- create new class using the Lombok annotations

### Gotchas with Project Lombok
**Circular reference with Bidirectional Entity Relationship**

Cause: Lombok can produce StackOverflowError in _hashCode()_ implementation
Resolution: remove the properties from the implementation of hashCode
Example: `@EqualsAndHashCode(exclude = {"recipes"})`

**Error: failed to lazily initialize a collection**

Cause: lazily initialize
Resolution: annotation @Transactional


