package com.sbm.rcu.repository;

import com.sbm.rcu.domain.SourceReference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SourceReference entity.
 */
@Repository
public interface SourceReferenceRepository extends MongoRepository<SourceReference, String> {}
