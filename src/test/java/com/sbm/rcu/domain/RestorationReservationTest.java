package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.CancelledTestSamples.*;
import static com.sbm.rcu.domain.ExpensesTestSamples.*;
import static com.sbm.rcu.domain.MetadataTestSamples.*;
import static com.sbm.rcu.domain.OneCustomerTestSamples.*;
import static com.sbm.rcu.domain.RestorationReservationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RestorationReservationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestorationReservation.class);
        RestorationReservation restorationReservation1 = getRestorationReservationSample1();
        RestorationReservation restorationReservation2 = new RestorationReservation();
        assertThat(restorationReservation1).isNotEqualTo(restorationReservation2);

        restorationReservation2.setId(restorationReservation1.getId());
        assertThat(restorationReservation1).isEqualTo(restorationReservation2);

        restorationReservation2 = getRestorationReservationSample2();
        assertThat(restorationReservation1).isNotEqualTo(restorationReservation2);
    }

    @Test
    void cancelledTest() {
        RestorationReservation restorationReservation = getRestorationReservationRandomSampleGenerator();
        Cancelled cancelledBack = getCancelledRandomSampleGenerator();

        restorationReservation.setCancelled(cancelledBack);
        assertThat(restorationReservation.getCancelled()).isEqualTo(cancelledBack);

        restorationReservation.cancelled(null);
        assertThat(restorationReservation.getCancelled()).isNull();
    }

    @Test
    void expensesTest() {
        RestorationReservation restorationReservation = getRestorationReservationRandomSampleGenerator();
        Expenses expensesBack = getExpensesRandomSampleGenerator();

        restorationReservation.setExpenses(expensesBack);
        assertThat(restorationReservation.getExpenses()).isEqualTo(expensesBack);

        restorationReservation.expenses(null);
        assertThat(restorationReservation.getExpenses()).isNull();
    }

    @Test
    void metadataTest() {
        RestorationReservation restorationReservation = getRestorationReservationRandomSampleGenerator();
        Metadata metadataBack = getMetadataRandomSampleGenerator();

        restorationReservation.setMetadata(metadataBack);
        assertThat(restorationReservation.getMetadata()).isEqualTo(metadataBack);

        restorationReservation.metadata(null);
        assertThat(restorationReservation.getMetadata()).isNull();
    }

    @Test
    void oneCustomerTest() {
        RestorationReservation restorationReservation = getRestorationReservationRandomSampleGenerator();
        OneCustomer oneCustomerBack = getOneCustomerRandomSampleGenerator();

        restorationReservation.setOneCustomer(oneCustomerBack);
        assertThat(restorationReservation.getOneCustomer()).isEqualTo(oneCustomerBack);

        restorationReservation.oneCustomer(null);
        assertThat(restorationReservation.getOneCustomer()).isNull();
    }
}
