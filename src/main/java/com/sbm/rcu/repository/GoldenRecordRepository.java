package com.sbm.rcu.repository;

import com.sbm.rcu.domain.GoldenRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the GoldenRecord entity.
 */
@Repository
public interface GoldenRecordRepository extends MongoRepository<GoldenRecord, String> {}
