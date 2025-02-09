package com.sbm.rcu.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PayloadAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPayloadAllPropertiesEquals(Payload expected, Payload actual) {
        assertPayloadAutoGeneratedPropertiesEquals(expected, actual);
        assertPayloadAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPayloadAllUpdatablePropertiesEquals(Payload expected, Payload actual) {
        assertPayloadUpdatableFieldsEquals(expected, actual);
        assertPayloadUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPayloadAutoGeneratedPropertiesEquals(Payload expected, Payload actual) {
        assertThat(actual)
            .as("Verify Payload auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPayloadUpdatableFieldsEquals(Payload expected, Payload actual) {
        assertThat(actual)
            .as("Verify Payload relevant properties")
            .satisfies(a -> assertThat(a.getLastName()).as("check lastName").isEqualTo(expected.getLastName()))
            .satisfies(a -> assertThat(a.getFirstName()).as("check firstName").isEqualTo(expected.getFirstName()))
            .satisfies(a -> assertThat(a.getBirthDate()).as("check birthDate").isEqualTo(expected.getBirthDate()))
            .satisfies(a -> assertThat(a.getLang()).as("check lang").isEqualTo(expected.getLang()))
            .satisfies(a -> assertThat(a.getIsVip()).as("check isVip").isEqualTo(expected.getIsVip()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPayloadUpdatableRelationshipsEquals(Payload expected, Payload actual) {
        assertThat(actual)
            .as("Verify Payload relationships")
            .satisfies(a -> assertThat(a.getMetadata()).as("check metadata").isEqualTo(expected.getMetadata()));
    }
}
