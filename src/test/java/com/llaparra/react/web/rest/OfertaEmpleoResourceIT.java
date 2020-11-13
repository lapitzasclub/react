package com.llaparra.react.web.rest;

import com.llaparra.react.ReactApp;
import com.llaparra.react.domain.OfertaEmpleo;
import com.llaparra.react.repository.OfertaEmpleoRepository;
import com.llaparra.react.service.OfertaEmpleoService;
import com.llaparra.react.service.dto.OfertaEmpleoDTO;
import com.llaparra.react.service.mapper.OfertaEmpleoMapper;
import com.llaparra.react.service.dto.OfertaEmpleoCriteria;
import com.llaparra.react.service.OfertaEmpleoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OfertaEmpleoResource} REST controller.
 */
@SpringBootTest(classes = ReactApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OfertaEmpleoResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PLACE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SLOTS = 1;
    private static final Integer UPDATED_SLOTS = 2;
    private static final Integer SMALLER_SLOTS = 1 - 1;

    private static final String DEFAULT_CONTRACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT = "BBBBBBBBBB";

    private static final String DEFAULT_TERM = "AAAAAAAAAA";
    private static final String UPDATED_TERM = "BBBBBBBBBB";

    @Autowired
    private OfertaEmpleoRepository ofertaEmpleoRepository;

    @Autowired
    private OfertaEmpleoMapper ofertaEmpleoMapper;

    @Autowired
    private OfertaEmpleoService ofertaEmpleoService;

    @Autowired
    private OfertaEmpleoQueryService ofertaEmpleoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfertaEmpleoMockMvc;

    private OfertaEmpleo ofertaEmpleo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfertaEmpleo createEntity(EntityManager em) {
        OfertaEmpleo ofertaEmpleo = new OfertaEmpleo()
            .title(DEFAULT_TITLE)
            .place(DEFAULT_PLACE)
            .slots(DEFAULT_SLOTS)
            .contract(DEFAULT_CONTRACT)
            .term(DEFAULT_TERM);
        return ofertaEmpleo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfertaEmpleo createUpdatedEntity(EntityManager em) {
        OfertaEmpleo ofertaEmpleo = new OfertaEmpleo()
            .title(UPDATED_TITLE)
            .place(UPDATED_PLACE)
            .slots(UPDATED_SLOTS)
            .contract(UPDATED_CONTRACT)
            .term(UPDATED_TERM);
        return ofertaEmpleo;
    }

    @BeforeEach
    public void initTest() {
        ofertaEmpleo = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleos() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList
        restOfertaEmpleoMockMvc.perform(get("/api/oferta-empleos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ofertaEmpleo.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE)))
            .andExpect(jsonPath("$.[*].slots").value(hasItem(DEFAULT_SLOTS)))
            .andExpect(jsonPath("$.[*].contract").value(hasItem(DEFAULT_CONTRACT)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)));
    }
    
    @Test
    @Transactional
    public void getOfertaEmpleo() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get the ofertaEmpleo
        restOfertaEmpleoMockMvc.perform(get("/api/oferta-empleos/{id}", ofertaEmpleo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ofertaEmpleo.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE))
            .andExpect(jsonPath("$.slots").value(DEFAULT_SLOTS))
            .andExpect(jsonPath("$.contract").value(DEFAULT_CONTRACT))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM));
    }


    @Test
    @Transactional
    public void getOfertaEmpleosByIdFiltering() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        Long id = ofertaEmpleo.getId();

        defaultOfertaEmpleoShouldBeFound("id.equals=" + id);
        defaultOfertaEmpleoShouldNotBeFound("id.notEquals=" + id);

        defaultOfertaEmpleoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOfertaEmpleoShouldNotBeFound("id.greaterThan=" + id);

        defaultOfertaEmpleoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOfertaEmpleoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOfertaEmpleosByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where title equals to DEFAULT_TITLE
        defaultOfertaEmpleoShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the ofertaEmpleoList where title equals to UPDATED_TITLE
        defaultOfertaEmpleoShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where title not equals to DEFAULT_TITLE
        defaultOfertaEmpleoShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the ofertaEmpleoList where title not equals to UPDATED_TITLE
        defaultOfertaEmpleoShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultOfertaEmpleoShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the ofertaEmpleoList where title equals to UPDATED_TITLE
        defaultOfertaEmpleoShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where title is not null
        defaultOfertaEmpleoShouldBeFound("title.specified=true");

        // Get all the ofertaEmpleoList where title is null
        defaultOfertaEmpleoShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllOfertaEmpleosByTitleContainsSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where title contains DEFAULT_TITLE
        defaultOfertaEmpleoShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the ofertaEmpleoList where title contains UPDATED_TITLE
        defaultOfertaEmpleoShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where title does not contain DEFAULT_TITLE
        defaultOfertaEmpleoShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the ofertaEmpleoList where title does not contain UPDATED_TITLE
        defaultOfertaEmpleoShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllOfertaEmpleosByPlaceIsEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where place equals to DEFAULT_PLACE
        defaultOfertaEmpleoShouldBeFound("place.equals=" + DEFAULT_PLACE);

        // Get all the ofertaEmpleoList where place equals to UPDATED_PLACE
        defaultOfertaEmpleoShouldNotBeFound("place.equals=" + UPDATED_PLACE);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByPlaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where place not equals to DEFAULT_PLACE
        defaultOfertaEmpleoShouldNotBeFound("place.notEquals=" + DEFAULT_PLACE);

        // Get all the ofertaEmpleoList where place not equals to UPDATED_PLACE
        defaultOfertaEmpleoShouldBeFound("place.notEquals=" + UPDATED_PLACE);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByPlaceIsInShouldWork() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where place in DEFAULT_PLACE or UPDATED_PLACE
        defaultOfertaEmpleoShouldBeFound("place.in=" + DEFAULT_PLACE + "," + UPDATED_PLACE);

        // Get all the ofertaEmpleoList where place equals to UPDATED_PLACE
        defaultOfertaEmpleoShouldNotBeFound("place.in=" + UPDATED_PLACE);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByPlaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where place is not null
        defaultOfertaEmpleoShouldBeFound("place.specified=true");

        // Get all the ofertaEmpleoList where place is null
        defaultOfertaEmpleoShouldNotBeFound("place.specified=false");
    }
                @Test
    @Transactional
    public void getAllOfertaEmpleosByPlaceContainsSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where place contains DEFAULT_PLACE
        defaultOfertaEmpleoShouldBeFound("place.contains=" + DEFAULT_PLACE);

        // Get all the ofertaEmpleoList where place contains UPDATED_PLACE
        defaultOfertaEmpleoShouldNotBeFound("place.contains=" + UPDATED_PLACE);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByPlaceNotContainsSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where place does not contain DEFAULT_PLACE
        defaultOfertaEmpleoShouldNotBeFound("place.doesNotContain=" + DEFAULT_PLACE);

        // Get all the ofertaEmpleoList where place does not contain UPDATED_PLACE
        defaultOfertaEmpleoShouldBeFound("place.doesNotContain=" + UPDATED_PLACE);
    }


    @Test
    @Transactional
    public void getAllOfertaEmpleosBySlotsIsEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where slots equals to DEFAULT_SLOTS
        defaultOfertaEmpleoShouldBeFound("slots.equals=" + DEFAULT_SLOTS);

        // Get all the ofertaEmpleoList where slots equals to UPDATED_SLOTS
        defaultOfertaEmpleoShouldNotBeFound("slots.equals=" + UPDATED_SLOTS);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosBySlotsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where slots not equals to DEFAULT_SLOTS
        defaultOfertaEmpleoShouldNotBeFound("slots.notEquals=" + DEFAULT_SLOTS);

        // Get all the ofertaEmpleoList where slots not equals to UPDATED_SLOTS
        defaultOfertaEmpleoShouldBeFound("slots.notEquals=" + UPDATED_SLOTS);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosBySlotsIsInShouldWork() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where slots in DEFAULT_SLOTS or UPDATED_SLOTS
        defaultOfertaEmpleoShouldBeFound("slots.in=" + DEFAULT_SLOTS + "," + UPDATED_SLOTS);

        // Get all the ofertaEmpleoList where slots equals to UPDATED_SLOTS
        defaultOfertaEmpleoShouldNotBeFound("slots.in=" + UPDATED_SLOTS);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosBySlotsIsNullOrNotNull() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where slots is not null
        defaultOfertaEmpleoShouldBeFound("slots.specified=true");

        // Get all the ofertaEmpleoList where slots is null
        defaultOfertaEmpleoShouldNotBeFound("slots.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosBySlotsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where slots is greater than or equal to DEFAULT_SLOTS
        defaultOfertaEmpleoShouldBeFound("slots.greaterThanOrEqual=" + DEFAULT_SLOTS);

        // Get all the ofertaEmpleoList where slots is greater than or equal to (DEFAULT_SLOTS + 1)
        defaultOfertaEmpleoShouldNotBeFound("slots.greaterThanOrEqual=" + (DEFAULT_SLOTS + 1));
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosBySlotsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where slots is less than or equal to DEFAULT_SLOTS
        defaultOfertaEmpleoShouldBeFound("slots.lessThanOrEqual=" + DEFAULT_SLOTS);

        // Get all the ofertaEmpleoList where slots is less than or equal to SMALLER_SLOTS
        defaultOfertaEmpleoShouldNotBeFound("slots.lessThanOrEqual=" + SMALLER_SLOTS);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosBySlotsIsLessThanSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where slots is less than DEFAULT_SLOTS
        defaultOfertaEmpleoShouldNotBeFound("slots.lessThan=" + DEFAULT_SLOTS);

        // Get all the ofertaEmpleoList where slots is less than (DEFAULT_SLOTS + 1)
        defaultOfertaEmpleoShouldBeFound("slots.lessThan=" + (DEFAULT_SLOTS + 1));
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosBySlotsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where slots is greater than DEFAULT_SLOTS
        defaultOfertaEmpleoShouldNotBeFound("slots.greaterThan=" + DEFAULT_SLOTS);

        // Get all the ofertaEmpleoList where slots is greater than SMALLER_SLOTS
        defaultOfertaEmpleoShouldBeFound("slots.greaterThan=" + SMALLER_SLOTS);
    }


    @Test
    @Transactional
    public void getAllOfertaEmpleosByContractIsEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where contract equals to DEFAULT_CONTRACT
        defaultOfertaEmpleoShouldBeFound("contract.equals=" + DEFAULT_CONTRACT);

        // Get all the ofertaEmpleoList where contract equals to UPDATED_CONTRACT
        defaultOfertaEmpleoShouldNotBeFound("contract.equals=" + UPDATED_CONTRACT);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByContractIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where contract not equals to DEFAULT_CONTRACT
        defaultOfertaEmpleoShouldNotBeFound("contract.notEquals=" + DEFAULT_CONTRACT);

        // Get all the ofertaEmpleoList where contract not equals to UPDATED_CONTRACT
        defaultOfertaEmpleoShouldBeFound("contract.notEquals=" + UPDATED_CONTRACT);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByContractIsInShouldWork() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where contract in DEFAULT_CONTRACT or UPDATED_CONTRACT
        defaultOfertaEmpleoShouldBeFound("contract.in=" + DEFAULT_CONTRACT + "," + UPDATED_CONTRACT);

        // Get all the ofertaEmpleoList where contract equals to UPDATED_CONTRACT
        defaultOfertaEmpleoShouldNotBeFound("contract.in=" + UPDATED_CONTRACT);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByContractIsNullOrNotNull() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where contract is not null
        defaultOfertaEmpleoShouldBeFound("contract.specified=true");

        // Get all the ofertaEmpleoList where contract is null
        defaultOfertaEmpleoShouldNotBeFound("contract.specified=false");
    }
                @Test
    @Transactional
    public void getAllOfertaEmpleosByContractContainsSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where contract contains DEFAULT_CONTRACT
        defaultOfertaEmpleoShouldBeFound("contract.contains=" + DEFAULT_CONTRACT);

        // Get all the ofertaEmpleoList where contract contains UPDATED_CONTRACT
        defaultOfertaEmpleoShouldNotBeFound("contract.contains=" + UPDATED_CONTRACT);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByContractNotContainsSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where contract does not contain DEFAULT_CONTRACT
        defaultOfertaEmpleoShouldNotBeFound("contract.doesNotContain=" + DEFAULT_CONTRACT);

        // Get all the ofertaEmpleoList where contract does not contain UPDATED_CONTRACT
        defaultOfertaEmpleoShouldBeFound("contract.doesNotContain=" + UPDATED_CONTRACT);
    }


    @Test
    @Transactional
    public void getAllOfertaEmpleosByTermIsEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where term equals to DEFAULT_TERM
        defaultOfertaEmpleoShouldBeFound("term.equals=" + DEFAULT_TERM);

        // Get all the ofertaEmpleoList where term equals to UPDATED_TERM
        defaultOfertaEmpleoShouldNotBeFound("term.equals=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByTermIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where term not equals to DEFAULT_TERM
        defaultOfertaEmpleoShouldNotBeFound("term.notEquals=" + DEFAULT_TERM);

        // Get all the ofertaEmpleoList where term not equals to UPDATED_TERM
        defaultOfertaEmpleoShouldBeFound("term.notEquals=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByTermIsInShouldWork() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where term in DEFAULT_TERM or UPDATED_TERM
        defaultOfertaEmpleoShouldBeFound("term.in=" + DEFAULT_TERM + "," + UPDATED_TERM);

        // Get all the ofertaEmpleoList where term equals to UPDATED_TERM
        defaultOfertaEmpleoShouldNotBeFound("term.in=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where term is not null
        defaultOfertaEmpleoShouldBeFound("term.specified=true");

        // Get all the ofertaEmpleoList where term is null
        defaultOfertaEmpleoShouldNotBeFound("term.specified=false");
    }
                @Test
    @Transactional
    public void getAllOfertaEmpleosByTermContainsSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where term contains DEFAULT_TERM
        defaultOfertaEmpleoShouldBeFound("term.contains=" + DEFAULT_TERM);

        // Get all the ofertaEmpleoList where term contains UPDATED_TERM
        defaultOfertaEmpleoShouldNotBeFound("term.contains=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    public void getAllOfertaEmpleosByTermNotContainsSomething() throws Exception {
        // Initialize the database
        ofertaEmpleoRepository.saveAndFlush(ofertaEmpleo);

        // Get all the ofertaEmpleoList where term does not contain DEFAULT_TERM
        defaultOfertaEmpleoShouldNotBeFound("term.doesNotContain=" + DEFAULT_TERM);

        // Get all the ofertaEmpleoList where term does not contain UPDATED_TERM
        defaultOfertaEmpleoShouldBeFound("term.doesNotContain=" + UPDATED_TERM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOfertaEmpleoShouldBeFound(String filter) throws Exception {
        restOfertaEmpleoMockMvc.perform(get("/api/oferta-empleos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ofertaEmpleo.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE)))
            .andExpect(jsonPath("$.[*].slots").value(hasItem(DEFAULT_SLOTS)))
            .andExpect(jsonPath("$.[*].contract").value(hasItem(DEFAULT_CONTRACT)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)));

        // Check, that the count call also returns 1
        restOfertaEmpleoMockMvc.perform(get("/api/oferta-empleos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOfertaEmpleoShouldNotBeFound(String filter) throws Exception {
        restOfertaEmpleoMockMvc.perform(get("/api/oferta-empleos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOfertaEmpleoMockMvc.perform(get("/api/oferta-empleos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOfertaEmpleo() throws Exception {
        // Get the ofertaEmpleo
        restOfertaEmpleoMockMvc.perform(get("/api/oferta-empleos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
