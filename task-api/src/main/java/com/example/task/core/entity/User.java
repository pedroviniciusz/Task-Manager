package com.example.task.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

@Table(name = "users")
@Entity(name = "users")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class User extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 6025280143767566404L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String username;

    @Column
    private String email;

    public User(int id) {
        this.id = id;
    }
}
