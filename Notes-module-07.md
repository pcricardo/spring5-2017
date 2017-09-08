## Module 07: JPA Data Modeling with Spring and Hibernate

### JPA Entity Relationship
**Types of Relationships**
- One to one
- One to many
- Many to one
- Many to many

**Unidirectional / Bidirectional**
- Unidirectional: one side of the relationship will not know about the other
- Bidirectional: both sides know about each other. __It is recommended__.

**Fetch Type**
- Lazy fetch: data is not required until referenced
- Eager fetch: data is quired up front

Notes:
- Hibernate 5 supports JPA 2.1
- JPA 2.1 fetch type default
    - one to one - eager
    - one to many - lazy
    - many to one - eager
    - many to many - lazy

**Cascade Types**
- persist
- merge
- refresh
- remove
- detach
- all

**Inheritance**
- Mapped Supper Class
    - entities inherit from a supper class
    - a data base table IS NOT created for the supper class
- Single Table
    - hibernate default
    - one table is used for all subclasses
    - disadvantage - unused database columns
- Joined Table
    - base class and subclasses have their own tables
    - disadvantage - fetching subclass entities require a join  to the table of the superclass
- Table Per Class
    - each subclass has its own table

**Automatically update timestamp properties for audit**
- JPA
    - @PrePersist
    - @PreUpdate
- Hibernate
    - @CreationTimestamp
    - @UpdateTimestamp

### One To One JPA Relationships
**Bidirectional**

**Entities Recipe and Notes**
```java
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;
}

@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;
}
```

Notes:
- @Cascade
    - _recipe_ will be the owner of the relationship
    - by specify cascade, the notes will be deleted when the recipe is deleted
    - _notes_ do not specify cascade, the recipe will NOT be deleted when the the notes are deleted
- @Lob - for large objects

### One To Many JPA Relationships
**Bidirectional**

**Entities Recipe and Ingredient**
```java
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients;
}

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Recipe recipe;
}
```

Notes:
- @Cascade
    - _recipe_ will be the owner of the relationship
    - by specify cascade, the ingredient will be deleted when the recipe is deleted
- _mappedBy_
	- should be placed in the OneToMany
	- should contain the name of the property of the other entity
- _ingredient_ do not specify cascade, the recipe will NOT be deleted when the the ingredients are deleted

### One to One Relationship
**Unidirectional**

**Entities Ingredient and Unit of Measure**
```java
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure uom;
}

@Entity
public class UnitOfMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
}
```

Notes:
- @Cascade
    - do not specify cascade, we do not want delete UnitOfMeasure when delete Ingredient
- UnitOfMeasure is only a reference table
- FetchType.EAGER
	- by default in @OneToOne relationship the fetch type is EAGER, so in the example the fetch type is optional

### JPA Enumerations
```java
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;
}

public enum  Difficulty {
    EASY, MODERATE, HARD
}
```

Notes:
- EnumType - specify how to save the values in the data base
    - ORDINAL
		- default
		- save a number
		- where the initial constant is assigned an ordinal of zero
	- STRING - save the string

### Many To Many JPA Relationships
```java
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;
}

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;
}
```

Notes:
- by default hibernate, create 2 relation tables. To change this behavior it is necessary setup ManyToMany and JoinTable annotations.
- _mappedBy_
	- should contain the name of the property of the other entity

### Creating Spring Data Repositories
| JPA Repositories | PK | extends |
| --- | --- | --- |
| JpaRepository<T, ID> | data.jpa.repository | PagingAndSortingRepository, QueryByExampleExecutor |
| PagingAndSortingRepository<T, ID> | data.repository  | CrudRepository<T, ID> |
| QueryByExampleExecutor<T> | data.repository.query | none |
| CrudRepository<T, ID> | data.repository | Repository<T, ID> |

How to create repository
- create an interface that extends CrudRepository<T, ID>
- T - represents the domain/model class
- ID - represents the type parameter in the class domain/model that represents the ID
Example
```java
public interface CategoryRepository extends CrudRepository<Recipe, Long> { }
```

Notes:
- it is not necessary add annotation because CrudRepository already has the annotation
- it is convention to put the repositories classes in a package called _repositories_
- it is convention to give the name of the repositories class - domain/entity + "repository"

### Database Initialization with Spring
**Hibernate DDL Auto**
- Hibernate property is set by Spring property _spring.jpa.hibernate.dll-auto_
- Options:
	- none
	- validate
		- advised to use in production.
		- validate if the schema not change
	- update
		- if schema change, automatically update
	- create
		- init data base creation
	- create-drop
- Spring Boot  will use create-drop for embedded databases (h2, derby)

**Initialize with Hibernate**
- data can be loaded from import.sql
	- this is Hibernate feature (not Spring)
	- must be on root of class path
	- Only executed if Hibernate's add-auto property is set to _create_ or _create-drop_

**Spring JDBC**
- Spring's DataSource initializer via Spring Boot  will by default load from the class path
	- schema.sql
	- data.sql
- Spring Boot will also load from
	- schema-${platform}.sql
	- data-${platform}.sql
	- must be set spring.datasource.platform

### Spring Data JPA Query Methods
**Create dynamic finders**

Steps:
- create method inside the interface
- Spring Data JPA will provide the implementation
- no need write SQL queries

Example
```java
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String description);
}
```
Notes:
- Spring 4 vs Spring 5
	- Spring 5 uses Optional in find methods


