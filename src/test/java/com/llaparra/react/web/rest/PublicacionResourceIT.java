package com.llaparra.react.web.rest;

import com.llaparra.react.ReactApp;
import com.llaparra.react.domain.Publicacion;
import com.llaparra.react.repository.PublicacionRepository;
import com.llaparra.react.service.PublicacionService;
import com.llaparra.react.service.dto.PublicacionDTO;
import com.llaparra.react.service.mapper.PublicacionMapper;
import com.llaparra.react.service.dto.PublicacionCriteria;
import com.llaparra.react.service.PublicacionQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PublicacionResource} REST controller.
 */
@SpringBootTest(classes = ReactApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PublicacionResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private PublicacionMapper publicacionMapper;

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private PublicacionQueryService publicacionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicacionMockMvc;

    private Publicacion publicacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publicacion createEntity(EntityManager em) {
        Publicacion publicacion = new Publicacion()
            .title(DEFAULT_TITLE)
            .date(DEFAULT_DATE)
            .summary(DEFAULT_SUMMARY)
            .content(DEFAULT_CONTENT);
        return publicacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publicacion createUpdatedEntity(EntityManager em) {
        Publicacion publicacion = new Publicacion()
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .summary(UPDATED_SUMMARY)
            .content(UPDATED_CONTENT);
        return publicacion;
    }

    @BeforeEach
    public void initTest() {
        publicacion = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllPublicacions() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList
        restPublicacionMockMvc.perform(get("/api/publicacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getPublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get the publicacion
        restPublicacionMockMvc.perform(get("/api/publicacions/{id}", publicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicacion.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }


    @Test
    @Transactional
    public void getPublicacionsByIdFiltering() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        Long id = publicacion.getId();

        defaultPublicacionShouldBeFound("id.equals=" + id);
        defaultPublicacionShouldNotBeFound("id.notEquals=" + id);

        defaultPublicacionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPublicacionShouldNotBeFound("id.greaterThan=" + id);

        defaultPublicacionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPublicacionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPublicacionsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where title equals to DEFAULT_TITLE
        defaultPublicacionShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the publicacionList where title equals to UPDATED_TITLE
        defaultPublicacionShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where title not equals to DEFAULT_TITLE
        defaultPublicacionShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the publicacionList where title not equals to UPDATED_TITLE
        defaultPublicacionShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultPublicacionShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the publicacionList where title equals to UPDATED_TITLE
        defaultPublicacionShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where title is not null
        defaultPublicacionShouldBeFound("title.specified=true");

        // Get all the publicacionList where title is null
        defaultPublicacionShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllPublicacionsByTitleContainsSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where title contains DEFAULT_TITLE
        defaultPublicacionShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the publicacionList where title contains UPDATED_TITLE
        defaultPublicacionShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where title does not contain DEFAULT_TITLE
        defaultPublicacionShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the publicacionList where title does not contain UPDATED_TITLE
        defaultPublicacionShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllPublicacionsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where date equals to DEFAULT_DATE
        defaultPublicacionShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the publicacionList where date equals to UPDATED_DATE
        defaultPublicacionShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where date not equals to DEFAULT_DATE
        defaultPublicacionShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the publicacionList where date not equals to UPDATED_DATE
        defaultPublicacionShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where date in DEFAULT_DATE or UPDATED_DATE
        defaultPublicacionShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the publicacionList where date equals to UPDATED_DATE
        defaultPublicacionShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where date is not null
        defaultPublicacionShouldBeFound("date.specified=true");

        // Get all the publicacionList where date is null
        defaultPublicacionShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicacionsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where date is greater than or equal to DEFAULT_DATE
        defaultPublicacionShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the publicacionList where date is greater than or equal to UPDATED_DATE
        defaultPublicacionShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where date is less than or equal to DEFAULT_DATE
        defaultPublicacionShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the publicacionList where date is less than or equal to SMALLER_DATE
        defaultPublicacionShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where date is less than DEFAULT_DATE
        defaultPublicacionShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the publicacionList where date is less than UPDATED_DATE
        defaultPublicacionShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where date is greater than DEFAULT_DATE
        defaultPublicacionShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the publicacionList where date is greater than SMALLER_DATE
        defaultPublicacionShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllPublicacionsBySummaryIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where summary equals to DEFAULT_SUMMARY
        defaultPublicacionShouldBeFound("summary.equals=" + DEFAULT_SUMMARY);

        // Get all the publicacionList where summary equals to UPDATED_SUMMARY
        defaultPublicacionShouldNotBeFound("summary.equals=" + UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllPublicacionsBySummaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where summary not equals to DEFAULT_SUMMARY
        defaultPublicacionShouldNotBeFound("summary.notEquals=" + DEFAULT_SUMMARY);

        // Get all the publicacionList where summary not equals to UPDATED_SUMMARY
        defaultPublicacionShouldBeFound("summary.notEquals=" + UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllPublicacionsBySummaryIsInShouldWork() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where summary in DEFAULT_SUMMARY or UPDATED_SUMMARY
        defaultPublicacionShouldBeFound("summary.in=" + DEFAULT_SUMMARY + "," + UPDATED_SUMMARY);

        // Get all the publicacionList where summary equals to UPDATED_SUMMARY
        defaultPublicacionShouldNotBeFound("summary.in=" + UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllPublicacionsBySummaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where summary is not null
        defaultPublicacionShouldBeFound("summary.specified=true");

        // Get all the publicacionList where summary is null
        defaultPublicacionShouldNotBeFound("summary.specified=false");
    }
                @Test
    @Transactional
    public void getAllPublicacionsBySummaryContainsSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where summary contains DEFAULT_SUMMARY
        defaultPublicacionShouldBeFound("summary.contains=" + DEFAULT_SUMMARY);

        // Get all the publicacionList where summary contains UPDATED_SUMMARY
        defaultPublicacionShouldNotBeFound("summary.contains=" + UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllPublicacionsBySummaryNotContainsSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where summary does not contain DEFAULT_SUMMARY
        defaultPublicacionShouldNotBeFound("summary.doesNotContain=" + DEFAULT_SUMMARY);

        // Get all the publicacionList where summary does not contain UPDATED_SUMMARY
        defaultPublicacionShouldBeFound("summary.doesNotContain=" + UPDATED_SUMMARY);
    }


    @Test
    @Transactional
    public void getAllPublicacionsByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where content equals to DEFAULT_CONTENT
        defaultPublicacionShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the publicacionList where content equals to UPDATED_CONTENT
        defaultPublicacionShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByContentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where content not equals to DEFAULT_CONTENT
        defaultPublicacionShouldNotBeFound("content.notEquals=" + DEFAULT_CONTENT);

        // Get all the publicacionList where content not equals to UPDATED_CONTENT
        defaultPublicacionShouldBeFound("content.notEquals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByContentIsInShouldWork() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultPublicacionShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the publicacionList where content equals to UPDATED_CONTENT
        defaultPublicacionShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where content is not null
        defaultPublicacionShouldBeFound("content.specified=true");

        // Get all the publicacionList where content is null
        defaultPublicacionShouldNotBeFound("content.specified=false");
    }
                @Test
    @Transactional
    public void getAllPublicacionsByContentContainsSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where content contains DEFAULT_CONTENT
        defaultPublicacionShouldBeFound("content.contains=" + DEFAULT_CONTENT);

        // Get all the publicacionList where content contains UPDATED_CONTENT
        defaultPublicacionShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPublicacionsByContentNotContainsSomething() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList where content does not contain DEFAULT_CONTENT
        defaultPublicacionShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);

        // Get all the publicacionList where content does not contain UPDATED_CONTENT
        defaultPublicacionShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPublicacionShouldBeFound(String filter) throws Exception {
        restPublicacionMockMvc.perform(get("/api/publicacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));

        // Check, that the count call also returns 1
        restPublicacionMockMvc.perform(get("/api/publicacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPublicacionShouldNotBeFound(String filter) throws Exception {
        restPublicacionMockMvc.perform(get("/api/publicacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPublicacionMockMvc.perform(get("/api/publicacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPublicacion() throws Exception {
        // Get the publicacion
        restPublicacionMockMvc.perform(get("/api/publicacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
