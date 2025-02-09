package com.sbm.rcu.domain;

import java.util.UUID;

public class GoldenRecordTestSamples {

    public static GoldenRecord getGoldenRecordSample1() {
        return new GoldenRecord()
            .id("id1")
            .aggregateId("aggregateId1")
            .aggregateType("aggregateType1")
            .domaine("domaine1")
            .mdmId("mdmId1")
            .source("source1");
    }

    public static GoldenRecord getGoldenRecordSample2() {
        return new GoldenRecord()
            .id("id2")
            .aggregateId("aggregateId2")
            .aggregateType("aggregateType2")
            .domaine("domaine2")
            .mdmId("mdmId2")
            .source("source2");
    }

    public static GoldenRecord getGoldenRecordRandomSampleGenerator() {
        return new GoldenRecord()
            .id(UUID.randomUUID().toString())
            .aggregateId(UUID.randomUUID().toString())
            .aggregateType(UUID.randomUUID().toString())
            .domaine(UUID.randomUUID().toString())
            .mdmId(UUID.randomUUID().toString())
            .source(UUID.randomUUID().toString());
    }
}
