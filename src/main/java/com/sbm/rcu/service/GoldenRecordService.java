package com.sbm.rcu.service;

import com.sbm.rcu.service.dto.GoldenRecordDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sbm.rcu.domain.GoldenRecord}.
 */
public interface GoldenRecordService {
    /**
     * Save a goldenRecord.
     *
     * @param goldenRecordDTO the entity to save.
     * @return the persisted entity.
     */
    GoldenRecordDTO save(GoldenRecordDTO goldenRecordDTO);

    /**
     * Updates a goldenRecord.
     *
     * @param goldenRecordDTO the entity to update.
     * @return the persisted entity.
     */
    GoldenRecordDTO update(GoldenRecordDTO goldenRecordDTO);

    /**
     * Partially updates a goldenRecord.
     *
     * @param goldenRecordDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GoldenRecordDTO> partialUpdate(GoldenRecordDTO goldenRecordDTO);

    /**
     * Get all the goldenRecords.
     *
     * @return the list of entities.
     */
    List<GoldenRecordDTO> findAll();

    /**
     * Get the "id" goldenRecord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoldenRecordDTO> findOne(String id);

    /**
     * Delete the "id" goldenRecord.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
