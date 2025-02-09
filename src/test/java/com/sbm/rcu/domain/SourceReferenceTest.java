package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.GoldenRecordTestSamples.*;
import static com.sbm.rcu.domain.SourceReferenceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SourceReferenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceReference.class);
        SourceReference sourceReference1 = getSourceReferenceSample1();
        SourceReference sourceReference2 = new SourceReference();
        assertThat(sourceReference1).isNotEqualTo(sourceReference2);

        sourceReference2.setId(sourceReference1.getId());
        assertThat(sourceReference1).isEqualTo(sourceReference2);

        sourceReference2 = getSourceReferenceSample2();
        assertThat(sourceReference1).isNotEqualTo(sourceReference2);
    }

    @Test
    void goldenRecordTest() {
        SourceReference sourceReference = getSourceReferenceRandomSampleGenerator();
        GoldenRecord goldenRecordBack = getGoldenRecordRandomSampleGenerator();

        sourceReference.setGoldenRecord(goldenRecordBack);
        assertThat(sourceReference.getGoldenRecord()).isEqualTo(goldenRecordBack);

        sourceReference.goldenRecord(null);
        assertThat(sourceReference.getGoldenRecord()).isNull();
    }
}
