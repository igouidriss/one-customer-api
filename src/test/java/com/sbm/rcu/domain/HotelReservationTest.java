package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.CancelledTestSamples.*;
import static com.sbm.rcu.domain.ExpensesTestSamples.*;
import static com.sbm.rcu.domain.HotelReservationTestSamples.*;
import static com.sbm.rcu.domain.MetadataTestSamples.*;
import static com.sbm.rcu.domain.OneCustomerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelReservationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelReservation.class);
        HotelReservation hotelReservation1 = getHotelReservationSample1();
        HotelReservation hotelReservation2 = new HotelReservation();
        assertThat(hotelReservation1).isNotEqualTo(hotelReservation2);

        hotelReservation2.setId(hotelReservation1.getId());
        assertThat(hotelReservation1).isEqualTo(hotelReservation2);

        hotelReservation2 = getHotelReservationSample2();
        assertThat(hotelReservation1).isNotEqualTo(hotelReservation2);
    }

    @Test
    void cancelledTest() {
        HotelReservation hotelReservation = getHotelReservationRandomSampleGenerator();
        Cancelled cancelledBack = getCancelledRandomSampleGenerator();

        hotelReservation.setCancelled(cancelledBack);
        assertThat(hotelReservation.getCancelled()).isEqualTo(cancelledBack);

        hotelReservation.cancelled(null);
        assertThat(hotelReservation.getCancelled()).isNull();
    }

    @Test
    void expensesTest() {
        HotelReservation hotelReservation = getHotelReservationRandomSampleGenerator();
        Expenses expensesBack = getExpensesRandomSampleGenerator();

        hotelReservation.setExpenses(expensesBack);
        assertThat(hotelReservation.getExpenses()).isEqualTo(expensesBack);

        hotelReservation.expenses(null);
        assertThat(hotelReservation.getExpenses()).isNull();
    }

    @Test
    void metadataTest() {
        HotelReservation hotelReservation = getHotelReservationRandomSampleGenerator();
        Metadata metadataBack = getMetadataRandomSampleGenerator();

        hotelReservation.setMetadata(metadataBack);
        assertThat(hotelReservation.getMetadata()).isEqualTo(metadataBack);

        hotelReservation.metadata(null);
        assertThat(hotelReservation.getMetadata()).isNull();
    }

    @Test
    void oneCustomerTest() {
        HotelReservation hotelReservation = getHotelReservationRandomSampleGenerator();
        OneCustomer oneCustomerBack = getOneCustomerRandomSampleGenerator();

        hotelReservation.setOneCustomer(oneCustomerBack);
        assertThat(hotelReservation.getOneCustomer()).isEqualTo(oneCustomerBack);

        hotelReservation.oneCustomer(null);
        assertThat(hotelReservation.getOneCustomer()).isNull();
    }
}
