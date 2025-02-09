package com.sbm.rcu.repository;

import com.sbm.rcu.domain.Expenses;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Expenses entity.
 */
@Repository
public interface ExpensesRepository extends MongoRepository<Expenses, String> {}
