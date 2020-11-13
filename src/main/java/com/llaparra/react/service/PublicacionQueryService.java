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

import com.llaparra.react.domain.Publicacion;
import com.llaparra.react.domain.*; // for static metamodels
import com.llaparra.react.repository.PublicacionRepository;
import com.llaparra.react.service.dto.PublicacionCriteria;
import com.llaparra.react.service.dto.PublicacionDTO;
import com.llaparra.react.service.mapper.PublicacionMapper;

/**
 * Service for executing complex queries for {@link Publicacion} entities in the database.
 * The main input is a {@link PublicacionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PublicacionDTO} or a {@link Page} of {@link PublicacionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PublicacionQueryService extends QueryService<Publicacion> {

    private final Logger log = LoggerFactory.getLogger(PublicacionQueryService.class);

    private final PublicacionRepository publicacionRepository;

    private final PublicacionMapper publicacionMapper;

    public PublicacionQueryService(PublicacionRepository publicacionRepository, PublicacionMapper publicacionMapper) {
        this.publicacionRepository = publicacionRepository;
        this.publicacionMapper = publicacionMapper;
    }

    /**
     * Return a {@link List} of {@link PublicacionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PublicacionDTO> findByCriteria(PublicacionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Publicacion> specification = createSpecification(criteria);
        return publicacionMapper.toDto(publicacionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PublicacionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicacionDTO> findByCriteria(PublicacionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Publicacion> specification = createSpecification(criteria);
        return publicacionRepository.findAll(specification, page)
            .map(publicacionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PublicacionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Publicacion> specification = createSpecification(criteria);
        return publicacionRepository.count(specification);
    }

    /**
     * Function to convert {@link PublicacionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Publicacion> createSpecification(PublicacionCriteria criteria) {
        Specification<Publicacion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Publicacion_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Publicacion_.title));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Publicacion_.date));
            }
            if (criteria.getSummary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSummary(), Publicacion_.summary));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), Publicacion_.content));
            }
        }
        return specification;
    }
}
