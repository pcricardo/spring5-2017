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