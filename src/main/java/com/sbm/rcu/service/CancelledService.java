package com.sbm.rcu.service;

import com.sbm.rcu.service.dto.CancelledDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sbm.rcu.domain.Cancelled}.
 */
public interface CancelledService {
    /**
     * Save a cancelled.
     *
     * @param cancelledDTO the entity to save.
     * @return the persisted entity.
     */
    CancelledDTO save(CancelledDTO cancelledDTO);

    /**
     * Updates a cancelled.
     *
     * @param cancelledDTO the entity to update.
     * @return the persisted entity.
     */
    CancelledDTO update(CancelledDTO cancelledDTO);

    /**
     * Partially updates a cancelled.
     *
     * @param cancelledDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CancelledDTO> partialUpdate(CancelledDTO cancelledDTO);

    /**
     * Get all the cancelleds.
     *
     * @return the list of entities.
     */
    List<CancelledDTO> findAll();

    /**
     * Get the "id" cancelled.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CancelledDTO> findOne(String id);

    /**
     * Delete the "id" cancelled.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
