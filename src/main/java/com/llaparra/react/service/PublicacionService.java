package com.llaparra.react.service;

import com.llaparra.react.domain.Publicacion;
import com.llaparra.react.repository.PublicacionRepository;
import com.llaparra.react.service.dto.PublicacionDTO;
import com.llaparra.react.service.mapper.PublicacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Publicacion}.
 */
@Service
@Transactional
public class PublicacionService {

    private final Logger log = LoggerFactory.getLogger(PublicacionService.class);

    private final PublicacionRepository publicacionRepository;

    private final PublicacionMapper publicacionMapper;

    public PublicacionService(PublicacionRepository publicacionRepository, PublicacionMapper publicacionMapper) {
        this.publicacionRepository = publicacionRepository;
        this.publicacionMapper = publicacionMapper;
    }

    /**
     * Save a publicacion.
     *
     * @param publicacionDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicacionDTO save(PublicacionDTO publicacionDTO) {
        log.debug("Request to save Publicacion : {}", publicacionDTO);
        Publicacion publicacion = publicacionMapper.toEntity(publicacionDTO);
        publicacion = publicacionRepository.save(publicacion);
        return publicacionMapper.toDto(publicacion);
    }

    /**
     * Get all the publicacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Publicacions");
        return publicacionRepository.findAll(pageable)
            .map(publicacionMapper::toDto);
    }


    /**
     * Get one publicacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicacionDTO> findOne(Long id) {
        log.debug("Request to get Publicacion : {}", id);
        return publicacionRepository.findById(id)
            .map(publicacionMapper::toDto);
    }

    /**
     * Delete the publicacion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Publicacion : {}", id);
        publicacionRepository.deleteById(id);
    }
}
