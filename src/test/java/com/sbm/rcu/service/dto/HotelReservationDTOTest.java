package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelReservationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelReservationDTO.class);
        HotelReservationDTO hotelReservationDTO1 = new HotelReservationDTO();
        hotelReservationDTO1.setId("id1");
        HotelReservationDTO hotelReservationDTO2 = new HotelReservationDTO();
        assertThat(hotelReservationDTO1).isNotEqualTo(hotelReservationDTO2);
        hotelReservationDTO2.setId(hotelReservationDTO1.getId());
        assertThat(hotelReservationDTO1).isEqualTo(hotelReservationDTO2);
        hotelReservationDTO2.setId("id2");
        assertThat(hotelReservationDTO1).isNotEqualTo(hotelReservationDTO2);
        hotelReservationDTO1.setId(null);
        assertThat(hotelReservationDTO1).isNotEqualTo(hotelReservationDTO2);
    }
}
