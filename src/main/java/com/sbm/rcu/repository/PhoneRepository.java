package com.sbm.rcu.repository;

import com.sbm.rcu.domain.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Phone entity.
 */
@Repository
public interface PhoneRepository extends MongoRepository<Phone, String> {}
