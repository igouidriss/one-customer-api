package com.sbm.rcu.domain;

import java.util.UUID;

public class OneCustomerTestSamples {

    public static OneCustomer getOneCustomerSample1() {
        return new OneCustomer().id("id1").domaine("domaine1").aggregateId("aggregateId1").aggregateType("aggregateType1");
    }

    public static OneCustomer getOneCustomerSample2() {
        return new OneCustomer().id("id2").domaine("domaine2").aggregateId("aggregateId2").aggregateType("aggregateType2");
    }

    public static OneCustomer getOneCustomerRandomSampleGenerator() {
        return new OneCustomer()
            .id(UUID.randomUUID().toString())
            .domaine(UUID.randomUUID().toString())
            .aggregateId(UUID.randomUUID().toString())
            .aggregateType(UUID.randomUUID().toString());
    }
}
