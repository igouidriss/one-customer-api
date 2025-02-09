package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.PayloadTestSamples.*;
import static com.sbm.rcu.domain.PhoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Phone.class);
        Phone phone1 = getPhoneSample1();
        Phone phone2 = new Phone();
        assertThat(phone1).isNotEqualTo(phone2);

        phone2.setId(phone1.getId());
        assertThat(phone1).isEqualTo(phone2);

        phone2 = getPhoneSample2();
        assertThat(phone1).isNotEqualTo(phone2);
    }

    @Test
    void payloadTest() {
        Phone phone = getPhoneRandomSampleGenerator();
        Payload payloadBack = getPayloadRandomSampleGenerator();

        phone.setPayload(payloadBack);
        assertThat(phone.getPayload()).isEqualTo(payloadBack);

        phone.payload(null);
        assertThat(phone.getPayload()).isNull();
    }
}
