## Module 09: Testing Spring Applications

### Introduction to Testing Spring
#### Testing Terminology
**Unit Tests**
- Designed to test speciﬁc sections of code
- Percentage of lines of code tested is code coverage
    - Ideal coverage is in the 70-80% range
- Should be ‘unity’ and execute very fast
- Should have no external dependencies
    - ie no database, no Spring context, etc

**Integration Tests**
- Designed to test behaviors between objects and parts of the overall system
- Much larger scope
- Can include the Spring Context, database, and message brokers
- Will run much slower than unit tests

**Functional Tests**
- Typically means you are testing the running application
- Application is live, likely deployed in a known environment
- Functional touch points are tested
    - i.e. Using a web driver (like Selenium), calling web services, sending / receiving messages, etc

**TDD - Test Driven Development**
- Write tests ﬁrst, which will fail, then code to ‘ﬁx’ test.

**BDD - Behavior Driven Development**
- Builds on TDD and speciﬁes that tests of any unit of software should be speciﬁed in terms of desired behavior of the unit.
- Often implemented with DSLs to create natural language tests
- JBehave, Cucumber, Spock
    - Spock example: given, when, then

**Mock**
- A fake implementation of a class used for testing. Like a test double.

**Spy**
- A partial mock, allowing you to override select methods of a real class.

#### Testing Goals
- Generally, you will want the majority of your tests to be unit tests
- Bringing up the Spring Context makes your tests exponentially slower
- Try to test speciﬁc business logic in unit tests
- Use Integration Tests to test interactions
- Think of a pyramid. Base is unit tests, middle is integration tests, top is functional tests

#### Test Scope Dependencies
 Using spring-boot-starter-test (default from Spring Initializer will load the following dependencies:
- JUnit - The de-facto standard for unit testing Java applications
- Spring Test and Spring Boot Test - Utilities and integration test support for Spring Boot applications
- AssertJ -  A ﬂuent assertion library
- Hamcrest - A library of matcher objects
- Mockito - A Java mocking framework
- JSONassert - An assertion library for JSON
- JSONPath - XPath for JSON

#### JUnit 4
- JUnit 4 is the most popular testing framework in the Spring community
- Originally written by Erich Gamma and Kent Beck (creator of extreme programming)
- JUnit 5 is currently in Alpha. Milestone 1 expected in July 2017. GA expected in 2017

### Creating a JUnit Test
It is necessary add a maven dependency in POM file
```XML
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
```

**Example of integration test**

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class Spring5webappApplicationTests {

	@Test
	public void contextLoads() {
	}
}
```

- @RunWith(SpringRunner.class) - will bring up the context

**Example of unit test**

```java
public class CategoryTest {
    private Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category();
    }

    @Test
    public void getId() throws Exception {
        Long idValue = 4L;
        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }
}
```

### Using Mockito Mocks
Steps:
- annotate the property that want to be mocked
- initialize mock
- setup the behavior we want to the object mocked
- test

Example - mock the repository
```java
    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipes() throws Exception {
        // GIVEN
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);


        // WHEN
        Set<Recipe> recipes = recipeService.getRecipes();

        // THEN
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();

    }
```

### Mockito Argument Capture
Example
```java
    (...)
    ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
    (...)
    verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
    Set<Recipe> setValueCaptor = argumentCaptor.getValue();
    assertEquals(2, setValueCaptor.size());
    (...)
```

### Introduction to Spring MockMVC
Features:
- Test MVC Controllers
- Mock Dispatch Servlet
- There is no need to bring up the Spring Context
- It is used in unit test

Example:
```java
    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipeapp/index"));
    }
```

### Spring Integration Test
Steps:
- create a test class
- annotate
    - @RunWith(SpringRunner.class)
    - @DataJpaTest - this will bring up an embedded database, and configure Spring Data JPA
    - or @SpringBootTest

Example:
```java
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    public void findByDescriptionTeaspoon() throws Exception {
        Optional<UnitOfMeasure> unit = unitOfMeasureRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", unit.get().getDescription());
    }
}
```
Notes:
- by convention the name of the call should ends with 'IT'
- in integration test the Spring Context will bring up
- by default, for each test class, the Spring Context will bring up ONLY once
- use the annotate @DirtiesContext, to force reload Spring Context after method test
- @DataJpaTest
    - provides some standard setup needed for testing the persistence layer:
    - configuring H2, an in-memory database
    - setting Hibernate, Spring Data, and the DataSource
    - performing an @EntityScan
    - turning on SQL logging
- @SpringBootTest
    - can be used when we need to bootstrap the entire container

### Maven Failsafe Plugin
- By default Maven run all test (unit an integration tests) in the 'test' bean lifecycle
- It is possible change this behavior, run the integration tests in the 'verify' bean lifecycle
- To change it is necessary add a plugin in POM file

```XML
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-failsafe-plugin</artifactId>
        <version>2.20</version>
        <configuration>
            <includes>
                <include>**/*IT.java</include>
            </includes>
            <additionalClasspathElements>
                <additionalClasspathElement>${basedir}/target/classes</additionalClasspathElement>
            </additionalClasspathElements>
            <parallel>none</parallel>
        </configuration>
        <executions>
            <execution>
                <goals>
                    <goal>integration-test</goal>
                    <goal>verify</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
```

### Continuous Integration Testing with Circle CI
