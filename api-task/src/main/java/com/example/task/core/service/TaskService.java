package com.example.task.core.service;

import com.example.task.core.entity.Task;
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

    public Task findTaskById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no Task by this id"));
    }

    public List<Task> findTaskByUser(int id) {
        return repository.findTaskByUserIdAndDeletedFalse(id);
    }

    public void createTask(Task task) {
        repository.save(task);
    }

    public void updateTask(int id, Task updatedTask) {

        Task task = findTaskById(id);
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setEndDate(updatedTask.getEndDate());
        task.setCompleted(updatedTask.isCompleted());

        repository.save(task);
    }

    @Transactional
    public void deleteTask(int id) {
        repository.deleteById(id);
    }

}
