package com.sbm.rcu.service;

import com.sbm.rcu.domain.Expense;
import com.sbm.rcu.repository.ExpenseRepository;
import com.sbm.rcu.service.dto.ExpenseDTO;
import com.sbm.rcu.service.mapper.ExpenseMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.Expense}.
 */
@Service
public class ExpenseService {

    private static final Logger LOG = LoggerFactory.getLogger(ExpenseService.class);

    private final ExpenseRepository expenseRepository;

    private final ExpenseMapper expenseMapper;

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
    }

    /**
     * Save a expense.
     *
     * @param expenseDTO the entity to save.
     * @return the persisted entity.
     */
    public ExpenseDTO save(ExpenseDTO expenseDTO) {
        LOG.debug("Request to save Expense : {}", expenseDTO);
        Expense expense = expenseMapper.toEntity(expenseDTO);
        expense = expenseRepository.save(expense);
        return expenseMapper.toDto(expense);
    }

    /**
     * Update a expense.
     *
     * @param expenseDTO the entity to save.
     * @return the persisted entity.
     */
    public ExpenseDTO update(ExpenseDTO expenseDTO) {
        LOG.debug("Request to update Expense : {}", expenseDTO);
        Expense expense = expenseMapper.toEntity(expenseDTO);
        expense = expenseRepository.save(expense);
        return expenseMapper.toDto(expense);
    }

    /**
     * Partially update a expense.
     *
     * @param expenseDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ExpenseDTO> partialUpdate(ExpenseDTO expenseDTO) {
        LOG.debug("Request to partially update Expense : {}", expenseDTO);

        return expenseRepository
            .findById(expenseDTO.getId())
            .map(existingExpense -> {
                expenseMapper.partialUpdate(existingExpense, expenseDTO);

                return existingExpense;
            })
            .map(expenseRepository::save)
            .map(expenseMapper::toDto);
    }

    /**
     * Get all the expenses.
     *
     * @return the list of entities.
     */
    public List<ExpenseDTO> findAll() {
        LOG.debug("Request to get all Expenses");
        return expenseRepository.findAll().stream().map(expenseMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one expense by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ExpenseDTO> findOne(String id) {
        LOG.debug("Request to get Expense : {}", id);
        return expenseRepository.findById(id).map(expenseMapper::toDto);
    }

    /**
     * Delete the expense by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete Expense : {}", id);
        expenseRepository.deleteById(id);
    }
}
