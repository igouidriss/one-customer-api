package com.sbm.rcu.service;

import com.sbm.rcu.service.dto.PayloadDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sbm.rcu.domain.Payload}.
 */
public interface PayloadService {
    /**
     * Save a payload.
     *
     * @param payloadDTO the entity to save.
     * @return the persisted entity.
     */
    PayloadDTO save(PayloadDTO payloadDTO);

    /**
     * Updates a payload.
     *
     * @param payloadDTO the entity to update.
     * @return the persisted entity.
     */
    PayloadDTO update(PayloadDTO payloadDTO);

    /**
     * Partially updates a payload.
     *
     * @param payloadDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PayloadDTO> partialUpdate(PayloadDTO payloadDTO);

    /**
     * Get all the payloads.
     *
     * @return the list of entities.
     */
    List<PayloadDTO> findAll();

    /**
     * Get the "id" payload.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PayloadDTO> findOne(String id);

    /**
     * Delete the "id" payload.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
