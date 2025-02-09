package com.sbm.rcu.domain;

import java.util.UUID;

public class EmailTestSamples {

    public static Email getEmailSample1() {
        return new Email().id("id1").type("type1").value("value1");
    }

    public static Email getEmailSample2() {
        return new Email().id("id2").type("type2").value("value2");
    }

    public static Email getEmailRandomSampleGenerator() {
        return new Email().id(UUID.randomUUID().toString()).type(UUID.randomUUID().toString()).value(UUID.randomUUID().toString());
    }
}
