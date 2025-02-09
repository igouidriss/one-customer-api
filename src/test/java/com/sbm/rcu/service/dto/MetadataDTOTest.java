package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetadataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetadataDTO.class);
        MetadataDTO metadataDTO1 = new MetadataDTO();
        metadataDTO1.setId("id1");
        MetadataDTO metadataDTO2 = new MetadataDTO();
        assertThat(metadataDTO1).isNotEqualTo(metadataDTO2);
        metadataDTO2.setId(metadataDTO1.getId());
        assertThat(metadataDTO1).isEqualTo(metadataDTO2);
        metadataDTO2.setId("id2");
        assertThat(metadataDTO1).isNotEqualTo(metadataDTO2);
        metadataDTO1.setId(null);
        assertThat(metadataDTO1).isNotEqualTo(metadataDTO2);
    }
}
