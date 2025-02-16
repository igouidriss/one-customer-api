package com.sbm.rcu.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpensesAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertExpensesAllPropertiesEquals(Expenses expected, Expenses actual) {
        assertExpensesAutoGeneratedPropertiesEquals(expected, actual);
        assertExpensesAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertExpensesAllUpdatablePropertiesEquals(Expenses expected, Expenses actual) {
        assertExpensesUpdatableFieldsEquals(expected, actual);
        assertExpensesUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertExpensesAutoGeneratedPropertiesEquals(Expenses expected, Expenses actual) {
        assertThat(actual)
            .as("Verify Expenses auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertExpensesUpdatableFieldsEquals(Expenses expected, Expenses actual) {
        assertThat(actual)
            .as("Verify Expenses relevant properties")
            .satisfies(a -> assertThat(a.getTotalExpense()).as("check totalExpense").isEqualTo(expected.getTotalExpense()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertExpensesUpdatableRelationshipsEquals(Expenses expected, Expenses actual) {
        // empty method
    }
}
