package com.llaparra.react.web.rest;

import com.llaparra.react.ReactApp;
import com.llaparra.react.domain.Notificacion;
import com.llaparra.react.repository.NotificacionRepository;
import com.llaparra.react.service.NotificacionService;
import com.llaparra.react.service.dto.NotificacionDTO;
import com.llaparra.react.service.mapper.NotificacionMapper;
import com.llaparra.react.service.dto.NotificacionCriteria;
import com.llaparra.react.service.NotificacionQueryService;

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
 * Integration tests for the {@link NotificacionResource} REST controller.
 */
@SpringBootTest(classes = ReactApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NotificacionResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_IS_READ = false;
    private static final Boolean UPDATED_IS_READ = true;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY = "BBBBBBBBBB";

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private NotificacionMapper notificacionMapper;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionQueryService notificacionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificacionMockMvc;

    private Notificacion notificacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notificacion createEntity(EntityManager em) {
        Notificacion notificacion = new Notificacion()
            .date(DEFAULT_DATE)
            .isRead(DEFAULT_IS_READ)
            .title(DEFAULT_TITLE)
            .summary(DEFAULT_SUMMARY);
        return notificacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notificacion createUpdatedEntity(EntityManager em) {
        Notificacion notificacion = new Notificacion()
            .date(UPDATED_DATE)
            .isRead(UPDATED_IS_READ)
            .title(UPDATED_TITLE)
            .summary(UPDATED_SUMMARY);
        return notificacion;
    }

    @BeforeEach
    public void initTest() {
        notificacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificacion() throws Exception {
        int databaseSizeBeforeCreate = notificacionRepository.findAll().size();
        // Create the Notificacion
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);
        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeCreate + 1);
        Notificacion testNotificacion = notificacionList.get(notificacionList.size() - 1);
        assertThat(testNotificacion.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNotificacion.isIsRead()).isEqualTo(DEFAULT_IS_READ);
        assertThat(testNotificacion.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNotificacion.getSummary()).isEqualTo(DEFAULT_SUMMARY);
    }

    @Test
    @Transactional
    public void createNotificacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificacionRepository.findAll().size();

        // Create the Notificacion with an existing ID
        notificacion.setId(1L);
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionRepository.findAll().size();
        // set the field null
        notificacion.setDate(null);

        // Create the Notificacion, which fails.
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);


        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacionDTO)))
            .andExpect(status().isBadRequest());

        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionRepository.findAll().size();
        // set the field null
        notificacion.setTitle(null);

        // Create the Notificacion, which fails.
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);


        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacionDTO)))
            .andExpect(status().isBadRequest());

        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSummaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionRepository.findAll().size();
        // set the field null
        notificacion.setSummary(null);

        // Create the Notificacion, which fails.
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);


        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacionDTO)))
            .andExpect(status().isBadRequest());

        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotificacions() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList
        restNotificacionMockMvc.perform(get("/api/notificacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isRead").value(hasItem(DEFAULT_IS_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY)));
    }
    
    @Test
    @Transactional
    public void getNotificacion() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get the notificacion
        restNotificacionMockMvc.perform(get("/api/notificacions/{id}", notificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificacion.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.isRead").value(DEFAULT_IS_READ.booleanValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY));
    }


    @Test
    @Transactional
    public void getNotificacionsByIdFiltering() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        Long id = notificacion.getId();

        defaultNotificacionShouldBeFound("id.equals=" + id);
        defaultNotificacionShouldNotBeFound("id.notEquals=" + id);

        defaultNotificacionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificacionShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificacionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificacionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNotificacionsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where date equals to DEFAULT_DATE
        defaultNotificacionShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the notificacionList where date equals to UPDATED_DATE
        defaultNotificacionShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where date not equals to DEFAULT_DATE
        defaultNotificacionShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the notificacionList where date not equals to UPDATED_DATE
        defaultNotificacionShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where date in DEFAULT_DATE or UPDATED_DATE
        defaultNotificacionShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the notificacionList where date equals to UPDATED_DATE
        defaultNotificacionShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where date is not null
        defaultNotificacionShouldBeFound("date.specified=true");

        // Get all the notificacionList where date is null
        defaultNotificacionShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificacionsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where date is greater than or equal to DEFAULT_DATE
        defaultNotificacionShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the notificacionList where date is greater than or equal to UPDATED_DATE
        defaultNotificacionShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where date is less than or equal to DEFAULT_DATE
        defaultNotificacionShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the notificacionList where date is less than or equal to SMALLER_DATE
        defaultNotificacionShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where date is less than DEFAULT_DATE
        defaultNotificacionShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the notificacionList where date is less than UPDATED_DATE
        defaultNotificacionShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where date is greater than DEFAULT_DATE
        defaultNotificacionShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the notificacionList where date is greater than SMALLER_DATE
        defaultNotificacionShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllNotificacionsByIsReadIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where isRead equals to DEFAULT_IS_READ
        defaultNotificacionShouldBeFound("isRead.equals=" + DEFAULT_IS_READ);

        // Get all the notificacionList where isRead equals to UPDATED_IS_READ
        defaultNotificacionShouldNotBeFound("isRead.equals=" + UPDATED_IS_READ);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByIsReadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where isRead not equals to DEFAULT_IS_READ
        defaultNotificacionShouldNotBeFound("isRead.notEquals=" + DEFAULT_IS_READ);

        // Get all the notificacionList where isRead not equals to UPDATED_IS_READ
        defaultNotificacionShouldBeFound("isRead.notEquals=" + UPDATED_IS_READ);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByIsReadIsInShouldWork() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where isRead in DEFAULT_IS_READ or UPDATED_IS_READ
        defaultNotificacionShouldBeFound("isRead.in=" + DEFAULT_IS_READ + "," + UPDATED_IS_READ);

        // Get all the notificacionList where isRead equals to UPDATED_IS_READ
        defaultNotificacionShouldNotBeFound("isRead.in=" + UPDATED_IS_READ);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByIsReadIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where isRead is not null
        defaultNotificacionShouldBeFound("isRead.specified=true");

        // Get all the notificacionList where isRead is null
        defaultNotificacionShouldNotBeFound("isRead.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificacionsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where title equals to DEFAULT_TITLE
        defaultNotificacionShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the notificacionList where title equals to UPDATED_TITLE
        defaultNotificacionShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where title not equals to DEFAULT_TITLE
        defaultNotificacionShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the notificacionList where title not equals to UPDATED_TITLE
        defaultNotificacionShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultNotificacionShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the notificacionList where title equals to UPDATED_TITLE
        defaultNotificacionShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where title is not null
        defaultNotificacionShouldBeFound("title.specified=true");

        // Get all the notificacionList where title is null
        defaultNotificacionShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificacionsByTitleContainsSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where title contains DEFAULT_TITLE
        defaultNotificacionShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the notificacionList where title contains UPDATED_TITLE
        defaultNotificacionShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificacionsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where title does not contain DEFAULT_TITLE
        defaultNotificacionShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the notificacionList where title does not contain UPDATED_TITLE
        defaultNotificacionShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllNotificacionsBySummaryIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where summary equals to DEFAULT_SUMMARY
        defaultNotificacionShouldBeFound("summary.equals=" + DEFAULT_SUMMARY);

        // Get all the notificacionList where summary equals to UPDATED_SUMMARY
        defaultNotificacionShouldNotBeFound("summary.equals=" + UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllNotificacionsBySummaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where summary not equals to DEFAULT_SUMMARY
        defaultNotificacionShouldNotBeFound("summary.notEquals=" + DEFAULT_SUMMARY);

        // Get all the notificacionList where summary not equals to UPDATED_SUMMARY
        defaultNotificacionShouldBeFound("summary.notEquals=" + UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllNotificacionsBySummaryIsInShouldWork() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where summary in DEFAULT_SUMMARY or UPDATED_SUMMARY
        defaultNotificacionShouldBeFound("summary.in=" + DEFAULT_SUMMARY + "," + UPDATED_SUMMARY);

        // Get all the notificacionList where summary equals to UPDATED_SUMMARY
        defaultNotificacionShouldNotBeFound("summary.in=" + UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllNotificacionsBySummaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where summary is not null
        defaultNotificacionShouldBeFound("summary.specified=true");

        // Get all the notificacionList where summary is null
        defaultNotificacionShouldNotBeFound("summary.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificacionsBySummaryContainsSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where summary contains DEFAULT_SUMMARY
        defaultNotificacionShouldBeFound("summary.contains=" + DEFAULT_SUMMARY);

        // Get all the notificacionList where summary contains UPDATED_SUMMARY
        defaultNotificacionShouldNotBeFound("summary.contains=" + UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllNotificacionsBySummaryNotContainsSomething() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList where summary does not contain DEFAULT_SUMMARY
        defaultNotificacionShouldNotBeFound("summary.doesNotContain=" + DEFAULT_SUMMARY);

        // Get all the notificacionList where summary does not contain UPDATED_SUMMARY
        defaultNotificacionShouldBeFound("summary.doesNotContain=" + UPDATED_SUMMARY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificacionShouldBeFound(String filter) throws Exception {
        restNotificacionMockMvc.perform(get("/api/notificacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isRead").value(hasItem(DEFAULT_IS_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY)));

        // Check, that the count call also returns 1
        restNotificacionMockMvc.perform(get("/api/notificacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificacionShouldNotBeFound(String filter) throws Exception {
        restNotificacionMockMvc.perform(get("/api/notificacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificacionMockMvc.perform(get("/api/notificacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNotificacion() throws Exception {
        // Get the notificacion
        restNotificacionMockMvc.perform(get("/api/notificacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificacion() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        int databaseSizeBeforeUpdate = notificacionRepository.findAll().size();

        // Update the notificacion
        Notificacion updatedNotificacion = notificacionRepository.findById(notificacion.getId()).get();
        // Disconnect from session so that the updates on updatedNotificacion are not directly saved in db
        em.detach(updatedNotificacion);
        updatedNotificacion
            .date(UPDATED_DATE)
            .isRead(UPDATED_IS_READ)
            .title(UPDATED_TITLE)
            .summary(UPDATED_SUMMARY);
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(updatedNotificacion);

        restNotificacionMockMvc.perform(put("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacionDTO)))
            .andExpect(status().isOk());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeUpdate);
        Notificacion testNotificacion = notificacionList.get(notificacionList.size() - 1);
        assertThat(testNotificacion.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNotificacion.isIsRead()).isEqualTo(UPDATED_IS_READ);
        assertThat(testNotificacion.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNotificacion.getSummary()).isEqualTo(UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificacion() throws Exception {
        int databaseSizeBeforeUpdate = notificacionRepository.findAll().size();

        // Create the Notificacion
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificacionMockMvc.perform(put("/api/notificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotificacion() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        int databaseSizeBeforeDelete = notificacionRepository.findAll().size();

        // Delete the notificacion
        restNotificacionMockMvc.perform(delete("/api/notificacions/{id}", notificacion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
