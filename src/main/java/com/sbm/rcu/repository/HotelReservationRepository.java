package com.sbm.rcu.repository;

import com.sbm.rcu.domain.HotelReservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the HotelReservation entity.
 */
@Repository
public interface HotelReservationRepository extends MongoRepository<HotelReservation, String> {}
