package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.AddressTestSamples.*;
import static com.sbm.rcu.domain.PayloadTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = getAddressSample1();
        Address address2 = new Address();
        assertThat(address1).isNotEqualTo(address2);

        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);

        address2 = getAddressSample2();
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    void payloadTest() {
        Address address = getAddressRandomSampleGenerator();
        Payload payloadBack = getPayloadRandomSampleGenerator();

        address.setPayload(payloadBack);
        assertThat(address.getPayload()).isEqualTo(payloadBack);

        address.payload(null);
        assertThat(address.getPayload()).isNull();
    }
}
