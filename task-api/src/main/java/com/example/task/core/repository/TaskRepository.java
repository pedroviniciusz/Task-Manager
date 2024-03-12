package com.example.task.core.repository;

import com.example.task.core.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findTaskByUserId(int id);

    @Query(value = """
            SELECT * FROM task t
            WHERE t.user_id = :userId
            AND t.completed = false
            AND t.deleted = false
            AND date(t.due_date) = CURRENT_DATE + 1""",
            nativeQuery = true)
    List<Task> findTasksExpiringTomorrow(@Param("userId") Integer userId);

    @Query(value = """
            SELECT * FROM task t
            WHERE t.user_id = :userId
            AND t.completed = false
            AND t.deleted = false
            AND date(t.due_date) <= CURRENT_DATE""",
            nativeQuery = true)
    List<Task> findExpiredTasks(@Param("userId") Integer userId);

}
