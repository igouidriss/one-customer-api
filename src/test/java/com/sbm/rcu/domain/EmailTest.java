package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.EmailTestSamples.*;
import static com.sbm.rcu.domain.PayloadTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Email.class);
        Email email1 = getEmailSample1();
        Email email2 = new Email();
        assertThat(email1).isNotEqualTo(email2);

        email2.setId(email1.getId());
        assertThat(email1).isEqualTo(email2);

        email2 = getEmailSample2();
        assertThat(email1).isNotEqualTo(email2);
    }

    @Test
    void payloadTest() {
        Email email = getEmailRandomSampleGenerator();
        Payload payloadBack = getPayloadRandomSampleGenerator();

        email.setPayload(payloadBack);
        assertThat(email.getPayload()).isEqualTo(payloadBack);

        email.payload(null);
        assertThat(email.getPayload()).isNull();
    }
}
