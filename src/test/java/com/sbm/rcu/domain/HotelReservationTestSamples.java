package com.sbm.rcu.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HotelReservationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static HotelReservation getHotelReservationSample1() {
        return new HotelReservation()
            .id("id1")
            .aggregateId("aggregateId1")
            .aggregateType("aggregateType1")
            .clientId("clientId1")
            .domaine("domaine1")
            .source("source1")
            .projection("projection1")
            .totalAmount(1L)
            .guestCount(1)
            .hotelName("hotelName1")
            .hotelId("hotelId1");
    }

    public static HotelReservation getHotelReservationSample2() {
        return new HotelReservation()
            .id("id2")
            .aggregateId("aggregateId2")
            .aggregateType("aggregateType2")
            .clientId("clientId2")
            .domaine("domaine2")
            .source("source2")
            .projection("projection2")
            .totalAmount(2L)
            .guestCount(2)
            .hotelName("hotelName2")
            .hotelId("hotelId2");
    }

    public static HotelReservation getHotelReservationRandomSampleGenerator() {
        return new HotelReservation()
            .id(UUID.randomUUID().toString())
            .aggregateId(UUID.randomUUID().toString())
            .aggregateType(UUID.randomUUID().toString())
            .clientId(UUID.randomUUID().toString())
            .domaine(UUID.randomUUID().toString())
            .source(UUID.randomUUID().toString())
            .projection(UUID.randomUUID().toString())
            .totalAmount(longCount.incrementAndGet())
            .guestCount(intCount.incrementAndGet())
            .hotelName(UUID.randomUUID().toString())
            .hotelId(UUID.randomUUID().toString());
    }
}
