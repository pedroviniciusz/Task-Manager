package com.example.task.core.repository;

import com.example.task.core.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findTaskByUserIdAndDeletedFalse(int id);

}
