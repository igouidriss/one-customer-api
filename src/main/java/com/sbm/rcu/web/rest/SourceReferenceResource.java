package com.sbm.rcu.web.rest;

import com.sbm.rcu.repository.SourceReferenceRepository;
import com.sbm.rcu.service.SourceReferenceService;
import com.sbm.rcu.service.dto.SourceReferenceDTO;
import com.sbm.rcu.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sbm.rcu.domain.SourceReference}.
 */
@RestController
@RequestMapping("/api/source-references")
public class SourceReferenceResource {

    private static final Logger LOG = LoggerFactory.getLogger(SourceReferenceResource.class);

    private static final String ENTITY_NAME = "sourceReference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SourceReferenceService sourceReferenceService;

    private final SourceReferenceRepository sourceReferenceRepository;

    public SourceReferenceResource(SourceReferenceService sourceReferenceService, SourceReferenceRepository sourceReferenceRepository) {
        this.sourceReferenceService = sourceReferenceService;
        this.sourceReferenceRepository = sourceReferenceRepository;
    }

    /**
     * {@code POST  /source-references} : Create a new sourceReference.
     *
     * @param sourceReferenceDTO the sourceReferenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sourceReferenceDTO, or with status {@code 400 (Bad Request)} if the sourceReference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SourceReferenceDTO> createSourceReference(@RequestBody SourceReferenceDTO sourceReferenceDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SourceReference : {}", sourceReferenceDTO);
        if (sourceReferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new sourceReference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sourceReferenceDTO = sourceReferenceService.save(sourceReferenceDTO);
        return ResponseEntity.created(new URI("/api/source-references/" + sourceReferenceDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sourceReferenceDTO.getId()))
            .body(sourceReferenceDTO);
    }

    /**
     * {@code PUT  /source-references/:id} : Updates an existing sourceReference.
     *
     * @param id the id of the sourceReferenceDTO to save.
     * @param sourceReferenceDTO the sourceReferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sourceReferenceDTO,
     * or with status {@code 400 (Bad Request)} if the sourceReferenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sourceReferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SourceReferenceDTO> updateSourceReference(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SourceReferenceDTO sourceReferenceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SourceReference : {}, {}", id, sourceReferenceDTO);
        if (sourceReferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sourceReferenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sourceReferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sourceReferenceDTO = sourceReferenceService.update(sourceReferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sourceReferenceDTO.getId()))
            .body(sourceReferenceDTO);
    }

    /**
     * {@code PATCH  /source-references/:id} : Partial updates given fields of an existing sourceReference, field will ignore if it is null
     *
     * @param id the id of the sourceReferenceDTO to save.
     * @param sourceReferenceDTO the sourceReferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sourceReferenceDTO,
     * or with status {@code 400 (Bad Request)} if the sourceReferenceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sourceReferenceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sourceReferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SourceReferenceDTO> partialUpdateSourceReference(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SourceReferenceDTO sourceReferenceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SourceReference partially : {}, {}", id, sourceReferenceDTO);
        if (sourceReferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sourceReferenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sourceReferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SourceReferenceDTO> result = sourceReferenceService.partialUpdate(sourceReferenceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sourceReferenceDTO.getId())
        );
    }

    /**
     * {@code GET  /source-references} : get all the sourceReferences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sourceReferences in body.
     */
    @GetMapping("")
    public List<SourceReferenceDTO> getAllSourceReferences() {
        LOG.debug("REST request to get all SourceReferences");
        return sourceReferenceService.findAll();
    }

    /**
     * {@code GET  /source-references/:id} : get the "id" sourceReference.
     *
     * @param id the id of the sourceReferenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sourceReferenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SourceReferenceDTO> getSourceReference(@PathVariable("id") String id) {
        LOG.debug("REST request to get SourceReference : {}", id);
        Optional<SourceReferenceDTO> sourceReferenceDTO = sourceReferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sourceReferenceDTO);
    }

    /**
     * {@code DELETE  /source-references/:id} : delete the "id" sourceReference.
     *
     * @param id the id of the sourceReferenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSourceReference(@PathVariable("id") String id) {
        LOG.debug("REST request to delete SourceReference : {}", id);
        sourceReferenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
