package com.sbm.rcu.repository;

import com.sbm.rcu.domain.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Email entity.
 */
@Repository
public interface EmailRepository extends MongoRepository<Email, String> {}
