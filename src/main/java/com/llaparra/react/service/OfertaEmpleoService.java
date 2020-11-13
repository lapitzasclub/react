package com.llaparra.react.service;

import com.llaparra.react.domain.OfertaEmpleo;
import com.llaparra.react.repository.OfertaEmpleoRepository;
import com.llaparra.react.service.dto.OfertaEmpleoDTO;
import com.llaparra.react.service.mapper.OfertaEmpleoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OfertaEmpleo}.
 */
@Service
@Transactional
public class OfertaEmpleoService {

    private final Logger log = LoggerFactory.getLogger(OfertaEmpleoService.class);

    private final OfertaEmpleoRepository ofertaEmpleoRepository;

    private final OfertaEmpleoMapper ofertaEmpleoMapper;

    public OfertaEmpleoService(OfertaEmpleoRepository ofertaEmpleoRepository, OfertaEmpleoMapper ofertaEmpleoMapper) {
        this.ofertaEmpleoRepository = ofertaEmpleoRepository;
        this.ofertaEmpleoMapper = ofertaEmpleoMapper;
    }

    /**
     * Save a ofertaEmpleo.
     *
     * @param ofertaEmpleoDTO the entity to save.
     * @return the persisted entity.
     */
    public OfertaEmpleoDTO save(OfertaEmpleoDTO ofertaEmpleoDTO) {
        log.debug("Request to save OfertaEmpleo : {}", ofertaEmpleoDTO);
        OfertaEmpleo ofertaEmpleo = ofertaEmpleoMapper.toEntity(ofertaEmpleoDTO);
        ofertaEmpleo = ofertaEmpleoRepository.save(ofertaEmpleo);
        return ofertaEmpleoMapper.toDto(ofertaEmpleo);
    }

    /**
     * Get all the ofertaEmpleos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OfertaEmpleoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OfertaEmpleos");
        return ofertaEmpleoRepository.findAll(pageable)
            .map(ofertaEmpleoMapper::toDto);
    }


    /**
     * Get one ofertaEmpleo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OfertaEmpleoDTO> findOne(Long id) {
        log.debug("Request to get OfertaEmpleo : {}", id);
        return ofertaEmpleoRepository.findById(id)
            .map(ofertaEmpleoMapper::toDto);
    }

    /**
     * Delete the ofertaEmpleo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OfertaEmpleo : {}", id);
        ofertaEmpleoRepository.deleteById(id);
    }
}
