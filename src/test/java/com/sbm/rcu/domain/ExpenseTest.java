package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.ExpenseTestSamples.*;
import static com.sbm.rcu.domain.ExpensesTestSamples.*;
import static com.sbm.rcu.domain.MetadataTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExpenseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expense.class);
        Expense expense1 = getExpenseSample1();
        Expense expense2 = new Expense();
        assertThat(expense1).isNotEqualTo(expense2);

        expense2.setId(expense1.getId());
        assertThat(expense1).isEqualTo(expense2);

        expense2 = getExpenseSample2();
        assertThat(expense1).isNotEqualTo(expense2);
    }

    @Test
    void metadataTest() {
        Expense expense = getExpenseRandomSampleGenerator();
        Metadata metadataBack = getMetadataRandomSampleGenerator();

        expense.setMetadata(metadataBack);
        assertThat(expense.getMetadata()).isEqualTo(metadataBack);

        expense.metadata(null);
        assertThat(expense.getMetadata()).isNull();
    }

    @Test
    void expensesTest() {
        Expense expense = getExpenseRandomSampleGenerator();
        Expenses expensesBack = getExpensesRandomSampleGenerator();

        expense.setExpenses(expensesBack);
        assertThat(expense.getExpenses()).isEqualTo(expensesBack);

        expense.expenses(null);
        assertThat(expense.getExpenses()).isNull();
    }
}
