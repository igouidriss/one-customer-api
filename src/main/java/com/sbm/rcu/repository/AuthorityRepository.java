package com.sbm.rcu.repository;

import com.sbm.rcu.domain.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
@Repository
public interface AuthorityRepository extends MongoRepository<Authority, String> {}
