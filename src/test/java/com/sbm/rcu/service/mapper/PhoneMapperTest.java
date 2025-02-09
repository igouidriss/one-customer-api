package com.sbm.rcu.service.mapper;

import static com.sbm.rcu.domain.PhoneAsserts.*;
import static com.sbm.rcu.domain.PhoneTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhoneMapperTest {

    private PhoneMapper phoneMapper;

    @BeforeEach
    void setUp() {
        phoneMapper = new PhoneMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPhoneSample1();
        var actual = phoneMapper.toEntity(phoneMapper.toDto(expected));
        assertPhoneAllPropertiesEquals(expected, actual);
    }
}
