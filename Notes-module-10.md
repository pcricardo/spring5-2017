## Module 10: CRUD Operations with Spring MVC

### Using WebJars with Spring Boot
**Add maven dependency**
- go to webjar website and select the bootstrap version
- add the code to POM file
- bootstrap webjar also include jQuery dependency
- in the Thymeleaf templates html files setup dependencies

Example:
```html
<head>
    <meta charset="UTF-8">
    <title>Recipe Home</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"
          th:href="@{/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css}">

    <script src="http://code.jquery.com/jquery-1.11.1.min.js"
            th:src="@{/webjars/jquery/1.11.1/jquery.min.js}"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"
            th:src="@{/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js}"></script>
</head>
```

### Processing Form Posts with Spring MVC
**Data Binding in Spring**
- Command Objects (aka Backing Beans)
    - are used to transfer data to and from web forms
- Spring wil automatically ding data of form posts
- Biding done by property name (less 'get' / 'set')
