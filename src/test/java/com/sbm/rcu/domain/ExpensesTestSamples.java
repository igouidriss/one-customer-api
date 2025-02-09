package com.sbm.rcu.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ExpensesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Expenses getExpensesSample1() {
        return new Expenses().id("id1").totalExpense(1L);
    }

    public static Expenses getExpensesSample2() {
        return new Expenses().id("id2").totalExpense(2L);
    }

    public static Expenses getExpensesRandomSampleGenerator() {
        return new Expenses().id(UUID.randomUUID().toString()).totalExpense(longCount.incrementAndGet());
    }
}
