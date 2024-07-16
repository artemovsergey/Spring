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
