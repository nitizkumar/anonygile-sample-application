package com.anonygile.tool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.anonygile.tool.domain.enumeration.TaskStatus;

/**
 * A TaskBucket.
 */
@Entity
@Table(name = "task_bucket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskBucket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = "taskBuckets", allowSetters = true)
    private Board board;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TaskBucket name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskBucket status(TaskStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Board getBoard() {
        return board;
    }

    public TaskBucket board(Board board) {
        this.board = board;
        return this;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskBucket)) {
            return false;
        }
        return id != null && id.equals(((TaskBucket) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskBucket{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
