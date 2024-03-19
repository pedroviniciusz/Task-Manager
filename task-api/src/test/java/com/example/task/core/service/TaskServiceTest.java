package com.example.task.core.service;

import com.example.task.core.entity.Task;
import com.example.task.core.exception.EntityNotFoundException;
import com.example.task.core.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    TaskService service;

    @Mock
    TaskRepository taskRepository;

    Task task;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id(1)
                .title("Any Title")
                .description("Any description")
                .dueDate(LocalDateTime.now())
                .completed(false)
                .items(Collections.emptyList())
                .build();
    }

    @Test
    @DisplayName("Should find task by id")
    void shouldFindTaskById() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        Task foundedTask = service.findTaskById(task.getId());

        assertEquals(task, foundedTask);
        verify(taskRepository, times(1)).findById(anyInt());
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    @DisplayName("Should throw exception when there is no task by this id")
    void shouldThrowExceptionWhenThereIsNoTaskById() {
        final EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> service.findTaskById(anyInt()));

        assertThat(e.getMessage()).isEqualTo("There is no Task by this id");
        verify(taskRepository, times(1)).findById(anyInt());
        verifyNoMoreInteractions(taskRepository);

    }

    @Test
    @DisplayName("Should create a task")
    void shouldCreateTask() {
        service.createTask(task);

        verify(taskRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should update a task")
    void shouldUpdateTask() {
        Task updatedTask = Task.builder()
                .id(1)
                .title("Any Title 2")
                .description("Any description 2")
                .dueDate(LocalDateTime.now())
                .completed(true)
                .items(Collections.emptyList())
                .build();

        when(taskRepository.save(any())).thenReturn(updatedTask);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        service.updateTask(task.getId(), updatedTask);

        Task foundedTask = service.findTaskById(task.getId());

        assertEquals(updatedTask.getTitle(), foundedTask.getTitle());
        assertEquals(updatedTask.getDescription(), foundedTask.getDescription());
        assertEquals(updatedTask.getDueDate(), foundedTask.getDueDate());
        assertEquals(updatedTask.isCompleted(), foundedTask.isCompleted());
        assertEquals(updatedTask.getItems(), foundedTask.getItems());

    }

    @Test
    @DisplayName("Should delete a task by id")
    void shouldDeleteTaskById() {
        service.deleteTask(task.getId());

        verify(taskRepository, times(1)).deleteById(anyInt());
    }
}