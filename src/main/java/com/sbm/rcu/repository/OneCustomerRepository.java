package com.sbm.rcu.repository;

import com.sbm.rcu.domain.OneCustomer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the OneCustomer entity.
 */
@Repository
public interface OneCustomerRepository extends MongoRepository<OneCustomer, String> {}
