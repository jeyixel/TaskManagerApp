package com.example.taskmanagerapp.entity; // Adjusted to match folder name

import jakarta.persistence.*; // Fixes the @Entity error
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks") // Good practice: explicit table name (plural)
@Data // Lombok: Generates getters, setters, toString, etc.
@NoArgsConstructor // JPA needs a constructor with no arguments
@AllArgsConstructor // Handy for creating objects easily
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private Long id;

    private String title;

    private String description;

    private boolean completed;
}