package com.example.examplespring.controllers;

import com.example.examplespring.interfaces.UserInterface;
import com.example.examplespring.models.User;
import com.example.examplespring.repositories.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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