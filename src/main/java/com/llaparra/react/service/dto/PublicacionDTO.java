package com.llaparra.react.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.llaparra.react.domain.Publicacion} entity.
 */
public class PublicacionDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 500)
    private String title;

    @NotNull
    private LocalDate date;

    @NotNull
    @Size(max = 500)
    private String summary;

    private String content;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicacionDTO)) {
            return false;
        }

        return id != null && id.equals(((PublicacionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicacionDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", summary='" + getSummary() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
