package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CancelledDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancelledDTO.class);
        CancelledDTO cancelledDTO1 = new CancelledDTO();
        cancelledDTO1.setId("id1");
        CancelledDTO cancelledDTO2 = new CancelledDTO();
        assertThat(cancelledDTO1).isNotEqualTo(cancelledDTO2);
        cancelledDTO2.setId(cancelledDTO1.getId());
        assertThat(cancelledDTO1).isEqualTo(cancelledDTO2);
        cancelledDTO2.setId("id2");
        assertThat(cancelledDTO1).isNotEqualTo(cancelledDTO2);
        cancelledDTO1.setId(null);
        assertThat(cancelledDTO1).isNotEqualTo(cancelledDTO2);
    }
}
