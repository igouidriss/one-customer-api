package com.sbm.rcu.domain;

import java.util.UUID;

public class PhoneTestSamples {

    public static Phone getPhoneSample1() {
        return new Phone().id("id1").type("type1").number("number1");
    }

    public static Phone getPhoneSample2() {
        return new Phone().id("id2").type("type2").number("number2");
    }

    public static Phone getPhoneRandomSampleGenerator() {
        return new Phone().id(UUID.randomUUID().toString()).type(UUID.randomUUID().toString()).number(UUID.randomUUID().toString());
    }
}
