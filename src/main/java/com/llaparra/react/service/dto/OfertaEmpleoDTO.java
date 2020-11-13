package com.llaparra.react.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.llaparra.react.domain.OfertaEmpleo} entity.
 */
public class OfertaEmpleoDTO implements Serializable {
    
    private Long id;

    private String title;

    @NotNull
    @Size(max = 500)
    private String place;

    @NotNull
    @Min(value = 1)
    @Max(value = 999)
    private Integer slots;

    @NotNull
    private String contract;

    @Size(max = 200)
    private String term;

    
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getSlots() {
        return slots;
    }

    public void setSlots(Integer slots) {
        this.slots = slots;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfertaEmpleoDTO)) {
            return false;
        }

        return id != null && id.equals(((OfertaEmpleoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfertaEmpleoDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", place='" + getPlace() + "'" +
            ", slots=" + getSlots() +
            ", contract='" + getContract() + "'" +
            ", term='" + getTerm() + "'" +
            "}";
    }
}
