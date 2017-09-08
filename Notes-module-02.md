## Module 02: Dependency Injection with Spring

### SOLID Principles of OOP
**Single Responsibility Principle**
- Every Class should have a single responsibility.
- There should never be more than one reason for a class to change.
- Your classes should be small. No more than a screen full of code.
- Avoid ‘god’ classes.
- Split big classes into smaller classes

**Open/Closed Principle**
- Your classes should be open for extension
- But closed for modification
- You should be able to extend a classes behavior, without modifying it.
- Use private variables with getters and setters - ONLY when you need them.
- Use abstract base classes

**Liskov Subsitution Principle**
- Objects in a program would be replaceable with instances of their subtypes WITHOUT altering the correctness of the program.
- Violations will often fail the “Is a” test.
- A Square “Is a” Rectangle
- However, a Rectangle “Is Not” a Square

**Interface Segregation Principle**
- Make fine grained interfaces that are client specific
- Many client specific interfaces are better than one “general purpose” interface
- Keep your components focused and minimize dependencies between them
- Notice relationship to the Single Responsibility Principle?
  - ie avoid ‘god’ interfaces

**Dependency Inversion Principle**
- Abstractions should not depend upon details
- Details should not depend upon abstractions
- Important that higher level and lower level objects depend on the same abstract interaction
- This is not the same as Dependency Injection - which is how objects obtain dependent objects

**Summary**
- The SOLID principles of OOP will lead you to better quality code.
- Your code will be more testable and easier to maintain.
- A key theme is avoiding tight coupling in your code.

### Basics of DI
**Dependency Injection**
- Dependency Injection is where a needed dependency is injected by another object.
- The class being injected has no responsibility in instantiating the object being injected.

**Types of Dependency Injection**
- By class properties - least preferred
	- Using private properties is __EVIL__
- By Setters - Area of much debate
- By Constructor - __Most Preferred__

**Inversion of Control**
- Inversion of Control - aka IoC
- Is a technique to allow dependencies to be injected at runtime
- Dependencies are not predetermined

**IoC vs Dependency Injection vs Dependency Inversion**
- Dependency Injection refers to the composition of your classes
	- you compose your classes with DI in mind
	- how objects obtain dependent objects
- IoC is the runtime environment (or framework) which injects dependencies
	- Example: Spring Framework’s IoC container
- Dependency Inversion is a SOLID principle
	- it says: instead of high-level module depending on a low-level module, both should depend on abstraction

### Dependency Injection using Spring Framework
- property - add @Autowired
    - Example: `@Autowired public GreetingServiceImpl greetingService;`
- setter - add @Autowired
- constructor - it is optional add _@Autowired_
    - since Spring 4.2 spring enable automatically autowired with constructor
    - in older versions was 'tricky', so people prefer setter

### Wiring up Beans
- Qualifier: specify a bean by name
- Primary: use when there are more than one bean, and want to specify a preferency for one over the others
- Profile: specify a bean by profile.
    - it is necessary setup the profile int the _application.properties_ file
    - the property to setup is: `spring.profiles.active=en`
- Default: use when there are not any profile active in the application
    - Example: @Profile({"en", "default"})
