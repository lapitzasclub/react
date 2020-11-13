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

import com.llaparra.react.domain.OfertaEmpleo;
import com.llaparra.react.domain.*; // for static metamodels
import com.llaparra.react.repository.OfertaEmpleoRepository;
import com.llaparra.react.service.dto.OfertaEmpleoCriteria;
import com.llaparra.react.service.dto.OfertaEmpleoDTO;
import com.llaparra.react.service.mapper.OfertaEmpleoMapper;

/**
 * Service for executing complex queries for {@link OfertaEmpleo} entities in the database.
 * The main input is a {@link OfertaEmpleoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OfertaEmpleoDTO} or a {@link Page} of {@link OfertaEmpleoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OfertaEmpleoQueryService extends QueryService<OfertaEmpleo> {

    private final Logger log = LoggerFactory.getLogger(OfertaEmpleoQueryService.class);

    private final OfertaEmpleoRepository ofertaEmpleoRepository;

    private final OfertaEmpleoMapper ofertaEmpleoMapper;

    public OfertaEmpleoQueryService(OfertaEmpleoRepository ofertaEmpleoRepository, OfertaEmpleoMapper ofertaEmpleoMapper) {
        this.ofertaEmpleoRepository = ofertaEmpleoRepository;
        this.ofertaEmpleoMapper = ofertaEmpleoMapper;
    }

    /**
     * Return a {@link List} of {@link OfertaEmpleoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OfertaEmpleoDTO> findByCriteria(OfertaEmpleoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OfertaEmpleo> specification = createSpecification(criteria);
        return ofertaEmpleoMapper.toDto(ofertaEmpleoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OfertaEmpleoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OfertaEmpleoDTO> findByCriteria(OfertaEmpleoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OfertaEmpleo> specification = createSpecification(criteria);
        return ofertaEmpleoRepository.findAll(specification, page)
            .map(ofertaEmpleoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OfertaEmpleoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OfertaEmpleo> specification = createSpecification(criteria);
        return ofertaEmpleoRepository.count(specification);
    }

    /**
     * Function to convert {@link OfertaEmpleoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OfertaEmpleo> createSpecification(OfertaEmpleoCriteria criteria) {
        Specification<OfertaEmpleo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OfertaEmpleo_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), OfertaEmpleo_.title));
            }
            if (criteria.getPlace() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlace(), OfertaEmpleo_.place));
            }
            if (criteria.getSlots() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSlots(), OfertaEmpleo_.slots));
            }
            if (criteria.getContract() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContract(), OfertaEmpleo_.contract));
            }
            if (criteria.getTerm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTerm(), OfertaEmpleo_.term));
            }
        }
        return specification;
    }
}
