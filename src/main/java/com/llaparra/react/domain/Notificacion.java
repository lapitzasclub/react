package com.llaparra.react.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Notificacion.
 */
@Entity
@Table(name = "notificacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "is_read")
    private Boolean isRead;

    @NotNull
    @Size(max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @NotNull
    @Size(max = 100)
    @Column(name = "summary", length = 100, nullable = false)
    private String summary;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Notificacion date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isIsRead() {
        return isRead;
    }

    public Notificacion isRead(Boolean isRead) {
        this.isRead = isRead;
        return this;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public String getTitle() {
        return title;
    }

    public Notificacion title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public Notificacion summary(String summary) {
        this.summary = summary;
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notificacion)) {
            return false;
        }
        return id != null && id.equals(((Notificacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notificacion{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", isRead='" + isIsRead() + "'" +
            ", title='" + getTitle() + "'" +
            ", summary='" + getSummary() + "'" +
            "}";
    }
}
