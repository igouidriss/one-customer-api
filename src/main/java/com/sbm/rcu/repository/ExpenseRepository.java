package com.sbm.rcu.repository;

import com.sbm.rcu.domain.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Expense entity.
 */
@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {}
