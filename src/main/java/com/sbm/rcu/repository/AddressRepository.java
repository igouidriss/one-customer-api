package com.sbm.rcu.repository;

import com.sbm.rcu.domain.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Address entity.
 */
@Repository
public interface AddressRepository extends MongoRepository<Address, String> {}
