package com.sbm.rcu.web.rest;

import com.sbm.rcu.repository.GoldenRecordRepository;
import com.sbm.rcu.service.GoldenRecordService;
import com.sbm.rcu.service.dto.GoldenRecordDTO;
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
 * REST controller for managing {@link com.sbm.rcu.domain.GoldenRecord}.
 */
//@RestController
//@RequestMapping("/api/golden-records")
public class GoldenRecordResource {

    private static final Logger LOG = LoggerFactory.getLogger(GoldenRecordResource.class);

    private static final String ENTITY_NAME = "goldenRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoldenRecordService goldenRecordService;

    private final GoldenRecordRepository goldenRecordRepository;

    public GoldenRecordResource(GoldenRecordService goldenRecordService, GoldenRecordRepository goldenRecordRepository) {
        this.goldenRecordService = goldenRecordService;
        this.goldenRecordRepository = goldenRecordRepository;
    }

    /**
     * {@code POST  /golden-records} : Create a new goldenRecord.
     *
     * @param goldenRecordDTO the goldenRecordDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goldenRecordDTO, or with status {@code 400 (Bad Request)} if the goldenRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GoldenRecordDTO> createGoldenRecord(@RequestBody GoldenRecordDTO goldenRecordDTO) throws URISyntaxException {
        LOG.debug("REST request to save GoldenRecord : {}", goldenRecordDTO);
        if (goldenRecordDTO.getId() != null) {
            throw new BadRequestAlertException("A new goldenRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        goldenRecordDTO = goldenRecordService.save(goldenRecordDTO);
        return ResponseEntity.created(new URI("/api/golden-records/" + goldenRecordDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, goldenRecordDTO.getId()))
            .body(goldenRecordDTO);
    }

    /**
     * {@code PUT  /golden-records/:id} : Updates an existing goldenRecord.
     *
     * @param id the id of the goldenRecordDTO to save.
     * @param goldenRecordDTO the goldenRecordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goldenRecordDTO,
     * or with status {@code 400 (Bad Request)} if the goldenRecordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goldenRecordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GoldenRecordDTO> updateGoldenRecord(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody GoldenRecordDTO goldenRecordDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update GoldenRecord : {}, {}", id, goldenRecordDTO);
        if (goldenRecordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goldenRecordDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goldenRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        goldenRecordDTO = goldenRecordService.update(goldenRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goldenRecordDTO.getId()))
            .body(goldenRecordDTO);
    }

    /**
     * {@code PATCH  /golden-records/:id} : Partial updates given fields of an existing goldenRecord, field will ignore if it is null
     *
     * @param id the id of the goldenRecordDTO to save.
     * @param goldenRecordDTO the goldenRecordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goldenRecordDTO,
     * or with status {@code 400 (Bad Request)} if the goldenRecordDTO is not valid,
     * or with status {@code 404 (Not Found)} if the goldenRecordDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the goldenRecordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GoldenRecordDTO> partialUpdateGoldenRecord(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody GoldenRecordDTO goldenRecordDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update GoldenRecord partially : {}, {}", id, goldenRecordDTO);
        if (goldenRecordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goldenRecordDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goldenRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GoldenRecordDTO> result = goldenRecordService.partialUpdate(goldenRecordDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goldenRecordDTO.getId())
        );
    }

    /**
     * {@code GET  /golden-records} : get all the goldenRecords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goldenRecords in body.
     */
    @GetMapping("")
    public List<GoldenRecordDTO> getAllGoldenRecords() {
        LOG.debug("REST request to get all GoldenRecords");
        return goldenRecordService.findAll();
    }

    /**
     * {@code GET  /golden-records/:id} : get the "id" goldenRecord.
     *
     * @param id the id of the goldenRecordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goldenRecordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GoldenRecordDTO> getGoldenRecord(@PathVariable("id") String id) {
        LOG.debug("REST request to get GoldenRecord : {}", id);
        Optional<GoldenRecordDTO> goldenRecordDTO = goldenRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goldenRecordDTO);
    }

    /**
     * {@code DELETE  /golden-records/:id} : delete the "id" goldenRecord.
     *
     * @param id the id of the goldenRecordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoldenRecord(@PathVariable("id") String id) {
        LOG.debug("REST request to delete GoldenRecord : {}", id);
        goldenRecordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
