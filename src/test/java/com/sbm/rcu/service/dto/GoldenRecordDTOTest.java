package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoldenRecordDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoldenRecordDTO.class);
        GoldenRecordDTO goldenRecordDTO1 = new GoldenRecordDTO();
        goldenRecordDTO1.setId("id1");
        GoldenRecordDTO goldenRecordDTO2 = new GoldenRecordDTO();
        assertThat(goldenRecordDTO1).isNotEqualTo(goldenRecordDTO2);
        goldenRecordDTO2.setId(goldenRecordDTO1.getId());
        assertThat(goldenRecordDTO1).isEqualTo(goldenRecordDTO2);
        goldenRecordDTO2.setId("id2");
        assertThat(goldenRecordDTO1).isNotEqualTo(goldenRecordDTO2);
        goldenRecordDTO1.setId(null);
        assertThat(goldenRecordDTO1).isNotEqualTo(goldenRecordDTO2);
    }
}
