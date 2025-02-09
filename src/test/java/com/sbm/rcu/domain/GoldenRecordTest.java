package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.CancelledTestSamples.*;
import static com.sbm.rcu.domain.GoldenRecordTestSamples.*;
import static com.sbm.rcu.domain.PayloadTestSamples.*;
import static com.sbm.rcu.domain.SourceReferenceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GoldenRecordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoldenRecord.class);
        GoldenRecord goldenRecord1 = getGoldenRecordSample1();
        GoldenRecord goldenRecord2 = new GoldenRecord();
        assertThat(goldenRecord1).isNotEqualTo(goldenRecord2);

        goldenRecord2.setId(goldenRecord1.getId());
        assertThat(goldenRecord1).isEqualTo(goldenRecord2);

        goldenRecord2 = getGoldenRecordSample2();
        assertThat(goldenRecord1).isNotEqualTo(goldenRecord2);
    }

    @Test
    void cancelledTest() {
        GoldenRecord goldenRecord = getGoldenRecordRandomSampleGenerator();
        Cancelled cancelledBack = getCancelledRandomSampleGenerator();

        goldenRecord.setCancelled(cancelledBack);
        assertThat(goldenRecord.getCancelled()).isEqualTo(cancelledBack);

        goldenRecord.cancelled(null);
        assertThat(goldenRecord.getCancelled()).isNull();
    }

    @Test
    void payloadTest() {
        GoldenRecord goldenRecord = getGoldenRecordRandomSampleGenerator();
        Payload payloadBack = getPayloadRandomSampleGenerator();

        goldenRecord.setPayload(payloadBack);
        assertThat(goldenRecord.getPayload()).isEqualTo(payloadBack);

        goldenRecord.payload(null);
        assertThat(goldenRecord.getPayload()).isNull();
    }

    @Test
    void sourceReferenceTest() {
        GoldenRecord goldenRecord = getGoldenRecordRandomSampleGenerator();
        SourceReference sourceReferenceBack = getSourceReferenceRandomSampleGenerator();

        goldenRecord.addSourceReference(sourceReferenceBack);
        assertThat(goldenRecord.getSourceReferences()).containsOnly(sourceReferenceBack);
        assertThat(sourceReferenceBack.getGoldenRecord()).isEqualTo(goldenRecord);

        goldenRecord.removeSourceReference(sourceReferenceBack);
        assertThat(goldenRecord.getSourceReferences()).doesNotContain(sourceReferenceBack);
        assertThat(sourceReferenceBack.getGoldenRecord()).isNull();

        goldenRecord.sourceReferences(new HashSet<>(Set.of(sourceReferenceBack)));
        assertThat(goldenRecord.getSourceReferences()).containsOnly(sourceReferenceBack);
        assertThat(sourceReferenceBack.getGoldenRecord()).isEqualTo(goldenRecord);

        goldenRecord.setSourceReferences(new HashSet<>());
        assertThat(goldenRecord.getSourceReferences()).doesNotContain(sourceReferenceBack);
        assertThat(sourceReferenceBack.getGoldenRecord()).isNull();
    }
}
