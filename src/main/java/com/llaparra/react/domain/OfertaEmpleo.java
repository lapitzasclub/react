package com.llaparra.react.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A OfertaEmpleo.
 */
@Entity
@Table(name = "oferta_empleo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OfertaEmpleo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @NotNull
    @Size(max = 500)
    @Column(name = "place", length = 500, nullable = false)
    private String place;

    @NotNull
    @Min(value = 1)
    @Max(value = 999)
    @Column(name = "slots", nullable = false)
    private Integer slots;

    @NotNull
    @Column(name = "contract", nullable = false)
    private String contract;

    @Size(max = 200)
    @Column(name = "term", length = 200)
    private String term;

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

    public OfertaEmpleo title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public OfertaEmpleo place(String place) {
        this.place = place;
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getSlots() {
        return slots;
    }

    public OfertaEmpleo slots(Integer slots) {
        this.slots = slots;
        return this;
    }

    public void setSlots(Integer slots) {
        this.slots = slots;
    }

    public String getContract() {
        return contract;
    }

    public OfertaEmpleo contract(String contract) {
        this.contract = contract;
        return this;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getTerm() {
        return term;
    }

    public OfertaEmpleo term(String term) {
        this.term = term;
        return this;
    }

    public void setTerm(String term) {
        this.term = term;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfertaEmpleo)) {
            return false;
        }
        return id != null && id.equals(((OfertaEmpleo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfertaEmpleo{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", place='" + getPlace() + "'" +
            ", slots=" + getSlots() +
            ", contract='" + getContract() + "'" +
            ", term='" + getTerm() + "'" +
            "}";
    }
}
