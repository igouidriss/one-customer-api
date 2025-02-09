package com.sbm.rcu.repository;

import com.sbm.rcu.domain.RestorationReservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RestorationReservation entity.
 */
@Repository
public interface RestorationReservationRepository extends MongoRepository<RestorationReservation, String> {}
