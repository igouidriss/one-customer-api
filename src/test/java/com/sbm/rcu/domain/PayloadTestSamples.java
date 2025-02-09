package com.sbm.rcu.domain;

import java.util.UUID;

public class PayloadTestSamples {

    public static Payload getPayloadSample1() {
        return new Payload().id("id1").lastName("lastName1").firstName("firstName1").lang("lang1");
    }

    public static Payload getPayloadSample2() {
        return new Payload().id("id2").lastName("lastName2").firstName("firstName2").lang("lang2");
    }

    public static Payload getPayloadRandomSampleGenerator() {
        return new Payload()
            .id(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString())
            .lang(UUID.randomUUID().toString());
    }
}
