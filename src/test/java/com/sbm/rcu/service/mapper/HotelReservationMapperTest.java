package com.sbm.rcu.service.mapper;

import static com.sbm.rcu.domain.HotelReservationAsserts.*;
import static com.sbm.rcu.domain.HotelReservationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HotelReservationMapperTest {

    private HotelReservationMapper hotelReservationMapper;

    @BeforeEach
    void setUp() {
        hotelReservationMapper = new HotelReservationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHotelReservationSample1();
        var actual = hotelReservationMapper.toEntity(hotelReservationMapper.toDto(expected));
        assertHotelReservationAllPropertiesEquals(expected, actual);
    }
}
