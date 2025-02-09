package com.sbm.rcu.domain;

import static com.sbm.rcu.domain.ExpenseTestSamples.*;
import static com.sbm.rcu.domain.ExpensesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.rcu.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ExpensesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expenses.class);
        Expenses expenses1 = getExpensesSample1();
        Expenses expenses2 = new Expenses();
        assertThat(expenses1).isNotEqualTo(expenses2);

        expenses2.setId(expenses1.getId());
        assertThat(expenses1).isEqualTo(expenses2);

        expenses2 = getExpensesSample2();
        assertThat(expenses1).isNotEqualTo(expenses2);
    }

    @Test
    void expenseTest() {
        Expenses expenses = getExpensesRandomSampleGenerator();
        Expense expenseBack = getExpenseRandomSampleGenerator();

        expenses.addExpense(expenseBack);
        assertThat(expenses.getExpenses()).containsOnly(expenseBack);
        assertThat(expenseBack.getExpenses()).isEqualTo(expenses);

        expenses.removeExpense(expenseBack);
        assertThat(expenses.getExpenses()).doesNotContain(expenseBack);
        assertThat(expenseBack.getExpenses()).isNull();

        expenses.expenses(new HashSet<>(Set.of(expenseBack)));
        assertThat(expenses.getExpenses()).containsOnly(expenseBack);
        assertThat(expenseBack.getExpenses()).isEqualTo(expenses);

        expenses.setExpenses(new HashSet<>());
        assertThat(expenses.getExpenses()).doesNotContain(expenseBack);
        assertThat(expenseBack.getExpenses()).isNull();
    }
}
