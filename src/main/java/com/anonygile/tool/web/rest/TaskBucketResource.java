package com.anonygile.tool.web.rest;

import com.anonygile.tool.domain.TaskBucket;
import com.anonygile.tool.repository.TaskBucketRepository;
import com.anonygile.tool.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.anonygile.tool.domain.TaskBucket}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaskBucketResource {

    private final Logger log = LoggerFactory.getLogger(TaskBucketResource.class);

    private static final String ENTITY_NAME = "taskBucket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskBucketRepository taskBucketRepository;

    public TaskBucketResource(TaskBucketRepository taskBucketRepository) {
        this.taskBucketRepository = taskBucketRepository;
    }

    /**
     * {@code POST  /task-buckets} : Create a new taskBucket.
     *
     * @param taskBucket the taskBucket to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskBucket, or with status {@code 400 (Bad Request)} if the taskBucket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-buckets")
    public ResponseEntity<TaskBucket> createTaskBucket(@RequestBody TaskBucket taskBucket) throws URISyntaxException {
        log.debug("REST request to save TaskBucket : {}", taskBucket);
        if (taskBucket.getId() != null) {
            throw new BadRequestAlertException("A new taskBucket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskBucket result = taskBucketRepository.save(taskBucket);
        return ResponseEntity.created(new URI("/api/task-buckets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-buckets} : Updates an existing taskBucket.
     *
     * @param taskBucket the taskBucket to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskBucket,
     * or with status {@code 400 (Bad Request)} if the taskBucket is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskBucket couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-buckets")
    public ResponseEntity<TaskBucket> updateTaskBucket(@RequestBody TaskBucket taskBucket) throws URISyntaxException {
        log.debug("REST request to update TaskBucket : {}", taskBucket);
        if (taskBucket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskBucket result = taskBucketRepository.save(taskBucket);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskBucket.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-buckets} : get all the taskBuckets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskBuckets in body.
     */
    @GetMapping("/task-buckets")
    public List<TaskBucket> getAllTaskBuckets() {
        log.debug("REST request to get all TaskBuckets");
        return taskBucketRepository.findAll();
    }

    /**
     * {@code GET  /task-buckets/:id} : get the "id" taskBucket.
     *
     * @param id the id of the taskBucket to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskBucket, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-buckets/{id}")
    public ResponseEntity<TaskBucket> getTaskBucket(@PathVariable Long id) {
        log.debug("REST request to get TaskBucket : {}", id);
        Optional<TaskBucket> taskBucket = taskBucketRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taskBucket);
    }

    /**
     * {@code DELETE  /task-buckets/:id} : delete the "id" taskBucket.
     *
     * @param id the id of the taskBucket to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-buckets/{id}")
    public ResponseEntity<Void> deleteTaskBucket(@PathVariable Long id) {
        log.debug("REST request to delete TaskBucket : {}", id);
        taskBucketRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
