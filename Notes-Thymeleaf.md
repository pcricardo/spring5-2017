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

