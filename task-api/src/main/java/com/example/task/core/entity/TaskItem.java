package com.example.task.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Table(name = "task_item")
@Entity(name = "task_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskItem extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 5723763756174147993L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public TaskItem(int id) {
        this.id = id;
    }
}
