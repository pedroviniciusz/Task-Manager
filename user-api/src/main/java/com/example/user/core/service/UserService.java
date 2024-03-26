package com.example.user.core.service;

import com.example.user.config.Enconder;
import com.example.user.core.messages.Messages;
import com.example.user.core.entity.User;
import com.example.user.core.exception.DuplicateEntityException;
import com.example.user.core.exception.EntityNotFoundException;
import com.example.user.core.exception.UserException;
import com.example.user.core.producer.RabbitMQProducer;
import com.example.user.core.repository.UserRepository;
import com.example.user.core.util.CpfUtil;
import com.example.user.web.dto.EmailDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.user.core.messages.Messages.*;
import static com.example.user.core.util.NullUtil.isNullOrEmpty;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final Enconder enconder;
    private final RabbitMQProducer rabbitMQProducer;
    private final Messages messages;

    public User findUserById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(messages.getMessage(USER_NOT_FOUND)));
    }

    public User findUserByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(messages.getMessage(USER_NOT_FOUND)));
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public void createUser(User user) {

        validate(user);

        user.setCpf(CpfUtil.formatCpf(user.getCpf()));
        enconder.encodePassword(user);

        rabbitMQProducer.sendMessage(new EmailDto(user.getEmail(), messages.getMessage(SUBJECT), messages.getMessage(BODY, user.getUsername())));

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

    @Transactional
    public void updateCpf(int id, String cpf) {

        if (!CpfUtil.isCpf(cpf))
            throw new UserException(messages.getMessage(INVALID_CPF));

        repository.updateCpf(CpfUtil.formatCpf(cpf), id);
    }

    private void validate(User user) {

        if (isNullOrEmpty(user.getPassword()))
            throw new UserException(messages.getMessage(PASSWORD_CANT_BE_EMPTY));

        if (!CpfUtil.isCpf(user.getCpf()))
            throw new UserException(messages.getMessage(INVALID_CPF));

        if (repository.existsUserByCpf(CpfUtil.formatCpf(user.getCpf())))
            throw new DuplicateEntityException(messages.getMessage(ALREADY_EXISTS_USER_BY_THIS_CPF));

        if (repository.existsUserByUsername(user.getUsername()))
            throw new DuplicateEntityException(messages.getMessage(ALREADY_EXISTS_USER_BY_THIS_USERNAME));

        if (repository.existsUserByEmail(user.getEmail()))
            throw new DuplicateEntityException(messages.getMessage(ALREADY_EXISTS_USER_BY_THIS_EMAIL));

    }

}
