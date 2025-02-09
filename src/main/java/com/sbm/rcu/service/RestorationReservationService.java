package com.sbm.rcu.service;

import com.sbm.rcu.service.dto.RestorationReservationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.sbm.rcu.domain.RestorationReservation}.
 */
public interface RestorationReservationService {
    /**
     * Save a restorationReservation.
     *
     * @param restorationReservationDTO the entity to save.
     * @return the persisted entity.
     */
    RestorationReservationDTO save(RestorationReservationDTO restorationReservationDTO);

    /**
     * Updates a restorationReservation.
     *
     * @param restorationReservationDTO the entity to update.
     * @return the persisted entity.
     */
    RestorationReservationDTO update(RestorationReservationDTO restorationReservationDTO);

    /**
     * Partially updates a restorationReservation.
     *
     * @param restorationReservationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RestorationReservationDTO> partialUpdate(RestorationReservationDTO restorationReservationDTO);

    /**
     * Get all the restorationReservations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RestorationReservationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" restorationReservation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RestorationReservationDTO> findOne(String id);

    /**
     * Delete the "id" restorationReservation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
