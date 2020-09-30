package com.anonygile.tool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SizeEstimate.
 */
@Entity
@Table(name = "size_estimate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SizeEstimate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "size")
    private String size;

    @Column(name = "estimate")
    private Double estimate;

    @ManyToOne
    @JsonIgnoreProperties(value = "sizeEstimates", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "sizeEstimates", allowSetters = true)
    private Board board;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public SizeEstimate size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getEstimate() {
        return estimate;
    }

    public SizeEstimate estimate(Double estimate) {
        this.estimate = estimate;
        return this;
    }

    public void setEstimate(Double estimate) {
        this.estimate = estimate;
    }

    public User getUser() {
        return user;
    }

    public SizeEstimate user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Board getBoard() {
        return board;
    }

    public SizeEstimate board(Board board) {
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
        if (!(o instanceof SizeEstimate)) {
            return false;
        }
        return id != null && id.equals(((SizeEstimate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SizeEstimate{" +
            "id=" + getId() +
            ", size='" + getSize() + "'" +
            ", estimate=" + getEstimate() +
            "}";
    }
}
