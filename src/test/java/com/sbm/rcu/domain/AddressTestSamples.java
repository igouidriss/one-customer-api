package com.sbm.rcu.domain;

import java.util.UUID;

public class AddressTestSamples {

    public static Address getAddressSample1() {
        return new Address()
            .id("id1")
            .type("type1")
            .zipCode("zipCode1")
            .city("city1")
            .country("country1")
            .line1("line11")
            .line2("line21")
            .line3("line31");
    }

    public static Address getAddressSample2() {
        return new Address()
            .id("id2")
            .type("type2")
            .zipCode("zipCode2")
            .city("city2")
            .country("country2")
            .line1("line12")
            .line2("line22")
            .line3("line32");
    }

    public static Address getAddressRandomSampleGenerator() {
        return new Address()
            .id(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .zipCode(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .line1(UUID.randomUUID().toString())
            .line2(UUID.randomUUID().toString())
            .line3(UUID.randomUUID().toString());
    }
}
