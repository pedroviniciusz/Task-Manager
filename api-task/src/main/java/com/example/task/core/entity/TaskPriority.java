package com.example.task.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Table(name = "task_priority")
@Entity(name = "task_priority")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class TaskPriority extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 3890510550099339280L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String level;

    public TaskPriority(int id) {
        this.id = id;
    }
}
