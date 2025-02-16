package com.sbm.rcu.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CancelledAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCancelledAllPropertiesEquals(Cancelled expected, Cancelled actual) {
        assertCancelledAutoGeneratedPropertiesEquals(expected, actual);
        assertCancelledAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCancelledAllUpdatablePropertiesEquals(Cancelled expected, Cancelled actual) {
        assertCancelledUpdatableFieldsEquals(expected, actual);
        assertCancelledUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCancelledAutoGeneratedPropertiesEquals(Cancelled expected, Cancelled actual) {
        assertThat(actual)
            .as("Verify Cancelled auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCancelledUpdatableFieldsEquals(Cancelled expected, Cancelled actual) {
        assertThat(actual)
            .as("Verify Cancelled relevant properties")
            .satisfies(a -> assertThat(a.getCancelReason()).as("check cancelReason").isEqualTo(expected.getCancelReason()))
            .satisfies(a -> assertThat(a.getIsItCancelled()).as("check isItCancelled").isEqualTo(expected.getIsItCancelled()))
            .satisfies(a -> assertThat(a.getCancelDate()).as("check cancelDate").isEqualTo(expected.getCancelDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCancelledUpdatableRelationshipsEquals(Cancelled expected, Cancelled actual) {
        // empty method
    }
}
