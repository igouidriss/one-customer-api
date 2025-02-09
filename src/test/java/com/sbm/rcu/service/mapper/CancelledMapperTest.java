package com.sbm.rcu.service.mapper;

import static com.sbm.rcu.domain.CancelledAsserts.*;
import static com.sbm.rcu.domain.CancelledTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CancelledMapperTest {

    private CancelledMapper cancelledMapper;

    @BeforeEach
    void setUp() {
        cancelledMapper = new CancelledMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCancelledSample1();
        var actual = cancelledMapper.toEntity(cancelledMapper.toDto(expected));
        assertCancelledAllPropertiesEquals(expected, actual);
    }
}
