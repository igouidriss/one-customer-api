package com.sbm.rcu.domain;

import java.util.UUID;

public class CancelledTestSamples {

    public static Cancelled getCancelledSample1() {
        return new Cancelled().id("id1").cancelReason("cancelReason1");
    }

    public static Cancelled getCancelledSample2() {
        return new Cancelled().id("id2").cancelReason("cancelReason2");
    }

    public static Cancelled getCancelledRandomSampleGenerator() {
        return new Cancelled().id(UUID.randomUUID().toString()).cancelReason(UUID.randomUUID().toString());
    }
}
