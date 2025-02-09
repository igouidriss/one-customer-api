package com.sbm.rcu.web.rest;

import com.sbm.rcu.repository.MetadataRepository;
import com.sbm.rcu.service.MetadataService;
import com.sbm.rcu.service.dto.MetadataDTO;
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
 * REST controller for managing {@link com.sbm.rcu.domain.Metadata}.
 */
@RestController
@RequestMapping("/api/metadata")
public class MetadataResource {

    private static final Logger LOG = LoggerFactory.getLogger(MetadataResource.class);

    private static final String ENTITY_NAME = "metadata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetadataService metadataService;

    private final MetadataRepository metadataRepository;

    public MetadataResource(MetadataService metadataService, MetadataRepository metadataRepository) {
        this.metadataService = metadataService;
        this.metadataRepository = metadataRepository;
    }

    /**
     * {@code POST  /metadata} : Create a new metadata.
     *
     * @param metadataDTO the metadataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metadataDTO, or with status {@code 400 (Bad Request)} if the metadata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MetadataDTO> createMetadata(@RequestBody MetadataDTO metadataDTO) throws URISyntaxException {
        LOG.debug("REST request to save Metadata : {}", metadataDTO);
        if (metadataDTO.getId() != null) {
            throw new BadRequestAlertException("A new metadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        metadataDTO = metadataService.save(metadataDTO);
        return ResponseEntity.created(new URI("/api/metadata/" + metadataDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, metadataDTO.getId()))
            .body(metadataDTO);
    }

    /**
     * {@code PUT  /metadata/:id} : Updates an existing metadata.
     *
     * @param id the id of the metadataDTO to save.
     * @param metadataDTO the metadataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metadataDTO,
     * or with status {@code 400 (Bad Request)} if the metadataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metadataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MetadataDTO> updateMetadata(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody MetadataDTO metadataDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Metadata : {}, {}", id, metadataDTO);
        if (metadataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metadataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        metadataDTO = metadataService.update(metadataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, metadataDTO.getId()))
            .body(metadataDTO);
    }

    /**
     * {@code PATCH  /metadata/:id} : Partial updates given fields of an existing metadata, field will ignore if it is null
     *
     * @param id the id of the metadataDTO to save.
     * @param metadataDTO the metadataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metadataDTO,
     * or with status {@code 400 (Bad Request)} if the metadataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metadataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metadataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MetadataDTO> partialUpdateMetadata(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody MetadataDTO metadataDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Metadata partially : {}, {}", id, metadataDTO);
        if (metadataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metadataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetadataDTO> result = metadataService.partialUpdate(metadataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, metadataDTO.getId())
        );
    }

    /**
     * {@code GET  /metadata} : get all the metadata.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metadata in body.
     */
    @GetMapping("")
    public List<MetadataDTO> getAllMetadata() {
        LOG.debug("REST request to get all Metadata");
        return metadataService.findAll();
    }

    /**
     * {@code GET  /metadata/:id} : get the "id" metadata.
     *
     * @param id the id of the metadataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metadataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MetadataDTO> getMetadata(@PathVariable("id") String id) {
        LOG.debug("REST request to get Metadata : {}", id);
        Optional<MetadataDTO> metadataDTO = metadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metadataDTO);
    }

    /**
     * {@code DELETE  /metadata/:id} : delete the "id" metadata.
     *
     * @param id the id of the metadataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetadata(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Metadata : {}", id);
        metadataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
