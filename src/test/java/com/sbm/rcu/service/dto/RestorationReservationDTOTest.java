package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RestorationReservationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestorationReservationDTO.class);
        RestorationReservationDTO restorationReservationDTO1 = new RestorationReservationDTO();
        restorationReservationDTO1.setId("id1");
        RestorationReservationDTO restorationReservationDTO2 = new RestorationReservationDTO();
        assertThat(restorationReservationDTO1).isNotEqualTo(restorationReservationDTO2);
        restorationReservationDTO2.setId(restorationReservationDTO1.getId());
        assertThat(restorationReservationDTO1).isEqualTo(restorationReservationDTO2);
        restorationReservationDTO2.setId("id2");
        assertThat(restorationReservationDTO1).isNotEqualTo(restorationReservationDTO2);
        restorationReservationDTO1.setId(null);
        assertThat(restorationReservationDTO1).isNotEqualTo(restorationReservationDTO2);
    }
}
