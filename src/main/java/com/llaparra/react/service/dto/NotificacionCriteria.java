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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.llaparra.react.domain.Notificacion} entity. This class is used
 * in {@link com.llaparra.react.web.rest.NotificacionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notificacions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NotificacionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private BooleanFilter isRead;

    private StringFilter title;

    private StringFilter summary;

    public NotificacionCriteria() {
    }

    public NotificacionCriteria(NotificacionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.isRead = other.isRead == null ? null : other.isRead.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.summary = other.summary == null ? null : other.summary.copy();
    }

    @Override
    public NotificacionCriteria copy() {
        return new NotificacionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public BooleanFilter getIsRead() {
        return isRead;
    }

    public void setIsRead(BooleanFilter isRead) {
        this.isRead = isRead;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getSummary() {
        return summary;
    }

    public void setSummary(StringFilter summary) {
        this.summary = summary;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NotificacionCriteria that = (NotificacionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(isRead, that.isRead) &&
            Objects.equals(title, that.title) &&
            Objects.equals(summary, that.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        isRead,
        title,
        summary
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificacionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (isRead != null ? "isRead=" + isRead + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (summary != null ? "summary=" + summary + ", " : "") +
            "}";
    }

}
