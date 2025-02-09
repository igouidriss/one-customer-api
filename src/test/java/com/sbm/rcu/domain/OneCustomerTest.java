package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.GoldenRecordTestSamples.*;
import static com.sbm.rcu.domain.HotelReservationTestSamples.*;
import static com.sbm.rcu.domain.OneCustomerTestSamples.*;
import static com.sbm.rcu.domain.RestorationReservationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OneCustomerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OneCustomer.class);
        OneCustomer oneCustomer1 = getOneCustomerSample1();
        OneCustomer oneCustomer2 = new OneCustomer();
        assertThat(oneCustomer1).isNotEqualTo(oneCustomer2);

        oneCustomer2.setId(oneCustomer1.getId());
        assertThat(oneCustomer1).isEqualTo(oneCustomer2);

        oneCustomer2 = getOneCustomerSample2();
        assertThat(oneCustomer1).isNotEqualTo(oneCustomer2);
    }

    @Test
    void goldenRecordTest() {
        OneCustomer oneCustomer = getOneCustomerRandomSampleGenerator();
        GoldenRecord goldenRecordBack = getGoldenRecordRandomSampleGenerator();

        oneCustomer.setGoldenRecord(goldenRecordBack);
        assertThat(oneCustomer.getGoldenRecord()).isEqualTo(goldenRecordBack);

        oneCustomer.goldenRecord(null);
        assertThat(oneCustomer.getGoldenRecord()).isNull();
    }

    @Test
    void hotelReservationsTest() {
        OneCustomer oneCustomer = getOneCustomerRandomSampleGenerator();
        HotelReservation hotelReservationBack = getHotelReservationRandomSampleGenerator();

        oneCustomer.addHotelReservations(hotelReservationBack);
        assertThat(oneCustomer.getHotelReservations()).containsOnly(hotelReservationBack);
        assertThat(hotelReservationBack.getOneCustomer()).isEqualTo(oneCustomer);

        oneCustomer.removeHotelReservations(hotelReservationBack);
        assertThat(oneCustomer.getHotelReservations()).doesNotContain(hotelReservationBack);
        assertThat(hotelReservationBack.getOneCustomer()).isNull();

        oneCustomer.hotelReservations(new HashSet<>(Set.of(hotelReservationBack)));
        assertThat(oneCustomer.getHotelReservations()).containsOnly(hotelReservationBack);
        assertThat(hotelReservationBack.getOneCustomer()).isEqualTo(oneCustomer);

        oneCustomer.setHotelReservations(new HashSet<>());
        assertThat(oneCustomer.getHotelReservations()).doesNotContain(hotelReservationBack);
        assertThat(hotelReservationBack.getOneCustomer()).isNull();
    }

    @Test
    void restorationReservationsTest() {
        OneCustomer oneCustomer = getOneCustomerRandomSampleGenerator();
        RestorationReservation restorationReservationBack = getRestorationReservationRandomSampleGenerator();

        oneCustomer.addRestorationReservations(restorationReservationBack);
        assertThat(oneCustomer.getRestorationReservations()).containsOnly(restorationReservationBack);
        assertThat(restorationReservationBack.getOneCustomer()).isEqualTo(oneCustomer);

        oneCustomer.removeRestorationReservations(restorationReservationBack);
        assertThat(oneCustomer.getRestorationReservations()).doesNotContain(restorationReservationBack);
        assertThat(restorationReservationBack.getOneCustomer()).isNull();

        oneCustomer.restorationReservations(new HashSet<>(Set.of(restorationReservationBack)));
        assertThat(oneCustomer.getRestorationReservations()).containsOnly(restorationReservationBack);
        assertThat(restorationReservationBack.getOneCustomer()).isEqualTo(oneCustomer);

        oneCustomer.setRestorationReservations(new HashSet<>());
        assertThat(oneCustomer.getRestorationReservations()).doesNotContain(restorationReservationBack);
        assertThat(restorationReservationBack.getOneCustomer()).isNull();
    }
}
