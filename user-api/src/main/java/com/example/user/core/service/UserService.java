package com.example.user.core.service;

import com.example.user.config.Enconder;
import com.example.user.core.entity.User;
import com.example.user.core.exception.DuplicateEntityException;
import com.example.user.core.exception.EntityNotFoundException;
import com.example.user.core.exception.UserException;
import com.example.user.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.user.core.util.NullUtil.isNullOrEmpty;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final Enconder enconder;

    public User findUserById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no user by this id"));
    }

    public void createUser(User user) {

        validate(user);

        enconder.encodePassword(user);
        repository.save(user);
    }

    public void updateUser(int id, User updatedUser) {

        validate(updatedUser);

        User user = findUserById(id);
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        enconder.encodePassword(user);

        repository.save(user);
    }

    public void deleteUser(int id) {
        repository.deleteById(id);
    }

    private void validate(User user) {

        if (isNullOrEmpty(user.getPassword()))
            throw new UserException("Password can not be empty");

        if (repository.existsUserByUsername(user.getUsername()))
            throw new DuplicateEntityException("Already exists user by this username");

        if (repository.existsUserByEmail(user.getEmail()))
            throw new DuplicateEntityException("Already exists user by this email");

    }

}
