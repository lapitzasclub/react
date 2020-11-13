package com.llaparra.react.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.llaparra.react.domain.OfertaEmpleo} entity. This class is used
 * in {@link com.llaparra.react.web.rest.OfertaEmpleoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /oferta-empleos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OfertaEmpleoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter place;

    private IntegerFilter slots;

    private StringFilter contract;

    private StringFilter term;

    public OfertaEmpleoCriteria() {
    }

    public OfertaEmpleoCriteria(OfertaEmpleoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.place = other.place == null ? null : other.place.copy();
        this.slots = other.slots == null ? null : other.slots.copy();
        this.contract = other.contract == null ? null : other.contract.copy();
        this.term = other.term == null ? null : other.term.copy();
    }

    @Override
    public OfertaEmpleoCriteria copy() {
        return new OfertaEmpleoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getPlace() {
        return place;
    }

    public void setPlace(StringFilter place) {
        this.place = place;
    }

    public IntegerFilter getSlots() {
        return slots;
    }

    public void setSlots(IntegerFilter slots) {
        this.slots = slots;
    }

    public StringFilter getContract() {
        return contract;
    }

    public void setContract(StringFilter contract) {
        this.contract = contract;
    }

    public StringFilter getTerm() {
        return term;
    }

    public void setTerm(StringFilter term) {
        this.term = term;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OfertaEmpleoCriteria that = (OfertaEmpleoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(place, that.place) &&
            Objects.equals(slots, that.slots) &&
            Objects.equals(contract, that.contract) &&
            Objects.equals(term, that.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        place,
        slots,
        contract,
        term
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfertaEmpleoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (place != null ? "place=" + place + ", " : "") +
                (slots != null ? "slots=" + slots + ", " : "") +
                (contract != null ? "contract=" + contract + ", " : "") +
                (term != null ? "term=" + term + ", " : "") +
            "}";
    }

}
