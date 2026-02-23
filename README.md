# Task Management System

This is a simple task management system that allows users to create, update, view and delete tasks. Users also can filter their tasks by status.


## Features

- Create, Update, View and Delete tasks
- Filter Task by Status
- Implemented pagination for the list of tasks
- Implemented centralized logging using AOP.



## My Tech Stack

**Backend:**  
Java & SpringBoot 

**Database:**  
PostgreSQL 



## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java >= 17+
- Spring Boot >= 3.x
- Relational database (e.g. PostgreSQL, MySQL)
- Maven as a project management tool



## Installation

Here's how to install my project:

```bash
# Clone my repository
git clone https://github.com/ManishDhamala/Task-Management-System.git
cd Task-Management-System

# Configure the database in the src/main/resources/application.properties
spring.datasource.url=your_database_url
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# Build the application
 mvn clean install

# Then run the application
 mvn spring-boot:run
```

Visit `http://localhost:8080/api/v1/task` to see it working
