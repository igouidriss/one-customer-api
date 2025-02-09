package com.sbm.rcu.web.rest;

import com.sbm.rcu.repository.ExpenseRepository;
import com.sbm.rcu.service.ExpenseService;
import com.sbm.rcu.service.dto.ExpenseDTO;
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
 * REST controller for managing {@link com.sbm.rcu.domain.Expense}.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseResource {

    private static final Logger LOG = LoggerFactory.getLogger(ExpenseResource.class);

    private static final String ENTITY_NAME = "expense";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpenseService expenseService;

    private final ExpenseRepository expenseRepository;

    public ExpenseResource(ExpenseService expenseService, ExpenseRepository expenseRepository) {
        this.expenseService = expenseService;
        this.expenseRepository = expenseRepository;
    }

    /**
     * {@code POST  /expenses} : Create a new expense.
     *
     * @param expenseDTO the expenseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expenseDTO, or with status {@code 400 (Bad Request)} if the expense has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO) throws URISyntaxException {
        LOG.debug("REST request to save Expense : {}", expenseDTO);
        if (expenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new expense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        expenseDTO = expenseService.save(expenseDTO);
        return ResponseEntity.created(new URI("/api/expenses/" + expenseDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, expenseDTO.getId()))
            .body(expenseDTO);
    }

    /**
     * {@code PUT  /expenses/:id} : Updates an existing expense.
     *
     * @param id the id of the expenseDTO to save.
     * @param expenseDTO the expenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expenseDTO,
     * or with status {@code 400 (Bad Request)} if the expenseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ExpenseDTO expenseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Expense : {}, {}", id, expenseDTO);
        if (expenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, expenseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!expenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        expenseDTO = expenseService.update(expenseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expenseDTO.getId()))
            .body(expenseDTO);
    }

    /**
     * {@code PATCH  /expenses/:id} : Partial updates given fields of an existing expense, field will ignore if it is null
     *
     * @param id the id of the expenseDTO to save.
     * @param expenseDTO the expenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expenseDTO,
     * or with status {@code 400 (Bad Request)} if the expenseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the expenseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the expenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExpenseDTO> partialUpdateExpense(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ExpenseDTO expenseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Expense partially : {}, {}", id, expenseDTO);
        if (expenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, expenseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!expenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExpenseDTO> result = expenseService.partialUpdate(expenseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expenseDTO.getId())
        );
    }

    /**
     * {@code GET  /expenses} : get all the expenses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expenses in body.
     */
    @GetMapping("")
    public List<ExpenseDTO> getAllExpenses() {
        LOG.debug("REST request to get all Expenses");
        return expenseService.findAll();
    }

    /**
     * {@code GET  /expenses/:id} : get the "id" expense.
     *
     * @param id the id of the expenseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expenseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpense(@PathVariable("id") String id) {
        LOG.debug("REST request to get Expense : {}", id);
        Optional<ExpenseDTO> expenseDTO = expenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expenseDTO);
    }

    /**
     * {@code DELETE  /expenses/:id} : delete the "id" expense.
     *
     * @param id the id of the expenseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Expense : {}", id);
        expenseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
