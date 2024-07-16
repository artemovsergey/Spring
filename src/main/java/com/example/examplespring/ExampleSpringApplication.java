package com.example.examplespring;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAdminServer
@SpringBootApplication
@EnableAsync
public class ExampleSpringApplication {

    public static void main(String[] args) {


        Configuration configuration = new Configuration();
        // Загрузка конфигурации из файла или другого источника
        configuration.configure();

        // Создание SessionFactory
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        SpringApplication.run(ExampleSpringApplication.class, args);


    }

}
