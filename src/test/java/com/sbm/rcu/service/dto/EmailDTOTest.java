package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailDTO.class);
        EmailDTO emailDTO1 = new EmailDTO();
        emailDTO1.setId("id1");
        EmailDTO emailDTO2 = new EmailDTO();
        assertThat(emailDTO1).isNotEqualTo(emailDTO2);
        emailDTO2.setId(emailDTO1.getId());
        assertThat(emailDTO1).isEqualTo(emailDTO2);
        emailDTO2.setId("id2");
        assertThat(emailDTO1).isNotEqualTo(emailDTO2);
        emailDTO1.setId(null);
        assertThat(emailDTO1).isNotEqualTo(emailDTO2);
    }
}
