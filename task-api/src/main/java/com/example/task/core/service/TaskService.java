package com.example.task.core.service;

import com.example.task.core.client.UserClient;
import com.example.task.core.entity.Task;
import com.example.task.core.entity.User;
import com.example.task.core.exception.EntityNotFoundException;
import com.example.task.core.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final UserClient userInterface;

    public Task findTaskById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no Task by this id"));
    }

    public List<Task> findTasksExpiringTomorrow(String username) {
        int userId = getUserClient(username).getId();
        return repository.findTasksExpiringTomorrow(userId);
    }

    public List<Task> findExpiredTasks(String username) {
        int userId = getUserClient(username).getId();
        return repository.findExpiredTasks(userId);
    }

    public List<Task> findTaskByUser(String username) {
        int userId = getUserClient(username).getId();
        return repository.findTaskByUserId(userId);
    }

    public void createTask(Task task) {
        repository.save(task);
    }

    public void updateTask(int id, Task updatedTask) {

        Task task = findTaskById(id);
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setCompleted(updatedTask.isCompleted());

        repository.save(task);
    }

    @Transactional
    public void deleteTask(int id) {
        repository.deleteById(id);
    }

    private User getUserClient(String username) {
        return userInterface.findByUsername(username).getBody();
    }

}
