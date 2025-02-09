package com.sbm.rcu.web.rest;

import com.sbm.rcu.repository.RestorationReservationRepository;
import com.sbm.rcu.service.RestorationReservationService;
import com.sbm.rcu.service.dto.RestorationReservationDTO;
import com.sbm.rcu.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sbm.rcu.domain.RestorationReservation}.
 */
@RestController
@RequestMapping("/api/restoration-reservations")
public class RestorationReservationResource {

    private static final Logger LOG = LoggerFactory.getLogger(RestorationReservationResource.class);

    private static final String ENTITY_NAME = "restorationReservation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RestorationReservationService restorationReservationService;

    private final RestorationReservationRepository restorationReservationRepository;

    public RestorationReservationResource(
        RestorationReservationService restorationReservationService,
        RestorationReservationRepository restorationReservationRepository
    ) {
        this.restorationReservationService = restorationReservationService;
        this.restorationReservationRepository = restorationReservationRepository;
    }

    /**
     * {@code POST  /restoration-reservations} : Create a new restorationReservation.
     *
     * @param restorationReservationDTO the restorationReservationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new restorationReservationDTO, or with status {@code 400 (Bad Request)} if the restorationReservation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RestorationReservationDTO> createRestorationReservation(
        @RequestBody RestorationReservationDTO restorationReservationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save RestorationReservation : {}", restorationReservationDTO);
        if (restorationReservationDTO.getId() != null) {
            throw new BadRequestAlertException("A new restorationReservation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        restorationReservationDTO = restorationReservationService.save(restorationReservationDTO);
        return ResponseEntity.created(new URI("/api/restoration-reservations/" + restorationReservationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, restorationReservationDTO.getId()))
            .body(restorationReservationDTO);
    }

    /**
     * {@code PUT  /restoration-reservations/:id} : Updates an existing restorationReservation.
     *
     * @param id the id of the restorationReservationDTO to save.
     * @param restorationReservationDTO the restorationReservationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restorationReservationDTO,
     * or with status {@code 400 (Bad Request)} if the restorationReservationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the restorationReservationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RestorationReservationDTO> updateRestorationReservation(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RestorationReservationDTO restorationReservationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update RestorationReservation : {}, {}", id, restorationReservationDTO);
        if (restorationReservationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, restorationReservationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!restorationReservationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        restorationReservationDTO = restorationReservationService.update(restorationReservationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restorationReservationDTO.getId()))
            .body(restorationReservationDTO);
    }

    /**
     * {@code PATCH  /restoration-reservations/:id} : Partial updates given fields of an existing restorationReservation, field will ignore if it is null
     *
     * @param id the id of the restorationReservationDTO to save.
     * @param restorationReservationDTO the restorationReservationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restorationReservationDTO,
     * or with status {@code 400 (Bad Request)} if the restorationReservationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the restorationReservationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the restorationReservationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RestorationReservationDTO> partialUpdateRestorationReservation(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RestorationReservationDTO restorationReservationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RestorationReservation partially : {}, {}", id, restorationReservationDTO);
        if (restorationReservationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, restorationReservationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!restorationReservationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RestorationReservationDTO> result = restorationReservationService.partialUpdate(restorationReservationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restorationReservationDTO.getId())
        );
    }

    /**
     * {@code GET  /restoration-reservations} : get all the restorationReservations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of restorationReservations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RestorationReservationDTO>> getAllRestorationReservations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of RestorationReservations");
        Page<RestorationReservationDTO> page = restorationReservationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /restoration-reservations/:id} : get the "id" restorationReservation.
     *
     * @param id the id of the restorationReservationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the restorationReservationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestorationReservationDTO> getRestorationReservation(@PathVariable("id") String id) {
        LOG.debug("REST request to get RestorationReservation : {}", id);
        Optional<RestorationReservationDTO> restorationReservationDTO = restorationReservationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restorationReservationDTO);
    }

    /**
     * {@code DELETE  /restoration-reservations/:id} : delete the "id" restorationReservation.
     *
     * @param id the id of the restorationReservationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestorationReservation(@PathVariable("id") String id) {
        LOG.debug("REST request to delete RestorationReservation : {}", id);
        restorationReservationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
