package com.example.task.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Table(name = "task_status")
@Entity(name = "task_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskStatus extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -2746933145681299065L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    public TaskStatus(int id) {
        this.id = id;
    }
}
