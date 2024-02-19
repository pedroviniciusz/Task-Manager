package com.example.task.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4398517752483175566L;

    public abstract int getId();

    @JsonIgnore
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    private Boolean deleted;

    @PrePersist
    private void setCreated() {
        setCreatedAt(LocalDateTime.now());
        setDeleted(false);
    }

    @PreUpdate
    private void setUpdated() {
        setUpdatedAt(LocalDateTime.now());
    }

}