package com.example.user.core.entity;

import com.example.user.core.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Table(name = "users")
@Entity(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String cpf;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRole userRole;
}
