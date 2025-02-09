package com.sbm.rcu.repository;

import com.sbm.rcu.domain.Payload;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Payload entity.
 */
@Repository
public interface PayloadRepository extends MongoRepository<Payload, String> {}
