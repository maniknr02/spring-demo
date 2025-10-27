package com.example.myfirstapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2,max = 50)
    @Column(nullable = false, length = 50)
    private String name;

    @Setter
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be empty")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password should not be Empty")
    private String password;

    private LocalDateTime  createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected  void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public User() {}

    public User( String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


}
