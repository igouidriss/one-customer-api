package com.sbm.rcu.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RestorationReservationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RestorationReservation getRestorationReservationSample1() {
        return new RestorationReservation()
            .id("id1")
            .aggregateId("aggregateId1")
            .aggregateType("aggregateType1")
            .clientId("clientId1")
            .domaine("domaine1")
            .source("source1")
            .projection("projection1")
            .depositAmount(1L)
            .totalAmount(1L)
            .shift("shift1")
            .guestCount(1)
            .restaurantName("restaurantName1")
            .restaurantId("restaurantId1");
    }

    public static RestorationReservation getRestorationReservationSample2() {
        return new RestorationReservation()
            .id("id2")
            .aggregateId("aggregateId2")
            .aggregateType("aggregateType2")
            .clientId("clientId2")
            .domaine("domaine2")
            .source("source2")
            .projection("projection2")
            .depositAmount(2L)
            .totalAmount(2L)
            .shift("shift2")
            .guestCount(2)
            .restaurantName("restaurantName2")
            .restaurantId("restaurantId2");
    }

    public static RestorationReservation getRestorationReservationRandomSampleGenerator() {
        return new RestorationReservation()
            .id(UUID.randomUUID().toString())
            .aggregateId(UUID.randomUUID().toString())
            .aggregateType(UUID.randomUUID().toString())
            .clientId(UUID.randomUUID().toString())
            .domaine(UUID.randomUUID().toString())
            .source(UUID.randomUUID().toString())
            .projection(UUID.randomUUID().toString())
            .depositAmount(longCount.incrementAndGet())
            .totalAmount(longCount.incrementAndGet())
            .shift(UUID.randomUUID().toString())
            .guestCount(intCount.incrementAndGet())
            .restaurantName(UUID.randomUUID().toString())
            .restaurantId(UUID.randomUUID().toString());
    }
}
