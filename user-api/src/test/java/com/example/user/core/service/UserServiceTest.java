package com.example.user.core.service;

import com.example.user.config.Enconder;
import com.example.user.core.entity.User;
import com.example.user.core.enums.UserRole;
import com.example.user.core.exception.DuplicateEntityException;
import com.example.user.core.exception.EntityNotFoundException;
import com.example.user.core.exception.UserException;
import com.example.user.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UserRepository userRepository;

    @Mock
    Enconder enconder;

    User user;

    @BeforeEach
    public void setUp() {

        user = new User(1, "pedro", "pedro@test.com", "749.175.350-77", "123", UserRole.USER);

    }

    @Test
    @DisplayName("Should find user by id")
    void shouldFindUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User foundedUser = service.findUserById(user.getId());

        assertEquals(user, foundedUser);
        verify(userRepository, times(1)).findById(user.getId());
        verifyNoMoreInteractions(userRepository);

    }

    @Test
    @DisplayName("Should throw exception when there is no user by this id")
    void shouldThrowExceptionWhenThereIsNoUserById() {
        final EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> {
            service.findUserById(anyInt());
        });

        assertThat(e.getMessage()).isEqualTo("There is no user by this id");
        verify(userRepository, times(1)).findById(anyInt());
        verifyNoMoreInteractions(userRepository);

    }

    @Test
    @DisplayName("Should find user by username")
    void shouldFindUserByUsername() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User foundedUser = service.findUserByUsername(user.getUsername());

        assertEquals(user, foundedUser);
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verifyNoMoreInteractions(userRepository);

    }

    @Test
    @DisplayName("Should throw exception when there is no user by this username")
    void shouldThrowExceptionWhenThereIsNoUserByUsername() {
        final EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> {
            service.findUserByUsername(anyString());
        });

        assertThat(e.getMessage()).isEqualTo("There is no user by this username");
        verify(userRepository, times(1)).findByUsername(anyString());
        verifyNoMoreInteractions(userRepository);

    }

    @Test
    @DisplayName("Should find all users")
    void shouldFindAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<User> users = service.findAllUsers();

        assertEquals(Collections.singletonList(user), users);
        assertThat(users).hasSize(1);
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);

    }

    @Test
    @DisplayName("Should create an user")
    void shouldCreateUser() {
        service.createUser(user);

        verify(userRepository, times(1)).save(user);

    }

    @Test
    @DisplayName("Should throw exception if password is null when creating an user")
    void shouldThrowExceptionIfPasswordIsNullWhenCreatingUser() {
        user.setPassword("");
        final UserException e = assertThrows(UserException.class, () -> {
            service.createUser(user);
        });

        assertThat(e.getMessage()).isEqualTo("Password can not be empty");
        verifyNoInteractions(userRepository);

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1234", "123456789101", "77777777777", "36496784150"})
    @DisplayName("Should throw exception if CPF is invalid")
    void shouldThrowExceptionIfCpfIsInvalidWhenCreatingUser(String cpf) {
        user.setCpf(cpf);
        final UserException e = assertThrows(UserException.class, () -> {
            service.createUser(user);
        });

        assertThat(e.getMessage()).isEqualTo("Invalid CPF");
        verifyNoInteractions(userRepository);

    }

    @Test
    @DisplayName("Should throw exception if has already exists user by this CPF")
    void shouldThrowExceptionIfHasAlreadyExistsUserByThisCpf() {
        when(userRepository.existsUserByCpf(anyString())).thenReturn(true);

        final DuplicateEntityException e = assertThrows(DuplicateEntityException.class, () -> {
            service.createUser(user);
        });

        assertThat(e.getMessage()).isEqualTo("Already exists user by this CPF");
        verify(userRepository, times(0)).save(any());

    }

    @Test
    @DisplayName("Should throw exception if has already exists user by this username")
    void shouldThrowExceptionIfHasAlreadyExistsUserByThisUsername() {
        when(userRepository.existsUserByUsername(anyString())).thenReturn(true);

        final DuplicateEntityException e = assertThrows(DuplicateEntityException.class, () -> {
            service.createUser(user);
        });

        assertThat(e.getMessage()).isEqualTo("Already exists user by this username");
        verify(userRepository, times(0)).save(any());

    }

    @Test
    @DisplayName("Should throw exception if has already exists user by this e-mail")
    void shouldThrowExceptionIfHasAlreadyExistsUserByThisEmail() {
        when(userRepository.existsUserByEmail(anyString())).thenReturn(true);

        final DuplicateEntityException e = assertThrows(DuplicateEntityException.class, () -> {
            service.createUser(user);
        });

        assertThat(e.getMessage()).isEqualTo("Already exists user by this email");
        verify(userRepository, times(0)).save(any());

    }

    @Test
    @DisplayName("Should update an user")
    void shouldUpdateUser() {
        User updatedUser = new User(1, "pedro1", "pedro1@test.com", "619.464.080-42", "1233", UserRole.USER);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(updatedUser);

        service.updateUser(1, updatedUser);
        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(user.getEmail(), updatedUser.getEmail());
        //assertEquals(user.getPassword(), updatedUser.getPassword());
        assertEquals(user.getUserRole().getRole(), updatedUser.getUserRole().getRole());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should delete an user by id")
    void shouldDeleteUserById() {
        service.deleteUser(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    @DisplayName("Should update just the cpf")
    void shouldUpdateJustCpf() {
        user.setCpf("706.315.700-04");

        service.updateCpf(user.getId(), user.getCpf());

        verify(userRepository, times(1)).updateCpf(user.getCpf(), user.getId());
    }
}