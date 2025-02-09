package com.sbm.rcu.service.mapper;

import static com.sbm.rcu.domain.GoldenRecordAsserts.*;
import static com.sbm.rcu.domain.GoldenRecordTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoldenRecordMapperTest {

    private GoldenRecordMapper goldenRecordMapper;

    @BeforeEach
    void setUp() {
        goldenRecordMapper = new GoldenRecordMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getGoldenRecordSample1();
        var actual = goldenRecordMapper.toEntity(goldenRecordMapper.toDto(expected));
        assertGoldenRecordAllPropertiesEquals(expected, actual);
    }
}
