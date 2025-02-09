package com.sbm.rcu.domain;

import java.util.UUID;

public class MetadataTestSamples {

    public static Metadata getMetadataSample1() {
        return new Metadata().id("id1").idEvent("idEvent1");
    }

    public static Metadata getMetadataSample2() {
        return new Metadata().id("id2").idEvent("idEvent2");
    }

    public static Metadata getMetadataRandomSampleGenerator() {
        return new Metadata().id(UUID.randomUUID().toString()).idEvent(UUID.randomUUID().toString());
    }
}
