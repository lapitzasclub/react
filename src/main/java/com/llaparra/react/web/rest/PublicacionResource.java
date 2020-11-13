package com.llaparra.react.web.rest;

import com.llaparra.react.service.PublicacionService;
import com.llaparra.react.web.rest.errors.BadRequestAlertException;
import com.llaparra.react.service.dto.PublicacionDTO;
import com.llaparra.react.service.dto.PublicacionCriteria;
import com.llaparra.react.service.PublicacionQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.llaparra.react.domain.Publicacion}.
 */
@RestController
@RequestMapping("/api")
public class PublicacionResource {

    private final Logger log = LoggerFactory.getLogger(PublicacionResource.class);

    private final PublicacionService publicacionService;

    private final PublicacionQueryService publicacionQueryService;

    public PublicacionResource(PublicacionService publicacionService, PublicacionQueryService publicacionQueryService) {
        this.publicacionService = publicacionService;
        this.publicacionQueryService = publicacionQueryService;
    }

    /**
     * {@code GET  /publicacions} : get all the publicacions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicacions in body.
     */
    @GetMapping("/publicacions")
    public ResponseEntity<List<PublicacionDTO>> getAllPublicacions(PublicacionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Publicacions by criteria: {}", criteria);
        Page<PublicacionDTO> page = publicacionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /publicacions/count} : count all the publicacions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/publicacions/count")
    public ResponseEntity<Long> countPublicacions(PublicacionCriteria criteria) {
        log.debug("REST request to count Publicacions by criteria: {}", criteria);
        return ResponseEntity.ok().body(publicacionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /publicacions/:id} : get the "id" publicacion.
     *
     * @param id the id of the publicacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/publicacions/{id}")
    public ResponseEntity<PublicacionDTO> getPublicacion(@PathVariable Long id) {
        log.debug("REST request to get Publicacion : {}", id);
        Optional<PublicacionDTO> publicacionDTO = publicacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicacionDTO);
    }
}
