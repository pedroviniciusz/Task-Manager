package com.example.task.web.dto;

import com.example.task.core.entity.Task;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TaskDto {

    private final int id;
    private final String title;
    private final String description;
    private final String endDate;
    private final boolean completed;
    private final int priorityId;
    private final String priority;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.endDate = task.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.completed = task.isCompleted();
        this.priorityId = task.getPriority().getId();
        this.priority = task.getPriority().getLevel();
    }

    public static TaskDto transferToDto(Task task) {
        return new TaskDto(task);
    }

    public static List<TaskDto> transferToDtoList(List<Task> taks) {
        return taks.stream().map(TaskDto::new).collect(Collectors.toList());
    }

}
