## Notes Spring MVC

### Controllers
- Annotate Controller Class with @Controller.
This will register the class as a Spring Bean and as a Controller in Spring MVC
- To map methods to http request paths use @RequestMapping


**Add tag @Controller to class**

Example:
```java
@Controller
public class RecipeController {
    (...)
}
```

**Request Mapping**

Example old way
```java
    @RequestMapping(value = "/recipe/{id}/show", method = RequestMethod.GET)
    public String showById(@PathVariable String id, Model model) {
        (...)
    }
```

Example new way (since version 4.3)
```java
    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        (...)
    }
```

**Add Data Validation with Spring MVC**
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