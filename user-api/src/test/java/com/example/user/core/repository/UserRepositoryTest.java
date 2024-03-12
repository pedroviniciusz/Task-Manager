package com.example.user.core.repository;

import com.example.user.core.entity.User;
import com.example.user.core.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User(1, "pedro", "pedro@test.com", "777.777.777-77", "", UserRole.USER);

        this.userRepository.save(user);
    }

    @Test
    @DisplayName("Should get user by username")
    void shouldFindByUsername() {
        Optional<User> foundedUser = this.userRepository.findByUsername(user.getUsername());

        assertThat(foundedUser).isPresent();
    }

    @Test
    @DisplayName("Should NOT get user by username")
    void shouldNotFindByUsername() {
        Optional<User> foundedUser = this.userRepository.findByUsername("");

        assertThat(foundedUser).isEmpty();
    }

    @Test
    @DisplayName("Should exists user by username")
    void shouldExistsUserByUsername() {
        boolean exists = this.userRepository.existsUserByUsername(user.getUsername());
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should NOT exists user by username")
    void shouldNotExistsUserByUsername() {
        boolean exists = this.userRepository.existsUserByUsername("");
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should exists user by E-mail")
    void shouldExistsUserByEmail() {
        boolean exists = this.userRepository.existsUserByEmail(user.getEmail());
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should NOT exists user by E-mail")
    void shouldNotExistsUserByEmail() {
        boolean exists = this.userRepository.existsUserByEmail("");
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should exists user by CPF")
    void shouldExistsUserByCpf() {
        boolean exists = this.userRepository.existsUserByCpf(user.getCpf());
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should NOT exists user by CPF")
    void shouldNotExistsUserByCpf() {
        boolean exists = this.userRepository.existsUserByCpf("");
        assertThat(exists).isFalse();
    }


    @Test
    @DisplayName("Should update user cpf by id")
    void updateCpf() {
        User foundedUser = this.userRepository.findByUsername(user.getUsername()).orElseThrow();

        final String newCpf = "888.888.888-88";
        foundedUser.setCpf(newCpf);
        this.userRepository.updateCpf(newCpf, foundedUser.getId());

        User updatedUser = this.userRepository.findById(foundedUser.getId()).orElseThrow();

        assertThat(updatedUser.getCpf()).isEqualTo(newCpf);

    }

}