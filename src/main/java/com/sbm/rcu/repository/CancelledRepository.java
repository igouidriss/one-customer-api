package com.sbm.rcu.repository;

import com.sbm.rcu.domain.Cancelled;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Cancelled entity.
 */
@Repository
public interface CancelledRepository extends MongoRepository<Cancelled, String> {}
