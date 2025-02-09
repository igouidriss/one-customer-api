package com.sbm.rcu.domain;

import java.util.UUID;

public class SourceReferenceTestSamples {

    public static SourceReference getSourceReferenceSample1() {
        return new SourceReference().id("id1").source("source1").value("value1");
    }

    public static SourceReference getSourceReferenceSample2() {
        return new SourceReference().id("id2").source("source2").value("value2");
    }

    public static SourceReference getSourceReferenceRandomSampleGenerator() {
        return new SourceReference()
            .id(UUID.randomUUID().toString())
            .source(UUID.randomUUID().toString())
            .value(UUID.randomUUID().toString());
    }
}
