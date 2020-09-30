package com.anonygile.tool.web.rest;

import com.anonygile.tool.domain.SizeEstimate;
import com.anonygile.tool.repository.SizeEstimateRepository;
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
 * REST controller for managing {@link com.anonygile.tool.domain.SizeEstimate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SizeEstimateResource {

    private final Logger log = LoggerFactory.getLogger(SizeEstimateResource.class);

    private static final String ENTITY_NAME = "sizeEstimate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SizeEstimateRepository sizeEstimateRepository;

    public SizeEstimateResource(SizeEstimateRepository sizeEstimateRepository) {
        this.sizeEstimateRepository = sizeEstimateRepository;
    }

    /**
     * {@code POST  /size-estimates} : Create a new sizeEstimate.
     *
     * @param sizeEstimate the sizeEstimate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sizeEstimate, or with status {@code 400 (Bad Request)} if the sizeEstimate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/size-estimates")
    public ResponseEntity<SizeEstimate> createSizeEstimate(@RequestBody SizeEstimate sizeEstimate) throws URISyntaxException {
        log.debug("REST request to save SizeEstimate : {}", sizeEstimate);
        if (sizeEstimate.getId() != null) {
            throw new BadRequestAlertException("A new sizeEstimate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SizeEstimate result = sizeEstimateRepository.save(sizeEstimate);
        return ResponseEntity.created(new URI("/api/size-estimates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /size-estimates} : Updates an existing sizeEstimate.
     *
     * @param sizeEstimate the sizeEstimate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sizeEstimate,
     * or with status {@code 400 (Bad Request)} if the sizeEstimate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sizeEstimate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/size-estimates")
    public ResponseEntity<SizeEstimate> updateSizeEstimate(@RequestBody SizeEstimate sizeEstimate) throws URISyntaxException {
        log.debug("REST request to update SizeEstimate : {}", sizeEstimate);
        if (sizeEstimate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SizeEstimate result = sizeEstimateRepository.save(sizeEstimate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sizeEstimate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /size-estimates} : get all the sizeEstimates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sizeEstimates in body.
     */
    @GetMapping("/size-estimates")
    public List<SizeEstimate> getAllSizeEstimates() {
        log.debug("REST request to get all SizeEstimates");
        return sizeEstimateRepository.findAll();
    }

    /**
     * {@code GET  /size-estimates/:id} : get the "id" sizeEstimate.
     *
     * @param id the id of the sizeEstimate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sizeEstimate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/size-estimates/{id}")
    public ResponseEntity<SizeEstimate> getSizeEstimate(@PathVariable Long id) {
        log.debug("REST request to get SizeEstimate : {}", id);
        Optional<SizeEstimate> sizeEstimate = sizeEstimateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sizeEstimate);
    }

    /**
     * {@code DELETE  /size-estimates/:id} : delete the "id" sizeEstimate.
     *
     * @param id the id of the sizeEstimate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/size-estimates/{id}")
    public ResponseEntity<Void> deleteSizeEstimate(@PathVariable Long id) {
        log.debug("REST request to delete SizeEstimate : {}", id);
        sizeEstimateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
