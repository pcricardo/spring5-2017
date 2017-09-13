## Notes Thymeleaf

### Using Thymeleaf

importing namespace - `<html lang="en" xmlns:th="http://www.thymeleaf.org">`

html - all html tags should have the close tag (/>)

### Code
**Foreach**
```html
    <tr th:each="book : ${books}">
        <td th:text="${book.id}">123</td>
        <td th:text="${book.title}">Spring in Action</td>
        <td th:text="${book.publisher.name}">Wrox</td>
    </tr>
```

**Do not render HTML in runtime using a condition**
```html
<div class="table-responsive" th:if="${not #lists.isEmpty(recipes)}">
```

**Remove HTML at runtime**
```html
	<tr th:remove="all">
		<td>100</td>
		<td>Tasty Goodnees 1</td>
	</tr>
```

This is useful to preview the template

**Include java script and css**
```html
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"
          th:href="@{/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css}">
<script src="http://code.jquery.com/jquery-1.11.1.min.js"
        th:src="@{/webjars/jquery/1.11.1/jquery.min.js}"></script>
```

**Fill Select HTML tag**

Enum Example
```HTML
<select class="form-control" th:field="*{difficulty}">
    <option
            th:each="difficultyValue : ${T(pc.springframework.spring5recipeapp.enums.Difficulty).values()}"
            th:value="${difficultyValue.name()}"
            th:text="${difficultyValue.name()}"
    >Easy</option>
</select>
```
The is no need check equality because Thymeleaf will use the equals operator

List Example:
```HTML
<select class="form-control" name="uom.id">
    <option
            th:each="uomEach : ${uomList}"
            th:value="${uomEach.id}"
            th:selected="${uomEach.id.equals(ingredient.uom.id)}"
            th:text="${uomEach.description}"
    >Each</option>
</select>
```
If the object do not implements correctly the equals, it is necessary use the 'th:selected' to select the element.
