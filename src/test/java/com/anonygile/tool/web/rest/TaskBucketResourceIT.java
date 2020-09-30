package com.anonygile.tool.web.rest;

import com.anonygile.tool.AnonygileApp;
import com.anonygile.tool.domain.TaskBucket;
import com.anonygile.tool.repository.TaskBucketRepository;

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

import com.anonygile.tool.domain.enumeration.TaskStatus;
/**
 * Integration tests for the {@link TaskBucketResource} REST controller.
 */
@SpringBootTest(classes = AnonygileApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskBucketResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final TaskStatus DEFAULT_STATUS = TaskStatus.PLANNED;
    private static final TaskStatus UPDATED_STATUS = TaskStatus.IN_PROGRESS;

    @Autowired
    private TaskBucketRepository taskBucketRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskBucketMockMvc;

    private TaskBucket taskBucket;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskBucket createEntity(EntityManager em) {
        TaskBucket taskBucket = new TaskBucket()
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS);
        return taskBucket;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskBucket createUpdatedEntity(EntityManager em) {
        TaskBucket taskBucket = new TaskBucket()
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);
        return taskBucket;
    }

    @BeforeEach
    public void initTest() {
        taskBucket = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskBucket() throws Exception {
        int databaseSizeBeforeCreate = taskBucketRepository.findAll().size();
        // Create the TaskBucket
        restTaskBucketMockMvc.perform(post("/api/task-buckets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskBucket)))
            .andExpect(status().isCreated());

        // Validate the TaskBucket in the database
        List<TaskBucket> taskBucketList = taskBucketRepository.findAll();
        assertThat(taskBucketList).hasSize(databaseSizeBeforeCreate + 1);
        TaskBucket testTaskBucket = taskBucketList.get(taskBucketList.size() - 1);
        assertThat(testTaskBucket.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskBucket.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createTaskBucketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskBucketRepository.findAll().size();

        // Create the TaskBucket with an existing ID
        taskBucket.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskBucketMockMvc.perform(post("/api/task-buckets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskBucket)))
            .andExpect(status().isBadRequest());

        // Validate the TaskBucket in the database
        List<TaskBucket> taskBucketList = taskBucketRepository.findAll();
        assertThat(taskBucketList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTaskBuckets() throws Exception {
        // Initialize the database
        taskBucketRepository.saveAndFlush(taskBucket);

        // Get all the taskBucketList
        restTaskBucketMockMvc.perform(get("/api/task-buckets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskBucket.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getTaskBucket() throws Exception {
        // Initialize the database
        taskBucketRepository.saveAndFlush(taskBucket);

        // Get the taskBucket
        restTaskBucketMockMvc.perform(get("/api/task-buckets/{id}", taskBucket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskBucket.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTaskBucket() throws Exception {
        // Get the taskBucket
        restTaskBucketMockMvc.perform(get("/api/task-buckets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskBucket() throws Exception {
        // Initialize the database
        taskBucketRepository.saveAndFlush(taskBucket);

        int databaseSizeBeforeUpdate = taskBucketRepository.findAll().size();

        // Update the taskBucket
        TaskBucket updatedTaskBucket = taskBucketRepository.findById(taskBucket.getId()).get();
        // Disconnect from session so that the updates on updatedTaskBucket are not directly saved in db
        em.detach(updatedTaskBucket);
        updatedTaskBucket
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);

        restTaskBucketMockMvc.perform(put("/api/task-buckets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaskBucket)))
            .andExpect(status().isOk());

        // Validate the TaskBucket in the database
        List<TaskBucket> taskBucketList = taskBucketRepository.findAll();
        assertThat(taskBucketList).hasSize(databaseSizeBeforeUpdate);
        TaskBucket testTaskBucket = taskBucketList.get(taskBucketList.size() - 1);
        assertThat(testTaskBucket.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskBucket.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskBucket() throws Exception {
        int databaseSizeBeforeUpdate = taskBucketRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskBucketMockMvc.perform(put("/api/task-buckets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskBucket)))
            .andExpect(status().isBadRequest());

        // Validate the TaskBucket in the database
        List<TaskBucket> taskBucketList = taskBucketRepository.findAll();
        assertThat(taskBucketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaskBucket() throws Exception {
        // Initialize the database
        taskBucketRepository.saveAndFlush(taskBucket);

        int databaseSizeBeforeDelete = taskBucketRepository.findAll().size();

        // Delete the taskBucket
        restTaskBucketMockMvc.perform(delete("/api/task-buckets/{id}", taskBucket.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskBucket> taskBucketList = taskBucketRepository.findAll();
        assertThat(taskBucketList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
