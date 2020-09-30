package com.anonygile.tool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.anonygile.tool.domain.enumeration.TaskType;

import com.anonygile.tool.domain.enumeration.TaskStatus;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaskType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "priority")
    private Double priority;

    @Column(name = "size")
    private String size;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private TaskBucket bucket;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Task title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskType getType() {
        return type;
    }

    public Task type(TaskType type) {
        this.type = type;
        return this;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Task status(TaskStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Task startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Task endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Double getPriority() {
        return priority;
    }

    public Task priority(Double priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }

    public String getSize() {
        return size;
    }

    public Task size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public TaskBucket getBucket() {
        return bucket;
    }

    public Task bucket(TaskBucket taskBucket) {
        this.bucket = taskBucket;
        return this;
    }

    public void setBucket(TaskBucket taskBucket) {
        this.bucket = taskBucket;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", priority=" + getPriority() +
            ", size='" + getSize() + "'" +
            "}";
    }
}
