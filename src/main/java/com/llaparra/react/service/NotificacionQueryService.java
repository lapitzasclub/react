package com.llaparra.react.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.llaparra.react.domain.Notificacion;
import com.llaparra.react.domain.*; // for static metamodels
import com.llaparra.react.repository.NotificacionRepository;
import com.llaparra.react.service.dto.NotificacionCriteria;
import com.llaparra.react.service.dto.NotificacionDTO;
import com.llaparra.react.service.mapper.NotificacionMapper;

/**
 * Service for executing complex queries for {@link Notificacion} entities in the database.
 * The main input is a {@link NotificacionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotificacionDTO} or a {@link Page} of {@link NotificacionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotificacionQueryService extends QueryService<Notificacion> {

    private final Logger log = LoggerFactory.getLogger(NotificacionQueryService.class);

    private final NotificacionRepository notificacionRepository;

    private final NotificacionMapper notificacionMapper;

    public NotificacionQueryService(NotificacionRepository notificacionRepository, NotificacionMapper notificacionMapper) {
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
    }

    /**
     * Return a {@link List} of {@link NotificacionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotificacionDTO> findByCriteria(NotificacionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Notificacion> specification = createSpecification(criteria);
        return notificacionMapper.toDto(notificacionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NotificacionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificacionDTO> findByCriteria(NotificacionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Notificacion> specification = createSpecification(criteria);
        return notificacionRepository.findAll(specification, page)
            .map(notificacionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotificacionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Notificacion> specification = createSpecification(criteria);
        return notificacionRepository.count(specification);
    }

    /**
     * Function to convert {@link NotificacionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Notificacion> createSpecification(NotificacionCriteria criteria) {
        Specification<Notificacion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Notificacion_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Notificacion_.date));
            }
            if (criteria.getIsRead() != null) {
                specification = specification.and(buildSpecification(criteria.getIsRead(), Notificacion_.isRead));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Notificacion_.title));
            }
            if (criteria.getSummary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSummary(), Notificacion_.summary));
            }
        }
        return specification;
    }
}
