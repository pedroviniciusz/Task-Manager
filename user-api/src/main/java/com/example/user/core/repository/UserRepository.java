package com.example.user.core.repository;

import com.example.user.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

}
