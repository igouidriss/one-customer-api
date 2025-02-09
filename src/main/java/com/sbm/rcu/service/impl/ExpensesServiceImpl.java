package com.sbm.rcu.service.impl;

import com.sbm.rcu.domain.Expenses;
import com.sbm.rcu.repository.ExpensesRepository;
import com.sbm.rcu.service.ExpensesService;
import com.sbm.rcu.service.dto.ExpensesDTO;
import com.sbm.rcu.service.mapper.ExpensesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.Expenses}.
 */
@Service
public class ExpensesServiceImpl implements ExpensesService {

    private static final Logger LOG = LoggerFactory.getLogger(ExpensesServiceImpl.class);

    private final ExpensesRepository expensesRepository;

    private final ExpensesMapper expensesMapper;

    public ExpensesServiceImpl(ExpensesRepository expensesRepository, ExpensesMapper expensesMapper) {
        this.expensesRepository = expensesRepository;
        this.expensesMapper = expensesMapper;
    }

    @Override
    public ExpensesDTO save(ExpensesDTO expensesDTO) {
        LOG.debug("Request to save Expenses : {}", expensesDTO);
        Expenses expenses = expensesMapper.toEntity(expensesDTO);
        expenses = expensesRepository.save(expenses);
        return expensesMapper.toDto(expenses);
    }

    @Override
    public ExpensesDTO update(ExpensesDTO expensesDTO) {
        LOG.debug("Request to update Expenses : {}", expensesDTO);
        Expenses expenses = expensesMapper.toEntity(expensesDTO);
        expenses = expensesRepository.save(expenses);
        return expensesMapper.toDto(expenses);
    }

    @Override
    public Optional<ExpensesDTO> partialUpdate(ExpensesDTO expensesDTO) {
        LOG.debug("Request to partially update Expenses : {}", expensesDTO);

        return expensesRepository
            .findById(expensesDTO.getId())
            .map(existingExpenses -> {
                expensesMapper.partialUpdate(existingExpenses, expensesDTO);

                return existingExpenses;
            })
            .map(expensesRepository::save)
            .map(expensesMapper::toDto);
    }

    @Override
    public List<ExpensesDTO> findAll() {
        LOG.debug("Request to get all Expenses");
        return expensesRepository.findAll().stream().map(expensesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<ExpensesDTO> findOne(String id) {
        LOG.debug("Request to get Expenses : {}", id);
        return expensesRepository.findById(id).map(expensesMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Expenses : {}", id);
        expensesRepository.deleteById(id);
    }
}
