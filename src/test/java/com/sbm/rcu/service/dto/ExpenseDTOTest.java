package com.sbm.rcu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExpenseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpenseDTO.class);
        ExpenseDTO expenseDTO1 = new ExpenseDTO();
        expenseDTO1.setId("id1");
        ExpenseDTO expenseDTO2 = new ExpenseDTO();
        assertThat(expenseDTO1).isNotEqualTo(expenseDTO2);
        expenseDTO2.setId(expenseDTO1.getId());
        assertThat(expenseDTO1).isEqualTo(expenseDTO2);
        expenseDTO2.setId("id2");
        assertThat(expenseDTO1).isNotEqualTo(expenseDTO2);
        expenseDTO1.setId(null);
        assertThat(expenseDTO1).isNotEqualTo(expenseDTO2);
    }
}
