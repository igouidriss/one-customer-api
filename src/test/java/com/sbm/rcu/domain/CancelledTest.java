package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.CancelledTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CancelledTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cancelled.class);
        Cancelled cancelled1 = getCancelledSample1();
        Cancelled cancelled2 = new Cancelled();
        assertThat(cancelled1).isNotEqualTo(cancelled2);

        cancelled2.setId(cancelled1.getId());
        assertThat(cancelled1).isEqualTo(cancelled2);

        cancelled2 = getCancelledSample2();
        assertThat(cancelled1).isNotEqualTo(cancelled2);
    }
}
