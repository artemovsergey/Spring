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
