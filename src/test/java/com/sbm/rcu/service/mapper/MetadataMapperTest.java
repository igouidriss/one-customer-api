package com.sbm.rcu.service.mapper;

import static com.sbm.rcu.domain.MetadataAsserts.*;
import static com.sbm.rcu.domain.MetadataTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetadataMapperTest {

    private MetadataMapper metadataMapper;

    @BeforeEach
    void setUp() {
        metadataMapper = new MetadataMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMetadataSample1();
        var actual = metadataMapper.toEntity(metadataMapper.toDto(expected));
        assertMetadataAllPropertiesEquals(expected, actual);
    }
}
