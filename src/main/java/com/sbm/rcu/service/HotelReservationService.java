package com.sbm.rcu.service;

import com.sbm.rcu.service.dto.HotelReservationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.sbm.rcu.domain.HotelReservation}.
 */
public interface HotelReservationService {
    /**
     * Save a hotelReservation.
     *
     * @param hotelReservationDTO the entity to save.
     * @return the persisted entity.
     */
    HotelReservationDTO save(HotelReservationDTO hotelReservationDTO);

    /**
     * Updates a hotelReservation.
     *
     * @param hotelReservationDTO the entity to update.
     * @return the persisted entity.
     */
    HotelReservationDTO update(HotelReservationDTO hotelReservationDTO);

    /**
     * Partially updates a hotelReservation.
     *
     * @param hotelReservationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HotelReservationDTO> partialUpdate(HotelReservationDTO hotelReservationDTO);

    /**
     * Get all the hotelReservations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HotelReservationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" hotelReservation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HotelReservationDTO> findOne(String id);

    /**
     * Delete the "id" hotelReservation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
