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
- Subselects
- ACID Compliance
    - Atomicity - all or nothing
    - Consistency - transactions are valid to rules of the DB
    - Isolation - Results of transactions are as if they were done end to end
    - Durability - Once a transaction is committed, it remains so

### Code Coverage Configuration
https://codecov.io
- it has a free account that allow 1 repository
- allow sing up with GtiHub

**Setup Steps:**
- in codecov website
- sing up with GtiHub account
- add a repository from Github
- in InteliJ project - add report in Readme file
    - in codecov go to
        - repository ->  settings -> badge
        - copy Markdown
    - in Readme file add the copy text


### Spring Boot Configuration for MySQL
Steps:
- add maven dependency
- add property file, like yam
    - define data source properties
        - Spring Boot will create a bean