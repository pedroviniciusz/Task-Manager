package com.example.task.core.service;

import com.example.task.core.client.UserClient;
import com.example.task.core.entity.Task;
import com.example.task.core.entity.User;
import com.example.task.core.exception.EntityNotFoundException;
import com.example.task.core.messages.Messages;
import com.example.task.core.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.task.core.messages.Messages.TASK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final UserClient userClient;
    private final Messages messages;

    public Task findTaskById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(messages.getMessage(TASK_NOT_FOUND)));
    }

    public List<Task> findTasksExpiringTomorrow(String username) {
        return repository.findTasksExpiringTomorrow(getUserClient(username).getId());
    }

    public List<Task> findExpiredTasks(String username) {
        return repository.findExpiredTasks(getUserClient(username).getId());
    }

    public List<Task> findTaskByUser(String username) {
        return repository.findTaskByUserId(getUserClient(username).getId());
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
        return userClient.findByUsername(username).getBody();
    }

}
