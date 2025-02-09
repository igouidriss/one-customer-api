package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayloadDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayloadDTO.class);
        PayloadDTO payloadDTO1 = new PayloadDTO();
        payloadDTO1.setId("id1");
        PayloadDTO payloadDTO2 = new PayloadDTO();
        assertThat(payloadDTO1).isNotEqualTo(payloadDTO2);
        payloadDTO2.setId(payloadDTO1.getId());
        assertThat(payloadDTO1).isEqualTo(payloadDTO2);
        payloadDTO2.setId("id2");
        assertThat(payloadDTO1).isNotEqualTo(payloadDTO2);
        payloadDTO1.setId(null);
        assertThat(payloadDTO1).isNotEqualTo(payloadDTO2);
    }
}
