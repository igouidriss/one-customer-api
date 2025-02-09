package com.sbm.rcu.service;

import com.sbm.rcu.service.dto.ExpensesDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sbm.rcu.domain.Expenses}.
 */
public interface ExpensesService {
    /**
     * Save a expenses.
     *
     * @param expensesDTO the entity to save.
     * @return the persisted entity.
     */
    ExpensesDTO save(ExpensesDTO expensesDTO);

    /**
     * Updates a expenses.
     *
     * @param expensesDTO the entity to update.
     * @return the persisted entity.
     */
    ExpensesDTO update(ExpensesDTO expensesDTO);

    /**
     * Partially updates a expenses.
     *
     * @param expensesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExpensesDTO> partialUpdate(ExpensesDTO expensesDTO);

    /**
     * Get all the expenses.
     *
     * @return the list of entities.
     */
    List<ExpensesDTO> findAll();

    /**
     * Get the "id" expenses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExpensesDTO> findOne(String id);

    /**
     * Delete the "id" expenses.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
