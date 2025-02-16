package com.sbm.rcu.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class GoldenRecordAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGoldenRecordAllPropertiesEquals(GoldenRecord expected, GoldenRecord actual) {
        assertGoldenRecordAutoGeneratedPropertiesEquals(expected, actual);
        assertGoldenRecordAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGoldenRecordAllUpdatablePropertiesEquals(GoldenRecord expected, GoldenRecord actual) {
        assertGoldenRecordUpdatableFieldsEquals(expected, actual);
        assertGoldenRecordUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGoldenRecordAutoGeneratedPropertiesEquals(GoldenRecord expected, GoldenRecord actual) {
        assertThat(actual)
            .as("Verify GoldenRecord auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGoldenRecordUpdatableFieldsEquals(GoldenRecord expected, GoldenRecord actual) {
        assertThat(actual)
            .as("Verify GoldenRecord relevant properties")
            .satisfies(a -> assertThat(a.getAggregateId()).as("check aggregateId").isEqualTo(expected.getAggregateId()))
            .satisfies(a -> assertThat(a.getAggregateType()).as("check aggregateType").isEqualTo(expected.getAggregateType()))
            .satisfies(a -> assertThat(a.getDomaine()).as("check domaine").isEqualTo(expected.getDomaine()))
            .satisfies(a -> assertThat(a.getMdmId()).as("check mdmId").isEqualTo(expected.getMdmId()))
            .satisfies(a -> assertThat(a.getSource()).as("check source").isEqualTo(expected.getSource()))
            .satisfies(a -> assertThat(a.getTimestamp()).as("check recordTimestamp").isEqualTo(expected.getTimestamp()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGoldenRecordUpdatableRelationshipsEquals(GoldenRecord expected, GoldenRecord actual) {
        assertThat(actual)
            .as("Verify GoldenRecord relationships")
            .satisfies(a -> assertThat(a.getCancelled()).as("check cancelled").isEqualTo(expected.getCancelled()))
            .satisfies(a -> assertThat(a.getPayload()).as("check payload").isEqualTo(expected.getPayload()));
    }
}
