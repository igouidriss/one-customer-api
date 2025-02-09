package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SourceReferenceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceReferenceDTO.class);
        SourceReferenceDTO sourceReferenceDTO1 = new SourceReferenceDTO();
        sourceReferenceDTO1.setId("id1");
        SourceReferenceDTO sourceReferenceDTO2 = new SourceReferenceDTO();
        assertThat(sourceReferenceDTO1).isNotEqualTo(sourceReferenceDTO2);
        sourceReferenceDTO2.setId(sourceReferenceDTO1.getId());
        assertThat(sourceReferenceDTO1).isEqualTo(sourceReferenceDTO2);
        sourceReferenceDTO2.setId("id2");
        assertThat(sourceReferenceDTO1).isNotEqualTo(sourceReferenceDTO2);
        sourceReferenceDTO1.setId(null);
        assertThat(sourceReferenceDTO1).isNotEqualTo(sourceReferenceDTO2);
    }
}
