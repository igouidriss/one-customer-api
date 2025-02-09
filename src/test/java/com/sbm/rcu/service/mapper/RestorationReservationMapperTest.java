package com.sbm.rcu.service.mapper;

import static com.sbm.rcu.domain.RestorationReservationAsserts.*;
import static com.sbm.rcu.domain.RestorationReservationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestorationReservationMapperTest {

    private RestorationReservationMapper restorationReservationMapper;

    @BeforeEach
    void setUp() {
        restorationReservationMapper = new RestorationReservationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRestorationReservationSample1();
        var actual = restorationReservationMapper.toEntity(restorationReservationMapper.toDto(expected));
        assertRestorationReservationAllPropertiesEquals(expected, actual);
    }
}
