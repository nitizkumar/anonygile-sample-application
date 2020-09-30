package com.anonygile.tool.web.rest;

import com.anonygile.tool.AnonygileApp;
import com.anonygile.tool.domain.SizeEstimate;
import com.anonygile.tool.repository.SizeEstimateRepository;

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
 * Integration tests for the {@link SizeEstimateResource} REST controller.
 */
@SpringBootTest(classes = AnonygileApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SizeEstimateResourceIT {

    private static final String DEFAULT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE = "BBBBBBBBBB";

    private static final Double DEFAULT_ESTIMATE = 1D;
    private static final Double UPDATED_ESTIMATE = 2D;

    @Autowired
    private SizeEstimateRepository sizeEstimateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSizeEstimateMockMvc;

    private SizeEstimate sizeEstimate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SizeEstimate createEntity(EntityManager em) {
        SizeEstimate sizeEstimate = new SizeEstimate()
            .size(DEFAULT_SIZE)
            .estimate(DEFAULT_ESTIMATE);
        return sizeEstimate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SizeEstimate createUpdatedEntity(EntityManager em) {
        SizeEstimate sizeEstimate = new SizeEstimate()
            .size(UPDATED_SIZE)
            .estimate(UPDATED_ESTIMATE);
        return sizeEstimate;
    }

    @BeforeEach
    public void initTest() {
        sizeEstimate = createEntity(em);
    }

    @Test
    @Transactional
    public void createSizeEstimate() throws Exception {
        int databaseSizeBeforeCreate = sizeEstimateRepository.findAll().size();
        // Create the SizeEstimate
        restSizeEstimateMockMvc.perform(post("/api/size-estimates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sizeEstimate)))
            .andExpect(status().isCreated());

        // Validate the SizeEstimate in the database
        List<SizeEstimate> sizeEstimateList = sizeEstimateRepository.findAll();
        assertThat(sizeEstimateList).hasSize(databaseSizeBeforeCreate + 1);
        SizeEstimate testSizeEstimate = sizeEstimateList.get(sizeEstimateList.size() - 1);
        assertThat(testSizeEstimate.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testSizeEstimate.getEstimate()).isEqualTo(DEFAULT_ESTIMATE);
    }

    @Test
    @Transactional
    public void createSizeEstimateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sizeEstimateRepository.findAll().size();

        // Create the SizeEstimate with an existing ID
        sizeEstimate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSizeEstimateMockMvc.perform(post("/api/size-estimates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sizeEstimate)))
            .andExpect(status().isBadRequest());

        // Validate the SizeEstimate in the database
        List<SizeEstimate> sizeEstimateList = sizeEstimateRepository.findAll();
        assertThat(sizeEstimateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSizeEstimates() throws Exception {
        // Initialize the database
        sizeEstimateRepository.saveAndFlush(sizeEstimate);

        // Get all the sizeEstimateList
        restSizeEstimateMockMvc.perform(get("/api/size-estimates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sizeEstimate.getId().intValue())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].estimate").value(hasItem(DEFAULT_ESTIMATE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSizeEstimate() throws Exception {
        // Initialize the database
        sizeEstimateRepository.saveAndFlush(sizeEstimate);

        // Get the sizeEstimate
        restSizeEstimateMockMvc.perform(get("/api/size-estimates/{id}", sizeEstimate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sizeEstimate.getId().intValue()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.estimate").value(DEFAULT_ESTIMATE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSizeEstimate() throws Exception {
        // Get the sizeEstimate
        restSizeEstimateMockMvc.perform(get("/api/size-estimates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSizeEstimate() throws Exception {
        // Initialize the database
        sizeEstimateRepository.saveAndFlush(sizeEstimate);

        int databaseSizeBeforeUpdate = sizeEstimateRepository.findAll().size();

        // Update the sizeEstimate
        SizeEstimate updatedSizeEstimate = sizeEstimateRepository.findById(sizeEstimate.getId()).get();
        // Disconnect from session so that the updates on updatedSizeEstimate are not directly saved in db
        em.detach(updatedSizeEstimate);
        updatedSizeEstimate
            .size(UPDATED_SIZE)
            .estimate(UPDATED_ESTIMATE);

        restSizeEstimateMockMvc.perform(put("/api/size-estimates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSizeEstimate)))
            .andExpect(status().isOk());

        // Validate the SizeEstimate in the database
        List<SizeEstimate> sizeEstimateList = sizeEstimateRepository.findAll();
        assertThat(sizeEstimateList).hasSize(databaseSizeBeforeUpdate);
        SizeEstimate testSizeEstimate = sizeEstimateList.get(sizeEstimateList.size() - 1);
        assertThat(testSizeEstimate.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testSizeEstimate.getEstimate()).isEqualTo(UPDATED_ESTIMATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSizeEstimate() throws Exception {
        int databaseSizeBeforeUpdate = sizeEstimateRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSizeEstimateMockMvc.perform(put("/api/size-estimates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sizeEstimate)))
            .andExpect(status().isBadRequest());

        // Validate the SizeEstimate in the database
        List<SizeEstimate> sizeEstimateList = sizeEstimateRepository.findAll();
        assertThat(sizeEstimateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSizeEstimate() throws Exception {
        // Initialize the database
        sizeEstimateRepository.saveAndFlush(sizeEstimate);

        int databaseSizeBeforeDelete = sizeEstimateRepository.findAll().size();

        // Delete the sizeEstimate
        restSizeEstimateMockMvc.perform(delete("/api/size-estimates/{id}", sizeEstimate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SizeEstimate> sizeEstimateList = sizeEstimateRepository.findAll();
        assertThat(sizeEstimateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
