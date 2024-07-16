# Spring
Технический журнал Spring


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

