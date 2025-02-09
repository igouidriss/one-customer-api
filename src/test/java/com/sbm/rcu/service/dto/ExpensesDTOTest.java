package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExpensesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpensesDTO.class);
        ExpensesDTO expensesDTO1 = new ExpensesDTO();
        expensesDTO1.setId("id1");
        ExpensesDTO expensesDTO2 = new ExpensesDTO();
        assertThat(expensesDTO1).isNotEqualTo(expensesDTO2);
        expensesDTO2.setId(expensesDTO1.getId());
        assertThat(expensesDTO1).isEqualTo(expensesDTO2);
        expensesDTO2.setId("id2");
        assertThat(expensesDTO1).isNotEqualTo(expensesDTO2);
        expensesDTO1.setId(null);
        assertThat(expensesDTO1).isNotEqualTo(expensesDTO2);
    }
}
