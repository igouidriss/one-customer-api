package com.sbm.rcu.web.rest;

import com.sbm.rcu.repository.HotelReservationRepository;
import com.sbm.rcu.service.HotelReservationService;
import com.sbm.rcu.service.dto.HotelReservationDTO;
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
 * REST controller for managing {@link com.sbm.rcu.domain.HotelReservation}.
 */
//@RestController
//@RequestMapping("/api/hotel-reservations")
public class HotelReservationResource {

    private static final Logger LOG = LoggerFactory.getLogger(HotelReservationResource.class);

    private static final String ENTITY_NAME = "hotelReservation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelReservationService hotelReservationService;

    private final HotelReservationRepository hotelReservationRepository;

    public HotelReservationResource(
        HotelReservationService hotelReservationService,
        HotelReservationRepository hotelReservationRepository
    ) {
        this.hotelReservationService = hotelReservationService;
        this.hotelReservationRepository = hotelReservationRepository;
    }

    /**
     * {@code POST  /hotel-reservations} : Create a new hotelReservation.
     *
     * @param hotelReservationDTO the hotelReservationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotelReservationDTO, or with status {@code 400 (Bad Request)} if the hotelReservation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HotelReservationDTO> createHotelReservation(@RequestBody HotelReservationDTO hotelReservationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save HotelReservation : {}", hotelReservationDTO);
        if (hotelReservationDTO.getId() != null) {
            throw new BadRequestAlertException("A new hotelReservation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hotelReservationDTO = hotelReservationService.save(hotelReservationDTO);
        return ResponseEntity.created(new URI("/api/hotel-reservations/" + hotelReservationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hotelReservationDTO.getId()))
            .body(hotelReservationDTO);
    }

    /**
     * {@code PUT  /hotel-reservations/:id} : Updates an existing hotelReservation.
     *
     * @param id the id of the hotelReservationDTO to save.
     * @param hotelReservationDTO the hotelReservationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelReservationDTO,
     * or with status {@code 400 (Bad Request)} if the hotelReservationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hotelReservationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HotelReservationDTO> updateHotelReservation(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody HotelReservationDTO hotelReservationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update HotelReservation : {}, {}", id, hotelReservationDTO);
        if (hotelReservationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelReservationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelReservationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hotelReservationDTO = hotelReservationService.update(hotelReservationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelReservationDTO.getId()))
            .body(hotelReservationDTO);
    }

    /**
     * {@code PATCH  /hotel-reservations/:id} : Partial updates given fields of an existing hotelReservation, field will ignore if it is null
     *
     * @param id the id of the hotelReservationDTO to save.
     * @param hotelReservationDTO the hotelReservationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelReservationDTO,
     * or with status {@code 400 (Bad Request)} if the hotelReservationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hotelReservationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hotelReservationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HotelReservationDTO> partialUpdateHotelReservation(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody HotelReservationDTO hotelReservationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update HotelReservation partially : {}, {}", id, hotelReservationDTO);
        if (hotelReservationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelReservationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelReservationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HotelReservationDTO> result = hotelReservationService.partialUpdate(hotelReservationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelReservationDTO.getId())
        );
    }

    /**
     * {@code GET  /hotel-reservations} : get all the hotelReservations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hotelReservations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HotelReservationDTO>> getAllHotelReservations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of HotelReservations");
        Page<HotelReservationDTO> page = hotelReservationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hotel-reservations/:id} : get the "id" hotelReservation.
     *
     * @param id the id of the hotelReservationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hotelReservationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HotelReservationDTO> getHotelReservation(@PathVariable("id") String id) {
        LOG.debug("REST request to get HotelReservation : {}", id);
        Optional<HotelReservationDTO> hotelReservationDTO = hotelReservationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hotelReservationDTO);
    }

    /**
     * {@code DELETE  /hotel-reservations/:id} : delete the "id" hotelReservation.
     *
     * @param id the id of the hotelReservationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotelReservation(@PathVariable("id") String id) {
        LOG.debug("REST request to delete HotelReservation : {}", id);
        hotelReservationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
