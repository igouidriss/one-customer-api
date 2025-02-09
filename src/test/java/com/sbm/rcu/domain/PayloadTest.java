package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.AddressTestSamples.*;
import static com.sbm.rcu.domain.EmailTestSamples.*;
import static com.sbm.rcu.domain.MetadataTestSamples.*;
import static com.sbm.rcu.domain.PayloadTestSamples.*;
import static com.sbm.rcu.domain.PhoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PayloadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payload.class);
        Payload payload1 = getPayloadSample1();
        Payload payload2 = new Payload();
        assertThat(payload1).isNotEqualTo(payload2);

        payload2.setId(payload1.getId());
        assertThat(payload1).isEqualTo(payload2);

        payload2 = getPayloadSample2();
        assertThat(payload1).isNotEqualTo(payload2);
    }

    @Test
    void metadataTest() {
        Payload payload = getPayloadRandomSampleGenerator();
        Metadata metadataBack = getMetadataRandomSampleGenerator();

        payload.setMetadata(metadataBack);
        assertThat(payload.getMetadata()).isEqualTo(metadataBack);

        payload.metadata(null);
        assertThat(payload.getMetadata()).isNull();
    }

    @Test
    void emailTest() {
        Payload payload = getPayloadRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        payload.addEmail(emailBack);
        assertThat(payload.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getPayload()).isEqualTo(payload);

        payload.removeEmail(emailBack);
        assertThat(payload.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getPayload()).isNull();

        payload.emails(new HashSet<>(Set.of(emailBack)));
        assertThat(payload.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getPayload()).isEqualTo(payload);

        payload.setEmails(new HashSet<>());
        assertThat(payload.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getPayload()).isNull();
    }

    @Test
    void phoneTest() {
        Payload payload = getPayloadRandomSampleGenerator();
        Phone phoneBack = getPhoneRandomSampleGenerator();

        payload.addPhone(phoneBack);
        assertThat(payload.getPhones()).containsOnly(phoneBack);
        assertThat(phoneBack.getPayload()).isEqualTo(payload);

        payload.removePhone(phoneBack);
        assertThat(payload.getPhones()).doesNotContain(phoneBack);
        assertThat(phoneBack.getPayload()).isNull();

        payload.phones(new HashSet<>(Set.of(phoneBack)));
        assertThat(payload.getPhones()).containsOnly(phoneBack);
        assertThat(phoneBack.getPayload()).isEqualTo(payload);

        payload.setPhones(new HashSet<>());
        assertThat(payload.getPhones()).doesNotContain(phoneBack);
        assertThat(phoneBack.getPayload()).isNull();
    }

    @Test
    void addressTest() {
        Payload payload = getPayloadRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        payload.addAddress(addressBack);
        assertThat(payload.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getPayload()).isEqualTo(payload);

        payload.removeAddress(addressBack);
        assertThat(payload.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getPayload()).isNull();

        payload.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(payload.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getPayload()).isEqualTo(payload);

        payload.setAddresses(new HashSet<>());
        assertThat(payload.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getPayload()).isNull();
    }
}
