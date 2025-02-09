package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhoneDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneDTO.class);
        PhoneDTO phoneDTO1 = new PhoneDTO();
        phoneDTO1.setId("id1");
        PhoneDTO phoneDTO2 = new PhoneDTO();
        assertThat(phoneDTO1).isNotEqualTo(phoneDTO2);
        phoneDTO2.setId(phoneDTO1.getId());
        assertThat(phoneDTO1).isEqualTo(phoneDTO2);
        phoneDTO2.setId("id2");
        assertThat(phoneDTO1).isNotEqualTo(phoneDTO2);
        phoneDTO1.setId(null);
        assertThat(phoneDTO1).isNotEqualTo(phoneDTO2);
    }
}
