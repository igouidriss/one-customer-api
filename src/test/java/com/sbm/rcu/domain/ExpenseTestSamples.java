package com.sbm.rcu.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ExpenseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Expense getExpenseSample1() {
        return new Expense()
            .id("id1")
            .expenseType("expenseType1")
            .amount(1L)
            .depositAmount(1L)
            .totalAmount(1L)
            .shift("shift1")
            .guestCount(1)
            .hotelName("hotelName1")
            .hotelId("hotelId1")
            .restaurantName("restaurantName1")
            .restaurantId("restaurantId1")
            .clientId("clientId1");
    }

    public static Expense getExpenseSample2() {
        return new Expense()
            .id("id2")
            .expenseType("expenseType2")
            .amount(2L)
            .depositAmount(2L)
            .totalAmount(2L)
            .shift("shift2")
            .guestCount(2)
            .hotelName("hotelName2")
            .hotelId("hotelId2")
            .restaurantName("restaurantName2")
            .restaurantId("restaurantId2")
            .clientId("clientId2");
    }

    public static Expense getExpenseRandomSampleGenerator() {
        return new Expense()
            .id(UUID.randomUUID().toString())
            .expenseType(UUID.randomUUID().toString())
            .amount(longCount.incrementAndGet())
            .depositAmount(longCount.incrementAndGet())
            .totalAmount(longCount.incrementAndGet())
            .shift(UUID.randomUUID().toString())
            .guestCount(intCount.incrementAndGet())
            .hotelName(UUID.randomUUID().toString())
            .hotelId(UUID.randomUUID().toString())
            .restaurantName(UUID.randomUUID().toString())
            .restaurantId(UUID.randomUUID().toString())
            .clientId(UUID.randomUUID().toString());
    }
}
