package com.example.examplespring.seed;

import com.example.examplespring.interfaces.UserInterface;
import com.example.examplespring.models.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Init {

    @Bean
    public CommandLineRunner dataLoader(UserInterface repo){


        User user = new User(null,"user1",1, "admin");
        return args -> repo.save(user);
    }


}
