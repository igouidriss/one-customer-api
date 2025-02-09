package com.sbm.rcu.web.rest;

import com.sbm.rcu.repository.PayloadRepository;
import com.sbm.rcu.service.PayloadService;
import com.sbm.rcu.service.dto.PayloadDTO;
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
 * REST controller for managing {@link com.sbm.rcu.domain.Payload}.
 */
@RestController
@RequestMapping("/api/payloads")
public class PayloadResource {

    private static final Logger LOG = LoggerFactory.getLogger(PayloadResource.class);

    private static final String ENTITY_NAME = "payload";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayloadService payloadService;

    private final PayloadRepository payloadRepository;

    public PayloadResource(PayloadService payloadService, PayloadRepository payloadRepository) {
        this.payloadService = payloadService;
        this.payloadRepository = payloadRepository;
    }

    /**
     * {@code POST  /payloads} : Create a new payload.
     *
     * @param payloadDTO the payloadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payloadDTO, or with status {@code 400 (Bad Request)} if the payload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PayloadDTO> createPayload(@RequestBody PayloadDTO payloadDTO) throws URISyntaxException {
        LOG.debug("REST request to save Payload : {}", payloadDTO);
        if (payloadDTO.getId() != null) {
            throw new BadRequestAlertException("A new payload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        payloadDTO = payloadService.save(payloadDTO);
        return ResponseEntity.created(new URI("/api/payloads/" + payloadDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, payloadDTO.getId()))
            .body(payloadDTO);
    }

    /**
     * {@code PUT  /payloads/:id} : Updates an existing payload.
     *
     * @param id the id of the payloadDTO to save.
     * @param payloadDTO the payloadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payloadDTO,
     * or with status {@code 400 (Bad Request)} if the payloadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payloadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PayloadDTO> updatePayload(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PayloadDTO payloadDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Payload : {}, {}", id, payloadDTO);
        if (payloadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payloadDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payloadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        payloadDTO = payloadService.update(payloadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payloadDTO.getId()))
            .body(payloadDTO);
    }

    /**
     * {@code PATCH  /payloads/:id} : Partial updates given fields of an existing payload, field will ignore if it is null
     *
     * @param id the id of the payloadDTO to save.
     * @param payloadDTO the payloadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payloadDTO,
     * or with status {@code 400 (Bad Request)} if the payloadDTO is not valid,
     * or with status {@code 404 (Not Found)} if the payloadDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the payloadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PayloadDTO> partialUpdatePayload(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PayloadDTO payloadDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Payload partially : {}, {}", id, payloadDTO);
        if (payloadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payloadDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payloadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PayloadDTO> result = payloadService.partialUpdate(payloadDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payloadDTO.getId())
        );
    }

    /**
     * {@code GET  /payloads} : get all the payloads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payloads in body.
     */
    @GetMapping("")
    public List<PayloadDTO> getAllPayloads() {
        LOG.debug("REST request to get all Payloads");
        return payloadService.findAll();
    }

    /**
     * {@code GET  /payloads/:id} : get the "id" payload.
     *
     * @param id the id of the payloadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payloadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PayloadDTO> getPayload(@PathVariable("id") String id) {
        LOG.debug("REST request to get Payload : {}", id);
        Optional<PayloadDTO> payloadDTO = payloadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payloadDTO);
    }

    /**
     * {@code DELETE  /payloads/:id} : delete the "id" payload.
     *
     * @param id the id of the payloadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayload(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Payload : {}", id);
        payloadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
