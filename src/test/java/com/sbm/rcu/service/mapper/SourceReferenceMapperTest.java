package com.sbm.rcu.service.mapper;

import static com.sbm.rcu.domain.SourceReferenceAsserts.*;
import static com.sbm.rcu.domain.SourceReferenceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SourceReferenceMapperTest {

    private SourceReferenceMapper sourceReferenceMapper;

    @BeforeEach
    void setUp() {
        sourceReferenceMapper = new SourceReferenceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSourceReferenceSample1();
        var actual = sourceReferenceMapper.toEntity(sourceReferenceMapper.toDto(expected));
        assertSourceReferenceAllPropertiesEquals(expected, actual);
    }
}
