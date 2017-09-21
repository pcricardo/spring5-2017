## Module 15: Using MySQL with Spring

### Introduction to MySQL
**About**
- MySQL is a Relational Database System (aka RDMS)
- MySQL is owned by Oracle, but MySQL is open source and free to use

**MySQL History**
- MySQL was created in 1995 by a Swedish company
- Under GPL, MySQL was open sourced in 2000
- In 2005, Oracle acquired Innobase, the company behind the storage backend of MySQL
- Michael (Monty) Widenius left Sun Microsystems and developed a fork of MySQL called MariaDB.
    - The MariaDB API remains 100% compatible with MySQL

**MySQL Features**
- MySQL is a Relational Database Management System
- MySQL is developed in C and C++, making it portable across many different platforms
- There are MySQL clients for all popular languages.
    - C, C++, Eiffel, Java, Perl, PHP, Python, Ruby,Tcl, and ODBC, JDBC, ADO.NET
- Stored Procedures
- Triggers
- Cursors
- Updatable Views
- Query Caching
- Sub-selects
- ACID Compliance
    - Atomicity - all or nothing
    - Consistency - transactions are valid to rules of the DB
    - Isolation - Results of transactions are as if they were done end to end
    - Durability - Once a transaction is committed, it remains so

### Configuration of MySQL
- setup a MySQL instance, local instance or docker
- for a docker:
    - create a docker for MySQL
        - `docker run -d --name guru-mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3306:3306  mysql`
    - with a MySQL tool, create schema, users and permissions for users
        - MySQL uses a localhost or other IP to identify the user
        - for docker container it is necessary setup user with '%' ('sfg_dev_user'@'%'), because docker
            - '%' it is a wildcard to represent any host

### CircleCI Configuration
Steps:
- in CirleCI website, add the GutHub repository
- in InteliJ
    - add a folder '.circleci' and a file 'config.yml'
    - in the file, copy the code from CircleCI website
- in CirleCI website, start building project

### Code Coverage Configuration
https://codecov.io
- it has a free account that allow 1 repository
- allow sing up with GtiHub

**Setup Steps:**
- in codecov website
- sing up with GtiHub account
- add a repository from Github
- in InteliJ project
    - add maven dependency
    ```xml
    <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>cobertura-maven-plugin</artifactId>
        <version>2.7</version>
        <configuration>
            <formats>
                <format>html</format>
                <format>xml</format>
            </formats>
            <check />
        </configuration>
    </plugin>
    ```
    - in CirleCI config.yml, replace
    `- run: mvn integration-test`
    with
    ```
      # run tests! and gen code coverage
      - run: mvn integration-test cobertura:cobertura

      - store_test_results:
          path: target/surefire-reports

      - run:
          name: Send to CodeCov
          command: bash <(curl -s https://codecov.io/bash)
    ```
    - add report in Readme file
        - in codecov go to
            - repository ->  settings -> badge
            - copy Markdown
        - in Readme file add the copy text


### Spring Boot Configuration for MySQL
Steps:
- add maven dependency - MySQL Driver
    ```
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    ```
- in resource folder, add property file, like '.property' or '.yml'
    - define data source properties
        - Spring Boot will create a bean
    - example
    ```
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/sfg_prod
        username: sfg_prod_user
        password: guru
      jpa:
        hibernate:
          ddl-auto: validate
        database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
        database: mysql
        show-sql: false
    ```

### Schema Generation With Hibernate
It is trick, because the spring no dot terminate a sql statement lines with ';'

**Steps:**
- add code in the property file that have the connection to the database
    ```
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/sfg_dev
        username: sfg_dev_user
        password: guru
      jpa:
        hibernate:
          ddl-auto: validate
        database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
        database: mysql
        show-sql: true
        properties:
          javax:
            persistence:
              schema-generation:
                create-source: metadata
                scripts:
                  action: create
                  create-target: pc_database_create.sql
    ```
- run the application with the correct profile
- it will generate errors
- it will generate the file defined in 'create-target: pc_database_create.sql'
- search the file 'pc_database_create.sql'
    - copy the content and run directly in the data base
- comment the lines
    ```
        properties:
          javax:
            persistence:
              schema-generation:
                create-source: metadata
                scripts:
                  action: create
                  create-target: pc_database_create.sql
    ```

### Refactor Database Initialization for MySQL
Setup init environment for more than one database
- profiles:
    - default
    - dev
    - prod
- 2 environment:
    - default - h2 database
    - dev/prod - MySQL


Files
- Bootstrap - define one file for each environment
    - define one file for default profile
        - default profile use the h2 database, every time the application runs the data base is clean
        - load all necessary data to test the application
    - define one file for the prod/dev profile
        - only load the tables that are lists of values
        - first check if the tables already are empty, if true insert the data
- application properties - define one file for each profile
    - application-default.properties
        - define spring data source platform to 'h2'
        - activate logs (optional)
        - Example
            ```
            spring.datasource.platform=h2
            spring.jpa.show-sql=true
            ```
    - application-dev.yml
        - add spring data source platform to 'mysql'
    - application-prod.yml
        - add spring data source platform to 'mysql'
- data source platform - define scrips files by platform
    - create script file that Spring Boot will run every time the application runs
    - create the file data-h2.sql, used in default profile
        - it will only run the file script defined in the application-[profile].properties
    - not create file for dev/prod profile
        - the init data is defined in Bootstrap file
- create a file 'data-h2.sql'
    - add the scripts to load the init data for tables that are lists of values

**Notes:**
- the default profile is active when there are not define any profile
- data source platform
    - this scripts files, Spring Boot will run every time the application runs
    - name format for this files: 'data-[spring.datasource.platform].sql'
