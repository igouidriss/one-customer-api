package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.RestorationReservationAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.RestorationReservation;
import com.sbm.rcu.repository.RestorationReservationRepository;
import com.sbm.rcu.service.dto.RestorationReservationDTO;
import com.sbm.rcu.service.mapper.RestorationReservationMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link RestorationReservationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RestorationReservationResourceIT {

    private static final String DEFAULT_AGGREGATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_AGGREGATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AGGREGATE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_AGGREGATE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAINE = "AAAAAAAAAA";
    private static final String UPDATED_DOMAINE = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESERVATION_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESERVATION_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PROJECTION = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_DEPOSIT_AMOUNT = 1L;
    private static final Long UPDATED_DEPOSIT_AMOUNT = 2L;

    private static final Long DEFAULT_TOTAL_AMOUNT = 1L;
    private static final Long UPDATED_TOTAL_AMOUNT = 2L;

    private static final String DEFAULT_SHIFT = "AAAAAAAAAA";
    private static final String UPDATED_SHIFT = "BBBBBBBBBB";

    private static final Integer DEFAULT_GUEST_COUNT = 1;
    private static final Integer UPDATED_GUEST_COUNT = 2;

    private static final Instant DEFAULT_ARRIVAL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ARRIVAL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RESTAURANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESTAURANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESTAURANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RESTAURANT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/restoration-reservations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RestorationReservationRepository restorationReservationRepository;

    @Autowired
    private RestorationReservationMapper restorationReservationMapper;

    @Autowired
    private MockMvc restRestorationReservationMockMvc;

    private RestorationReservation restorationReservation;

    private RestorationReservation insertedRestorationReservation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RestorationReservation createEntity() {
        return new RestorationReservation()
            .aggregateId(DEFAULT_AGGREGATE_ID)
            .aggregateType(DEFAULT_AGGREGATE_TYPE)
            .clientId(DEFAULT_CLIENT_ID)
            .domaine(DEFAULT_DOMAINE)
            .source(DEFAULT_SOURCE)
            .reservationTimestamp(DEFAULT_RESERVATION_TIMESTAMP)
            .projection(DEFAULT_PROJECTION)
            .date(DEFAULT_DATE)
            .depositAmount(DEFAULT_DEPOSIT_AMOUNT)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .shift(DEFAULT_SHIFT)
            .guestCount(DEFAULT_GUEST_COUNT)
            .arrivalDate(DEFAULT_ARRIVAL_DATE)
            .restaurantName(DEFAULT_RESTAURANT_NAME)
            .restaurantId(DEFAULT_RESTAURANT_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RestorationReservation createUpdatedEntity() {
        return new RestorationReservation()
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .clientId(UPDATED_CLIENT_ID)
            .domaine(UPDATED_DOMAINE)
            .source(UPDATED_SOURCE)
            .reservationTimestamp(UPDATED_RESERVATION_TIMESTAMP)
            .projection(UPDATED_PROJECTION)
            .date(UPDATED_DATE)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .shift(UPDATED_SHIFT)
            .guestCount(UPDATED_GUEST_COUNT)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .restaurantName(UPDATED_RESTAURANT_NAME)
            .restaurantId(UPDATED_RESTAURANT_ID);
    }

    @BeforeEach
    public void initTest() {
        restorationReservation = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRestorationReservation != null) {
            restorationReservationRepository.delete(insertedRestorationReservation);
            insertedRestorationReservation = null;
        }
    }

    @Test
    void createRestorationReservation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RestorationReservation
        RestorationReservationDTO restorationReservationDTO = restorationReservationMapper.toDto(restorationReservation);
        var returnedRestorationReservationDTO = om.readValue(
            restRestorationReservationMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(restorationReservationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RestorationReservationDTO.class
        );

        // Validate the RestorationReservation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRestorationReservation = restorationReservationMapper.toEntity(returnedRestorationReservationDTO);
        assertRestorationReservationUpdatableFieldsEquals(
            returnedRestorationReservation,
            getPersistedRestorationReservation(returnedRestorationReservation)
        );

        insertedRestorationReservation = returnedRestorationReservation;
    }

    @Test
    void createRestorationReservationWithExistingId() throws Exception {
        // Create the RestorationReservation with an existing ID
        restorationReservation.setId("existing_id");
        RestorationReservationDTO restorationReservationDTO = restorationReservationMapper.toDto(restorationReservation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestorationReservationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(restorationReservationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RestorationReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRestorationReservations() throws Exception {
        // Initialize the database
        insertedRestorationReservation = restorationReservationRepository.save(restorationReservation);

        // Get all the restorationReservationList
        restRestorationReservationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restorationReservation.getId())))
            .andExpect(jsonPath("$.[*].aggregateId").value(hasItem(DEFAULT_AGGREGATE_ID)))
            .andExpect(jsonPath("$.[*].aggregateType").value(hasItem(DEFAULT_AGGREGATE_TYPE)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].reservationTimestamp").value(hasItem(DEFAULT_RESERVATION_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].projection").value(hasItem(DEFAULT_PROJECTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].depositAmount").value(hasItem(DEFAULT_DEPOSIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].shift").value(hasItem(DEFAULT_SHIFT)))
            .andExpect(jsonPath("$.[*].guestCount").value(hasItem(DEFAULT_GUEST_COUNT)))
            .andExpect(jsonPath("$.[*].arrivalDate").value(hasItem(DEFAULT_ARRIVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].restaurantName").value(hasItem(DEFAULT_RESTAURANT_NAME)))
            .andExpect(jsonPath("$.[*].restaurantId").value(hasItem(DEFAULT_RESTAURANT_ID)));
    }

    @Test
    void getRestorationReservation() throws Exception {
        // Initialize the database
        insertedRestorationReservation = restorationReservationRepository.save(restorationReservation);

        // Get the restorationReservation
        restRestorationReservationMockMvc
            .perform(get(ENTITY_API_URL_ID, restorationReservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(restorationReservation.getId()))
            .andExpect(jsonPath("$.aggregateId").value(DEFAULT_AGGREGATE_ID))
            .andExpect(jsonPath("$.aggregateType").value(DEFAULT_AGGREGATE_TYPE))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.domaine").value(DEFAULT_DOMAINE))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.reservationTimestamp").value(DEFAULT_RESERVATION_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.projection").value(DEFAULT_PROJECTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.depositAmount").value(DEFAULT_DEPOSIT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.shift").value(DEFAULT_SHIFT))
            .andExpect(jsonPath("$.guestCount").value(DEFAULT_GUEST_COUNT))
            .andExpect(jsonPath("$.arrivalDate").value(DEFAULT_ARRIVAL_DATE.toString()))
            .andExpect(jsonPath("$.restaurantName").value(DEFAULT_RESTAURANT_NAME))
            .andExpect(jsonPath("$.restaurantId").value(DEFAULT_RESTAURANT_ID));
    }

    @Test
    void getNonExistingRestorationReservation() throws Exception {
        // Get the restorationReservation
        restRestorationReservationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingRestorationReservation() throws Exception {
        // Initialize the database
        insertedRestorationReservation = restorationReservationRepository.save(restorationReservation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the restorationReservation
        RestorationReservation updatedRestorationReservation = restorationReservationRepository
            .findById(restorationReservation.getId())
            .orElseThrow();
        updatedRestorationReservation
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .clientId(UPDATED_CLIENT_ID)
            .domaine(UPDATED_DOMAINE)
            .source(UPDATED_SOURCE)
            .reservationTimestamp(UPDATED_RESERVATION_TIMESTAMP)
            .projection(UPDATED_PROJECTION)
            .date(UPDATED_DATE)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .shift(UPDATED_SHIFT)
            .guestCount(UPDATED_GUEST_COUNT)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .restaurantName(UPDATED_RESTAURANT_NAME)
            .restaurantId(UPDATED_RESTAURANT_ID);
        RestorationReservationDTO restorationReservationDTO = restorationReservationMapper.toDto(updatedRestorationReservation);

        restRestorationReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, restorationReservationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(restorationReservationDTO))
            )
            .andExpect(status().isOk());

        // Validate the RestorationReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRestorationReservationToMatchAllProperties(updatedRestorationReservation);
    }

    @Test
    void putNonExistingRestorationReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        restorationReservation.setId(UUID.randomUUID().toString());

        // Create the RestorationReservation
        RestorationReservationDTO restorationReservationDTO = restorationReservationMapper.toDto(restorationReservation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestorationReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, restorationReservationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(restorationReservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestorationReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRestorationReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        restorationReservation.setId(UUID.randomUUID().toString());

        // Create the RestorationReservation
        RestorationReservationDTO restorationReservationDTO = restorationReservationMapper.toDto(restorationReservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestorationReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(restorationReservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestorationReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRestorationReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        restorationReservation.setId(UUID.randomUUID().toString());

        // Create the RestorationReservation
        RestorationReservationDTO restorationReservationDTO = restorationReservationMapper.toDto(restorationReservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestorationReservationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(restorationReservationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RestorationReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRestorationReservationWithPatch() throws Exception {
        // Initialize the database
        insertedRestorationReservation = restorationReservationRepository.save(restorationReservation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the restorationReservation using partial update
        RestorationReservation partialUpdatedRestorationReservation = new RestorationReservation();
        partialUpdatedRestorationReservation.setId(restorationReservation.getId());

        partialUpdatedRestorationReservation
            .aggregateId(UPDATED_AGGREGATE_ID)
            .clientId(UPDATED_CLIENT_ID)
            .domaine(UPDATED_DOMAINE)
            .projection(UPDATED_PROJECTION)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT);

        restRestorationReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestorationReservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRestorationReservation))
            )
            .andExpect(status().isOk());

        // Validate the RestorationReservation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRestorationReservationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRestorationReservation, restorationReservation),
            getPersistedRestorationReservation(restorationReservation)
        );
    }

    @Test
    void fullUpdateRestorationReservationWithPatch() throws Exception {
        // Initialize the database
        insertedRestorationReservation = restorationReservationRepository.save(restorationReservation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the restorationReservation using partial update
        RestorationReservation partialUpdatedRestorationReservation = new RestorationReservation();
        partialUpdatedRestorationReservation.setId(restorationReservation.getId());

        partialUpdatedRestorationReservation
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .clientId(UPDATED_CLIENT_ID)
            .domaine(UPDATED_DOMAINE)
            .source(UPDATED_SOURCE)
            .reservationTimestamp(UPDATED_RESERVATION_TIMESTAMP)
            .projection(UPDATED_PROJECTION)
            .date(UPDATED_DATE)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .shift(UPDATED_SHIFT)
            .guestCount(UPDATED_GUEST_COUNT)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .restaurantName(UPDATED_RESTAURANT_NAME)
            .restaurantId(UPDATED_RESTAURANT_ID);

        restRestorationReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestorationReservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRestorationReservation))
            )
            .andExpect(status().isOk());

        // Validate the RestorationReservation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRestorationReservationUpdatableFieldsEquals(
            partialUpdatedRestorationReservation,
            getPersistedRestorationReservation(partialUpdatedRestorationReservation)
        );
    }

    @Test
    void patchNonExistingRestorationReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        restorationReservation.setId(UUID.randomUUID().toString());

        // Create the RestorationReservation
        RestorationReservationDTO restorationReservationDTO = restorationReservationMapper.toDto(restorationReservation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestorationReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, restorationReservationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(restorationReservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestorationReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRestorationReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        restorationReservation.setId(UUID.randomUUID().toString());

        // Create the RestorationReservation
        RestorationReservationDTO restorationReservationDTO = restorationReservationMapper.toDto(restorationReservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestorationReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(restorationReservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestorationReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRestorationReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        restorationReservation.setId(UUID.randomUUID().toString());

        // Create the RestorationReservation
        RestorationReservationDTO restorationReservationDTO = restorationReservationMapper.toDto(restorationReservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestorationReservationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(restorationReservationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RestorationReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRestorationReservation() throws Exception {
        // Initialize the database
        insertedRestorationReservation = restorationReservationRepository.save(restorationReservation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the restorationReservation
        restRestorationReservationMockMvc
            .perform(delete(ENTITY_API_URL_ID, restorationReservation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return restorationReservationRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected RestorationReservation getPersistedRestorationReservation(RestorationReservation restorationReservation) {
        return restorationReservationRepository.findById(restorationReservation.getId()).orElseThrow();
    }

    protected void assertPersistedRestorationReservationToMatchAllProperties(RestorationReservation expectedRestorationReservation) {
        assertRestorationReservationAllPropertiesEquals(
            expectedRestorationReservation,
            getPersistedRestorationReservation(expectedRestorationReservation)
        );
    }

    protected void assertPersistedRestorationReservationToMatchUpdatableProperties(RestorationReservation expectedRestorationReservation) {
        assertRestorationReservationAllUpdatablePropertiesEquals(
            expectedRestorationReservation,
            getPersistedRestorationReservation(expectedRestorationReservation)
        );
    }
}
