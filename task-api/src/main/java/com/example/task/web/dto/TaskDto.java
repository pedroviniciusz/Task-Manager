package com.example.task.web.dto;

import com.example.task.core.entity.Task;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class TaskDto {

    private final int id;
    private final String title;
    private final String description;
    private final String dueDate;
    private final boolean completed;
    private final int priorityId;
    private final String priority;
    private final String status;
    private final List<TaskItemDto> items;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.completed = task.isCompleted();
        this.priorityId = task.getPriority().getId();
        this.priority = task.getPriority().getLevel();
        this.status = task.getStatus().getName();
        this.items = getTaskItemDtos(task);
    }

    public static TaskDto transferToDto(Task task) {
        return new TaskDto(task);
    }

    public static List<TaskDto> transferToDtoList(List<Task> taks) {
        return taks.stream().map(TaskDto::new).toList();
    }

    private List<TaskItemDto> getTaskItemDtos(Task task) {
        return task.getItems().stream()
                .map(item -> {
                    TaskItemDto taskItemDto = new TaskItemDto();
                    taskItemDto.setId(item.getId());
                    taskItemDto.setDescription(item.getDescription());
                    return taskItemDto;
                }).toList();
    }

}
