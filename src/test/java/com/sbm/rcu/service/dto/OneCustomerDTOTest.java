package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OneCustomerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OneCustomerDTO.class);
        OneCustomerDTO oneCustomerDTO1 = new OneCustomerDTO();
        oneCustomerDTO1.setId("id1");
        OneCustomerDTO oneCustomerDTO2 = new OneCustomerDTO();
        assertThat(oneCustomerDTO1).isNotEqualTo(oneCustomerDTO2);
        oneCustomerDTO2.setId(oneCustomerDTO1.getId());
        assertThat(oneCustomerDTO1).isEqualTo(oneCustomerDTO2);
        oneCustomerDTO2.setId("id2");
        assertThat(oneCustomerDTO1).isNotEqualTo(oneCustomerDTO2);
        oneCustomerDTO1.setId(null);
        assertThat(oneCustomerDTO1).isNotEqualTo(oneCustomerDTO2);
    }
}
