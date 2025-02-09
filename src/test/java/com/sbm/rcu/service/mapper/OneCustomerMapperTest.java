package com.sbm.rcu.service.mapper;

import static com.sbm.rcu.domain.OneCustomerAsserts.*;
import static com.sbm.rcu.domain.OneCustomerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OneCustomerMapperTest {

    private OneCustomerMapper oneCustomerMapper;

    @BeforeEach
    void setUp() {
        oneCustomerMapper = new OneCustomerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOneCustomerSample1();
        var actual = oneCustomerMapper.toEntity(oneCustomerMapper.toDto(expected));
        assertOneCustomerAllPropertiesEquals(expected, actual);
    }
}
