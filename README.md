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
