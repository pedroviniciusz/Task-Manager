package com.example.task.core.repository;

import com.example.task.core.entity.Task;
import com.example.task.core.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    Task task;
    User user;

    @BeforeEach
    public void setUp() {
        user = new User(1);
        task = Task.builder()
                .id(1)
                .title("Any Title")
                .description("Any description")
                .dueDate(LocalDateTime.now())
                .completed(false)
                .items(Collections.emptyList())
                .user(user)
                .build();

        this.userRepository.save(user);
        this.taskRepository.save(task);
    }

    @Test
    @DisplayName("Should get task by user id")
    void shouldFindTaskByUserId() {
        List<Task> tasks = this.taskRepository.findTaskByUserId(user.getId());

        assertEquals(1, tasks.size());
        assertEquals("Any Title", tasks.getFirst().getTitle());
        assertEquals("Any description", tasks.getFirst().getDescription());
        assertFalse(tasks.getFirst().isCompleted());
    }

    @Test
    @DisplayName("Should find user tasks that will expires tomorrow")
    void shouldFindUserTasksThatWillExpiresTomorrow() {
        List<Task> tasks = this.taskRepository.findTaskByUserId(user.getId());

        Task task = tasks.getFirst();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        task.setDueDate(tomorrow);
        this.taskRepository.save(task);

        List<Task> expiringTasks = this.taskRepository.findTasksExpiringTomorrow(user.getId());

        assertEquals(expiringTasks.getFirst().getDueDate(), tomorrow);

    }

    @Test
    @DisplayName("Should find user tasks that have already expired")
    void shouldFindUserTasksThatHaveAlreadyExpired() {
        List<Task> tasks = this.taskRepository.findTaskByUserId(user.getId());

        Task task = tasks.getFirst();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        task.setDueDate(yesterday);
        this.taskRepository.save(task);

        List<Task> expiredTasks = this.taskRepository.findExpiredTasks(user.getId());

        assertEquals(expiredTasks.getFirst().getDueDate(), yesterday);
    }

}