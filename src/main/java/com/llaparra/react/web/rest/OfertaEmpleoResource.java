package com.llaparra.react.web.rest;

import com.llaparra.react.service.OfertaEmpleoService;
import com.llaparra.react.web.rest.errors.BadRequestAlertException;
import com.llaparra.react.service.dto.OfertaEmpleoDTO;
import com.llaparra.react.service.dto.OfertaEmpleoCriteria;
import com.llaparra.react.service.OfertaEmpleoQueryService;

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
 * REST controller for managing {@link com.llaparra.react.domain.OfertaEmpleo}.
 */
@RestController
@RequestMapping("/api")
public class OfertaEmpleoResource {

    private final Logger log = LoggerFactory.getLogger(OfertaEmpleoResource.class);

    private final OfertaEmpleoService ofertaEmpleoService;

    private final OfertaEmpleoQueryService ofertaEmpleoQueryService;

    public OfertaEmpleoResource(OfertaEmpleoService ofertaEmpleoService, OfertaEmpleoQueryService ofertaEmpleoQueryService) {
        this.ofertaEmpleoService = ofertaEmpleoService;
        this.ofertaEmpleoQueryService = ofertaEmpleoQueryService;
    }

    /**
     * {@code GET  /oferta-empleos} : get all the ofertaEmpleos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ofertaEmpleos in body.
     */
    @GetMapping("/oferta-empleos")
    public ResponseEntity<List<OfertaEmpleoDTO>> getAllOfertaEmpleos(OfertaEmpleoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OfertaEmpleos by criteria: {}", criteria);
        Page<OfertaEmpleoDTO> page = ofertaEmpleoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /oferta-empleos/count} : count all the ofertaEmpleos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/oferta-empleos/count")
    public ResponseEntity<Long> countOfertaEmpleos(OfertaEmpleoCriteria criteria) {
        log.debug("REST request to count OfertaEmpleos by criteria: {}", criteria);
        return ResponseEntity.ok().body(ofertaEmpleoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /oferta-empleos/:id} : get the "id" ofertaEmpleo.
     *
     * @param id the id of the ofertaEmpleoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ofertaEmpleoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/oferta-empleos/{id}")
    public ResponseEntity<OfertaEmpleoDTO> getOfertaEmpleo(@PathVariable Long id) {
        log.debug("REST request to get OfertaEmpleo : {}", id);
        Optional<OfertaEmpleoDTO> ofertaEmpleoDTO = ofertaEmpleoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ofertaEmpleoDTO);
    }
}
