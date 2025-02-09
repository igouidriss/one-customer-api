package com.sbm.rcu.service.mapper;

import static com.sbm.rcu.domain.PayloadAsserts.*;
import static com.sbm.rcu.domain.PayloadTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PayloadMapperTest {

    private PayloadMapper payloadMapper;

    @BeforeEach
    void setUp() {
        payloadMapper = new PayloadMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPayloadSample1();
        var actual = payloadMapper.toEntity(payloadMapper.toDto(expected));
        assertPayloadAllPropertiesEquals(expected, actual);
    }
}
