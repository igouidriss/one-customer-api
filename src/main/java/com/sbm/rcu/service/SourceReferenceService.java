package com.sbm.rcu.service;

import com.sbm.rcu.service.dto.SourceReferenceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sbm.rcu.domain.SourceReference}.
 */
public interface SourceReferenceService {
    /**
     * Save a sourceReference.
     *
     * @param sourceReferenceDTO the entity to save.
     * @return the persisted entity.
     */
    SourceReferenceDTO save(SourceReferenceDTO sourceReferenceDTO);

    /**
     * Updates a sourceReference.
     *
     * @param sourceReferenceDTO the entity to update.
     * @return the persisted entity.
     */
    SourceReferenceDTO update(SourceReferenceDTO sourceReferenceDTO);

    /**
     * Partially updates a sourceReference.
     *
     * @param sourceReferenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SourceReferenceDTO> partialUpdate(SourceReferenceDTO sourceReferenceDTO);

    /**
     * Get all the sourceReferences.
     *
     * @return the list of entities.
     */
    List<SourceReferenceDTO> findAll();

    /**
     * Get the "id" sourceReference.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SourceReferenceDTO> findOne(String id);

    /**
     * Delete the "id" sourceReference.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
