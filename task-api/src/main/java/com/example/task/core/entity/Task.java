package com.example.task.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.io.Serial;
import java.time.LocalDateTime;

@Table(name = "task")
@Entity(name = "task")
@SQLDelete(sql = "UPDATE task SET deleted = true WHERE id=?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -7347995602470914128L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
