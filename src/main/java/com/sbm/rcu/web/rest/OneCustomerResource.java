package com.sbm.rcu.web.rest;

import com.sbm.rcu.repository.OneCustomerRepository;
import com.sbm.rcu.service.OneCustomerService;
import com.sbm.rcu.service.dto.OneCustomerDTO;
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
 * REST controller for managing {@link com.sbm.rcu.domain.OneCustomer}.
 */
@RestController
@RequestMapping("/api/one-customers")
public class OneCustomerResource {

    private static final Logger LOG = LoggerFactory.getLogger(OneCustomerResource.class);

    private static final String ENTITY_NAME = "oneCustomer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OneCustomerService oneCustomerService;

    private final OneCustomerRepository oneCustomerRepository;

    public OneCustomerResource(OneCustomerService oneCustomerService, OneCustomerRepository oneCustomerRepository) {
        this.oneCustomerService = oneCustomerService;
        this.oneCustomerRepository = oneCustomerRepository;
    }

    /**
     * {@code POST  /one-customers} : Create a new oneCustomer.
     *
     * @param oneCustomerDTO the oneCustomerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oneCustomerDTO, or with status {@code 400 (Bad Request)} if the oneCustomer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OneCustomerDTO> createOneCustomer(@RequestBody OneCustomerDTO oneCustomerDTO) throws URISyntaxException {
        LOG.debug("REST request to save OneCustomer : {}", oneCustomerDTO);
        if (oneCustomerDTO.getId() != null) {
            throw new BadRequestAlertException("A new oneCustomer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        oneCustomerDTO = oneCustomerService.save(oneCustomerDTO);
        return ResponseEntity.created(new URI("/api/one-customers/" + oneCustomerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, oneCustomerDTO.getId()))
            .body(oneCustomerDTO);
    }

    /**
     * {@code PUT  /one-customers/:id} : Updates an existing oneCustomer.
     *
     * @param id the id of the oneCustomerDTO to save.
     * @param oneCustomerDTO the oneCustomerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oneCustomerDTO,
     * or with status {@code 400 (Bad Request)} if the oneCustomerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oneCustomerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OneCustomerDTO> updateOneCustomer(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody OneCustomerDTO oneCustomerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update OneCustomer : {}, {}", id, oneCustomerDTO);
        if (oneCustomerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oneCustomerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oneCustomerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        oneCustomerDTO = oneCustomerService.update(oneCustomerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oneCustomerDTO.getId()))
            .body(oneCustomerDTO);
    }

    /**
     * {@code PATCH  /one-customers/:id} : Partial updates given fields of an existing oneCustomer, field will ignore if it is null
     *
     * @param id the id of the oneCustomerDTO to save.
     * @param oneCustomerDTO the oneCustomerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oneCustomerDTO,
     * or with status {@code 400 (Bad Request)} if the oneCustomerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the oneCustomerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the oneCustomerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OneCustomerDTO> partialUpdateOneCustomer(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody OneCustomerDTO oneCustomerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OneCustomer partially : {}, {}", id, oneCustomerDTO);
        if (oneCustomerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oneCustomerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oneCustomerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OneCustomerDTO> result = oneCustomerService.partialUpdate(oneCustomerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oneCustomerDTO.getId())
        );
    }

    /**
     * {@code GET  /one-customers} : get all the oneCustomers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oneCustomers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OneCustomerDTO>> getAllOneCustomers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of OneCustomers");
        Page<OneCustomerDTO> page = oneCustomerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /one-customers/:id} : get the "id" oneCustomer.
     *
     * @param id the id of the oneCustomerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oneCustomerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OneCustomerDTO> getOneCustomer(@PathVariable("id") String id) {
        LOG.debug("REST request to get OneCustomer : {}", id);
        Optional<OneCustomerDTO> oneCustomerDTO = oneCustomerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oneCustomerDTO);
    }

    /**
     * {@code DELETE  /one-customers/:id} : delete the "id" oneCustomer.
     *
     * @param id the id of the oneCustomerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneCustomer(@PathVariable("id") String id) {
        LOG.debug("REST request to delete OneCustomer : {}", id);
        oneCustomerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
