package com.sbm.rcu.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceReferenceAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSourceReferenceAllPropertiesEquals(SourceReference expected, SourceReference actual) {
        assertSourceReferenceAutoGeneratedPropertiesEquals(expected, actual);
        assertSourceReferenceAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSourceReferenceAllUpdatablePropertiesEquals(SourceReference expected, SourceReference actual) {
        assertSourceReferenceUpdatableFieldsEquals(expected, actual);
        assertSourceReferenceUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSourceReferenceAutoGeneratedPropertiesEquals(SourceReference expected, SourceReference actual) {
        assertThat(actual)
            .as("Verify SourceReference auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSourceReferenceUpdatableFieldsEquals(SourceReference expected, SourceReference actual) {
        assertThat(actual)
            .as("Verify SourceReference relevant properties")
            .satisfies(a -> assertThat(a.getSource()).as("check source").isEqualTo(expected.getSource()))
            .satisfies(a -> assertThat(a.getValue()).as("check value").isEqualTo(expected.getValue()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSourceReferenceUpdatableRelationshipsEquals(SourceReference expected, SourceReference actual) {
        assertThat(actual)
            .as("Verify SourceReference relationships")
            .satisfies(a -> assertThat(a.getGoldenRecord()).as("check goldenRecord").isEqualTo(expected.getGoldenRecord()));
    }
}
