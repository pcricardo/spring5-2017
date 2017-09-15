## Module 11: Validation and Constraints with Spring MVC

### Using Spring MVC Annotation @ResponseStatus
**Steps for not found:**
- create a class that extends RuntimeException , and annotated with ResponseStatus(HttpStatus.NOT_FOUND)
- in the service class, throws the exception
- the result will be a 4xx error instead 5xx error

Example: create our own RuntimeException class
```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

Example: in the service class, throws the exception
```java
@Override
public Recipe findById(Long id) {
    Optional<Recipe> recipeOptional = recipeRepository.findById(id);
    if (!recipeOptional.isPresent()) {
        throw new NotFoundException("Recipe not found.");
    }
    return recipeOptional.get();
}
```

Example: test service and controller
```java
//Test ser4vice
@Test(expected = NotFoundException.class)
public void testFindByIdWhenIdNotExistThenExpectNotFoundException() throws Exception{
    //given
    Optional<Recipe>recipeOptional = Optional.empty();
    when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

    //when
    recipeService.findById(anyLong());

    //then
    fail("Exception Expected");

}

//Test controller
@Test
public void testShowByIdNotFound() throws Exception {
    when(service.findById(anyLong())).thenThrow(NotFoundException.class);
    mockMvc.perform(get("/recipe/1/show"))
            .andExpect(status().isNotFound());
}

```

### Spring MVC Exception Handler
**Steps create our own error 4xx page**
- create html template
- in the controller define a new method that will handle the error page
- the result will be:
    - if any method in the controller generate a NotFoundException Exception
    - then it will present the 404error template, instead an error in the browser

Example: method in the controller
```java
@ResponseStatus(HttpStatus.NOT_FOUND)
@ExceptionHandler(NotFoundException.class)
public ModelAndView handleNotFound() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("404error");
    return modelAndView;
}
```
Notes:
- NotFoundException - our own error class
- @ResponseStatus(HttpStatus.NOT_FOUND) - the same annotation used in our NotFoundException class

### Showing Error Data on 404 Error Page
**Steps:**
- in the controller method add the `public ModelAndView handleNotFound(Exception exception)` in the parameter definition
- add the exception to ModelAndView - `modelAndView.addObject("exception", exception)`
- in the template view show the information `th:text="${exception.getMessage()}"`

### Handle Number Format Exception
Example
```java
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBaRequest(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
```
Note:
- NumberFormatException is a Java class that extends IllegalArgumentException

### Spring MVC Controller Advice
This allow to centralise in only one Controller all the common exceptions. So it remove duplicate code.
**Steps**
- Create a new controller, and annotate with `@@ControllerAdvice`
- move all the handle error from the others controllers to the now controller
- in the unit tests it is necessary change the mock
    - from `mockMvc = MockMvcBuilders.standaloneSetup(controller).build();`
    - to `mockMvc = MockMvcBuilders.standaloneSetup(controller) .setControllerAdvice(new ControllerExceptionHandler()) .build();`

Example:
```java
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBaRequest(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
```

### Data Validation with JSR-303
**JSR 303 - Java Bean Validation**
- Approved on November 16th, 2009
- Part of JEE v6
- JSR 303 Supported Since Spring Framework 3
- JSR 303 Produced Standard Validation Annotations
- Standard validations are found in the package javax.validation.constraints
- From the jar javax.validation:validationapi-1.1.0.Final
- API Implementation is: org.hibernate:hibernatevalidator:5.4.1.Final

**JSR 380 - Bean Validation 2.0**
- July 2016 Work started on Bean Validation 2.0
- Primary goal is to leverage features of Java 8
- CR 3 (Candidate Release) was released on July 12, 2017
- Bean Validation 2.0 not supported in Spring (yet)

**Standard Validators**
- @AssertFalse
- @AssertTrue
- @DecimalMax
- @DecimalMin
- @Digits
- @Size
- @Max
- @Min
- @NotNull
- @Null
- @Future
- @Past
- @Pattern

**Hibernate Validators**
- @CreditCardNumber
- @Currency
- @EAN
- @Email
- @Length
- @LunhCheck
- @Mod10Check
- @Mod11Check
- @NotBlank
- @NotEmpty
- @ParameterScriptAssert
- @Range
- @SafeHtml
- @ScriptAssert
- @URL

### Data Validation with Spring MVC
Example without validation
```java
@PostMapping("saverecipe")
public String saveOrUpdate( @ModelAttribute RecipeCommand command, BindingResult bindingResult) {
	RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
	return SUCESSFUL_URL;
}
```

Example with validation
Steps:
- in method declaration
    - annotate the parameter with `@Valid`
    - add `BindingResult` parameter
- in method body
    - check if there are errors in he biding
```java
@PostMapping("saverecipe")
public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
	if (bindingResult.hasErrors()) {
		bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
		return NEW_EDIT_FORM_URL;
	}

	RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
	return SUCESSFUL_URL;
}
```

### Customizing Error Messages with Message Source
Steps:
- add the file 'messages.properties' in the resource folder

**Validation Messages - Order of precedence**
- 1 code.objectName.fieldName
- 2 code.fieldName
- 3 code.fieldType (Java data type)
- 4 code

### Internationalization Spring MVC
**International Standards**
- Language identifiers were established by RFC 3066 in 2001
	- Language Codes are governed by ISO 639
- ISO - International Organization for Standardization
	- Region codes are governed by ISO 3166 •Can refer to countries, regions, territories, etc.

**Locale Detection**
- Default behavior is to use Accept-Language header
- Can be configured to use system, a cookie, or a custom parameter.
	- Custom Parameter is useful to allow user to select language.

**Locale Resolvers**
- AcceptHeaderLocaleResolver is the Spring Boot Default
- Optionally, can use FixedLocaleResolver
	- Uses the locale of the JVM
- Available: CookieLocaleResolver, SessionLocaleResolve

**Changing Locale**
- Browsers are typically tied to the Locale of the operating system
- Locale changing plugins are available
- Spring MVC provides as LocaleChangeInterceptor to allow you to configure a custom parameter to use to change the locale.

