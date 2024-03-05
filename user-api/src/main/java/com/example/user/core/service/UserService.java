package com.example.user.core.service;

import com.example.user.config.Enconder;
import com.example.user.core.entity.User;
import com.example.user.core.exception.DuplicateEntityException;
import com.example.user.core.exception.EntityNotFoundException;
import com.example.user.core.exception.UserException;
import com.example.user.core.repository.UserRepository;
import com.example.user.core.util.CpfUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.user.core.util.NullUtil.isNullOrEmpty;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final Enconder enconder;

    public User findUserById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no user by this id"));
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public void createUser(User user) {

        validate(user);

        user.setCpf(CpfUtil.formatCpf(user.getCpf()));
        enconder.encodePassword(user);

        repository.save(user);
    }

    public void updateUser(int id, User updatedUser) {

        validate(updatedUser);

        User user = findUserById(id);
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setCpf(CpfUtil.formatCpf(user.getCpf()));
        enconder.encodePassword(user);

        repository.save(user);
    }

    public void deleteUser(int id) {
        repository.deleteById(id);
    }

    @Transactional
    public void updateCpf(int id, String cpf) {

        if (!CpfUtil.isCpf(cpf))
            throw new UserException("Invalid CPF");

        repository.updateCpf(CpfUtil.formatCpf(cpf), id);
    }

    private void validate(User user) {

        if (isNullOrEmpty(user.getPassword()))
            throw new UserException("Password can not be empty");

        if (!CpfUtil.isCpf(user.getCpf()))
            throw new UserException("Invalid CPF");

        if (repository.existsUserByCpf(CpfUtil.formatCpf(user.getCpf())))
            throw new DuplicateEntityException("Already exists user by this CPF");

        if (repository.existsUserByUsername(user.getUsername()))
            throw new DuplicateEntityException("Already exists user by this username");

        if (repository.existsUserByEmail(user.getEmail()))
            throw new DuplicateEntityException("Already exists user by this email");

    }

}
