package com.llaparra.react.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.llaparra.react.domain.Notificacion} entity.
 */
public class NotificacionDTO implements Serializable {
    
    private Long id;

    @NotNull
    private LocalDate date;

    private Boolean isRead;

    @NotNull
    @Size(max = 50)
    private String title;

    @NotNull
    @Size(max = 100)
    private String summary;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificacionDTO)) {
            return false;
        }

        return id != null && id.equals(((NotificacionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificacionDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", isRead='" + isIsRead() + "'" +
            ", title='" + getTitle() + "'" +
            ", summary='" + getSummary() + "'" +
            "}";
    }
}
