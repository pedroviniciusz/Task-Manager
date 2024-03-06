package com.example.user.core.repository;

import com.example.user.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    boolean existsUserByCpf(String cpf);

    @Modifying
    @Query(value = "update users u set u.cpf = :cpf where u.id = :id")
    void updateCpf(String cpf, int id);

}
