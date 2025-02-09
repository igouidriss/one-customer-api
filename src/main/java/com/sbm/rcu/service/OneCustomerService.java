package com.sbm.rcu.service;

import com.sbm.rcu.service.dto.OneCustomerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.sbm.rcu.domain.OneCustomer}.
 */
public interface OneCustomerService {
    /**
     * Save a oneCustomer.
     *
     * @param oneCustomerDTO the entity to save.
     * @return the persisted entity.
     */
    OneCustomerDTO save(OneCustomerDTO oneCustomerDTO);

    /**
     * Updates a oneCustomer.
     *
     * @param oneCustomerDTO the entity to update.
     * @return the persisted entity.
     */
    OneCustomerDTO update(OneCustomerDTO oneCustomerDTO);

    /**
     * Partially updates a oneCustomer.
     *
     * @param oneCustomerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OneCustomerDTO> partialUpdate(OneCustomerDTO oneCustomerDTO);

    /**
     * Get all the oneCustomers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OneCustomerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" oneCustomer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OneCustomerDTO> findOne(String id);

    /**
     * Delete the "id" oneCustomer.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
