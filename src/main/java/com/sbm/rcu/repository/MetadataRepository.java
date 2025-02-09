package com.sbm.rcu.repository;

import com.sbm.rcu.domain.Metadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Metadata entity.
 */
@Repository
public interface MetadataRepository extends MongoRepository<Metadata, String> {}
