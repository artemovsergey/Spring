# Spring

# application.properties

```
spring.application.name=ExampleSpring
spring.boot.admin.client.url = http://localhost:8090
server.port=8090

spring.datasource.generate-unique-name=false
spring.datasource.name=Example

spring.datasource.url=jdbc:postgresql://localhost:5432/UserStore
spring.datasource.username=postgres
spring.datasource.password=root
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true

#
spring.jpa.hibernate.ddl-auto=update

#spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml

spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
springdoc.api-docs.path=/api-docs

management.endpoints.web.exposure.include=health,info,beans,conditions,mappings,loggers
management.endpoint.health.show-details = always
```


# Event Loop

В цикле событий все обрабатывается как событие, включая запросы и обратные вызовы
от интенсивных операций, таких как работа с базой данных и сетью. Когда требуется выполнить дорогостоящую операцию, цикл событий регистрирует обратный вызов для этой операции, которая будет выполняться
параллельно, в то время как он переходит к обработке других событий.

# Spring WebFlux

Основное назначение Spring WebFlux — обеспечение высокопроизводительной, масштабируемой платформы для обработки HTTP-запросов в реальном времени или для приложений, требующих высокой доступности и быстрой реакции на изменения данных.

# RSocket

Как SignalR

# Deploy

- Build ```mvnw package```
- Run  ```java -jar filename```


https://www.cloudfoundry.org/

# Авто сборка Docker Image from Spring

- ```mvnw spring-boot:build-image```
- ```mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=your_image_name_here```

- Настройка имени образа

```xnl
<plugin>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-maven-plugin</artifactId>
 <configuration>
 <image>
 <name>tacocloud/${project.artifactId}:${project.version}</name>
 </image>
 </configuration>
</plugin>
```

# Dockerfile

```
FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

# Logging

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!-- Форматируем дату и время
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
            -->
            <pattern> %highlight(%-5level) %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="info">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```
# Hilbernate Config

```xml
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <!-- Database connection settings -->
        <property name="connection.driver_class">org.postgresql.Driver</property>
        <property name="connection.url">jdbc:postgresql://localhost:5432/UserStore</property>
        <property name="connection.username">postgres</property>
        <property name="connection.password">root</property>

        <!-- SQL dialect -->
        <property name="dialect">org.hibernate.dialect.PostgreSQLDialect</property>

        <!-- Echo all executed SQL to stdout -->
        <property name="show_sql">true</property>

        <!-- Naming strategy -->
        <property name="hbm2ddl.auto">update</property>

        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.format_sql">true</property>

    </session-factory>
</hibernate-configuration>

```

# Migration Liquibase

- db/changelog/master.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd">

    <include file="db/changelog/users-table-changelog.xml"/>
    <include file="db/changelog/addRoleToUser.xml"/>

</databaseChangeLog>
```

- users-table-changelog.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"

        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd"

        xmlns:db="http://www.liquibase.org/xml/ns/dbchangelog">

    <db:createTable tableName="users">
        <db:column name="id" type="int">
            <db:constraints primaryKey="true" nullable="false"/>
        </db:column>
        <db:column name="name" type="varchar(255)">
            <db:constraints nullable="false"/>
        </db:column>
        <db:column name="age" type="int">
            <db:constraints nullable="false"/>
        </db:column>
    </db:createTable>

</databaseChangeLog>
```

# Seed

```java
@Configuration
public class Init {
    @Bean
    public CommandLineRunner dataLoader(UserInterface repo){
        User user = new User(null,"user1",1, "admin");
        return args -> repo.save(user);
    }
}
```

# Model User

```java
package com.example.examplespring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.relational.core.mapping.Column;

@Data
@Entity(name="\"Users\"")
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @Column("Name")
    @NotBlank(message="name is required")
    @Size(min=5, message="Name must be at least 5 characters long")
    private String Name;

    @NotNull
    private Integer Age;

    @NotNull
    private String Role;

}

```

# Interface User

```java
package com.example.examplespring.interfaces;

import com.example.examplespring.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserInterface extends CrudRepository<User, Integer> {

//    Iterable<User> findAll();
//
//    Optional<User> findById(String id);
//
//    User save(User user);
}

```

# RestAPI Controller

```java
package com.example.examplespring.controllers;

import com.example.examplespring.interfaces.UserInterface;
import com.example.examplespring.models.User;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@RestController
@Slf4j
@RequestMapping(path="/api/users", produces="application/json")
@CrossOrigin(origins="http://localhost:5173")
public class UserController {

    @Autowired
    private UserInterface repo;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        try {
            User savedUser = repo.save(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    public CompletableFuture<Iterable<User>> getUsers() {
        log.info("Fetching all users asynchronously");
        return CompletableFuture.completedFuture(repo.findAll());
    }

    // Получение пользователя по ID
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<User>> getUserById(@PathVariable Integer id) {
        log.info("Getting user by ID asynchronously: {}", id);
        return CompletableFuture.supplyAsync(() -> {
            Optional<User> userOptional = repo.findById(id);
            if (userOptional.isPresent()) {
                return ResponseEntity.ok(userOptional.get());
            } else {
                log.warn("User not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        });
    }



    // Обновление существующего пользователя
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<User>> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        log.info("Updating user with ID: {} asynchronously with details: {}", id, userDetails);
        return CompletableFuture.supplyAsync(() -> {
            Optional<User> userOptional = repo.findById(id);
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                existingUser.setName(userDetails.getName());
                existingUser.setAge(userDetails.getAge());
                existingUser.setRole(userDetails.getRole());
                final User updatedUser = repo.save(existingUser);
                return ResponseEntity.ok(updatedUser);
            } else {
                log.warn("User not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        });
    }

    // Удаление пользователя по ID
    @DeleteMapping("/{id}")
    public CompletableFuture<Void> deleteUser(@PathVariable Integer id) {
        log.info("Deleting user with ID: {} asynchronously", id);
        return CompletableFuture.runAsync(() -> {
            repo.deleteById(id);
            log.info("User with ID: {} deleted successfully", id);
        });
    }

}
```
