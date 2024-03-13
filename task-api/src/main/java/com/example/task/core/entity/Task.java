package com.example.task.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "task")
@Entity(name = "task")
@SQLDelete(sql = "UPDATE task SET deleted = true WHERE id=?")
@SQLRestriction("deleted = false")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
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

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private TaskStatus status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskItem> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
