package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.HotelReservationAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.HotelReservation;
import com.sbm.rcu.repository.HotelReservationRepository;
import com.sbm.rcu.service.dto.HotelReservationDTO;
import com.sbm.rcu.service.mapper.HotelReservationMapper;
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
 * Integration tests for the {@link HotelReservationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HotelReservationResourceIT {

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

    private static final Long DEFAULT_TOTAL_AMOUNT = 1L;
    private static final Long UPDATED_TOTAL_AMOUNT = 2L;

    private static final Instant DEFAULT_ARRIVAL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ARRIVAL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LEAVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LEAVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_GUEST_COUNT = 1;
    private static final Integer UPDATED_GUEST_COUNT = 2;

    private static final String DEFAULT_HOTEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOTEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOTEL_ID = "AAAAAAAAAA";
    private static final String UPDATED_HOTEL_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hotel-reservations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HotelReservationRepository hotelReservationRepository;

    @Autowired
    private HotelReservationMapper hotelReservationMapper;

    @Autowired
    private MockMvc restHotelReservationMockMvc;

    private HotelReservation hotelReservation;

    private HotelReservation insertedHotelReservation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelReservation createEntity() {
        return new HotelReservation()
            .aggregateId(DEFAULT_AGGREGATE_ID)
            .aggregateType(DEFAULT_AGGREGATE_TYPE)
            .clientId(DEFAULT_CLIENT_ID)
            .domaine(DEFAULT_DOMAINE)
            .source(DEFAULT_SOURCE)
            .reservationTimestamp(DEFAULT_RESERVATION_TIMESTAMP)
            .projection(DEFAULT_PROJECTION)
            .date(DEFAULT_DATE)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .arrivalDate(DEFAULT_ARRIVAL_DATE)
            .leaveDate(DEFAULT_LEAVE_DATE)
            .guestCount(DEFAULT_GUEST_COUNT)
            .hotelName(DEFAULT_HOTEL_NAME)
            .hotelId(DEFAULT_HOTEL_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelReservation createUpdatedEntity() {
        return new HotelReservation()
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .clientId(UPDATED_CLIENT_ID)
            .domaine(UPDATED_DOMAINE)
            .source(UPDATED_SOURCE)
            .reservationTimestamp(UPDATED_RESERVATION_TIMESTAMP)
            .projection(UPDATED_PROJECTION)
            .date(UPDATED_DATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .leaveDate(UPDATED_LEAVE_DATE)
            .guestCount(UPDATED_GUEST_COUNT)
            .hotelName(UPDATED_HOTEL_NAME)
            .hotelId(UPDATED_HOTEL_ID);
    }

    @BeforeEach
    public void initTest() {
        hotelReservation = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedHotelReservation != null) {
            hotelReservationRepository.delete(insertedHotelReservation);
            insertedHotelReservation = null;
        }
    }

    @Test
    void createHotelReservation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HotelReservation
        HotelReservationDTO hotelReservationDTO = hotelReservationMapper.toDto(hotelReservation);
        var returnedHotelReservationDTO = om.readValue(
            restHotelReservationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelReservationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HotelReservationDTO.class
        );

        // Validate the HotelReservation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHotelReservation = hotelReservationMapper.toEntity(returnedHotelReservationDTO);
        assertHotelReservationUpdatableFieldsEquals(returnedHotelReservation, getPersistedHotelReservation(returnedHotelReservation));

        insertedHotelReservation = returnedHotelReservation;
    }

    @Test
    void createHotelReservationWithExistingId() throws Exception {
        // Create the HotelReservation with an existing ID
        hotelReservation.setId("existing_id");
        HotelReservationDTO hotelReservationDTO = hotelReservationMapper.toDto(hotelReservation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelReservationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelReservationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HotelReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllHotelReservations() throws Exception {
        // Initialize the database
        insertedHotelReservation = hotelReservationRepository.save(hotelReservation);

        // Get all the hotelReservationList
        restHotelReservationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotelReservation.getId())))
            .andExpect(jsonPath("$.[*].aggregateId").value(hasItem(DEFAULT_AGGREGATE_ID)))
            .andExpect(jsonPath("$.[*].aggregateType").value(hasItem(DEFAULT_AGGREGATE_TYPE)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].reservationTimestamp").value(hasItem(DEFAULT_RESERVATION_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].projection").value(hasItem(DEFAULT_PROJECTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].arrivalDate").value(hasItem(DEFAULT_ARRIVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].leaveDate").value(hasItem(DEFAULT_LEAVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].guestCount").value(hasItem(DEFAULT_GUEST_COUNT)))
            .andExpect(jsonPath("$.[*].hotelName").value(hasItem(DEFAULT_HOTEL_NAME)))
            .andExpect(jsonPath("$.[*].hotelId").value(hasItem(DEFAULT_HOTEL_ID)));
    }

    @Test
    void getHotelReservation() throws Exception {
        // Initialize the database
        insertedHotelReservation = hotelReservationRepository.save(hotelReservation);

        // Get the hotelReservation
        restHotelReservationMockMvc
            .perform(get(ENTITY_API_URL_ID, hotelReservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotelReservation.getId()))
            .andExpect(jsonPath("$.aggregateId").value(DEFAULT_AGGREGATE_ID))
            .andExpect(jsonPath("$.aggregateType").value(DEFAULT_AGGREGATE_TYPE))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.domaine").value(DEFAULT_DOMAINE))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.reservationTimestamp").value(DEFAULT_RESERVATION_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.projection").value(DEFAULT_PROJECTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.arrivalDate").value(DEFAULT_ARRIVAL_DATE.toString()))
            .andExpect(jsonPath("$.leaveDate").value(DEFAULT_LEAVE_DATE.toString()))
            .andExpect(jsonPath("$.guestCount").value(DEFAULT_GUEST_COUNT))
            .andExpect(jsonPath("$.hotelName").value(DEFAULT_HOTEL_NAME))
            .andExpect(jsonPath("$.hotelId").value(DEFAULT_HOTEL_ID));
    }

    @Test
    void getNonExistingHotelReservation() throws Exception {
        // Get the hotelReservation
        restHotelReservationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingHotelReservation() throws Exception {
        // Initialize the database
        insertedHotelReservation = hotelReservationRepository.save(hotelReservation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotelReservation
        HotelReservation updatedHotelReservation = hotelReservationRepository.findById(hotelReservation.getId()).orElseThrow();
        updatedHotelReservation
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .clientId(UPDATED_CLIENT_ID)
            .domaine(UPDATED_DOMAINE)
            .source(UPDATED_SOURCE)
            .reservationTimestamp(UPDATED_RESERVATION_TIMESTAMP)
            .projection(UPDATED_PROJECTION)
            .date(UPDATED_DATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .leaveDate(UPDATED_LEAVE_DATE)
            .guestCount(UPDATED_GUEST_COUNT)
            .hotelName(UPDATED_HOTEL_NAME)
            .hotelId(UPDATED_HOTEL_ID);
        HotelReservationDTO hotelReservationDTO = hotelReservationMapper.toDto(updatedHotelReservation);

        restHotelReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelReservationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hotelReservationDTO))
            )
            .andExpect(status().isOk());

        // Validate the HotelReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHotelReservationToMatchAllProperties(updatedHotelReservation);
    }

    @Test
    void putNonExistingHotelReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelReservation.setId(UUID.randomUUID().toString());

        // Create the HotelReservation
        HotelReservationDTO hotelReservationDTO = hotelReservationMapper.toDto(hotelReservation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelReservationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hotelReservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchHotelReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelReservation.setId(UUID.randomUUID().toString());

        // Create the HotelReservation
        HotelReservationDTO hotelReservationDTO = hotelReservationMapper.toDto(hotelReservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hotelReservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamHotelReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelReservation.setId(UUID.randomUUID().toString());

        // Create the HotelReservation
        HotelReservationDTO hotelReservationDTO = hotelReservationMapper.toDto(hotelReservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelReservationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelReservationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateHotelReservationWithPatch() throws Exception {
        // Initialize the database
        insertedHotelReservation = hotelReservationRepository.save(hotelReservation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotelReservation using partial update
        HotelReservation partialUpdatedHotelReservation = new HotelReservation();
        partialUpdatedHotelReservation.setId(hotelReservation.getId());

        partialUpdatedHotelReservation
            .clientId(UPDATED_CLIENT_ID)
            .source(UPDATED_SOURCE)
            .reservationTimestamp(UPDATED_RESERVATION_TIMESTAMP)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .leaveDate(UPDATED_LEAVE_DATE)
            .hotelName(UPDATED_HOTEL_NAME)
            .hotelId(UPDATED_HOTEL_ID);

        restHotelReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelReservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHotelReservation))
            )
            .andExpect(status().isOk());

        // Validate the HotelReservation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHotelReservationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHotelReservation, hotelReservation),
            getPersistedHotelReservation(hotelReservation)
        );
    }

    @Test
    void fullUpdateHotelReservationWithPatch() throws Exception {
        // Initialize the database
        insertedHotelReservation = hotelReservationRepository.save(hotelReservation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotelReservation using partial update
        HotelReservation partialUpdatedHotelReservation = new HotelReservation();
        partialUpdatedHotelReservation.setId(hotelReservation.getId());

        partialUpdatedHotelReservation
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .clientId(UPDATED_CLIENT_ID)
            .domaine(UPDATED_DOMAINE)
            .source(UPDATED_SOURCE)
            .reservationTimestamp(UPDATED_RESERVATION_TIMESTAMP)
            .projection(UPDATED_PROJECTION)
            .date(UPDATED_DATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .leaveDate(UPDATED_LEAVE_DATE)
            .guestCount(UPDATED_GUEST_COUNT)
            .hotelName(UPDATED_HOTEL_NAME)
            .hotelId(UPDATED_HOTEL_ID);

        restHotelReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelReservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHotelReservation))
            )
            .andExpect(status().isOk());

        // Validate the HotelReservation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHotelReservationUpdatableFieldsEquals(
            partialUpdatedHotelReservation,
            getPersistedHotelReservation(partialUpdatedHotelReservation)
        );
    }

    @Test
    void patchNonExistingHotelReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelReservation.setId(UUID.randomUUID().toString());

        // Create the HotelReservation
        HotelReservationDTO hotelReservationDTO = hotelReservationMapper.toDto(hotelReservation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hotelReservationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hotelReservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchHotelReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelReservation.setId(UUID.randomUUID().toString());

        // Create the HotelReservation
        HotelReservationDTO hotelReservationDTO = hotelReservationMapper.toDto(hotelReservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hotelReservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamHotelReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelReservation.setId(UUID.randomUUID().toString());

        // Create the HotelReservation
        HotelReservationDTO hotelReservationDTO = hotelReservationMapper.toDto(hotelReservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelReservationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hotelReservationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelReservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteHotelReservation() throws Exception {
        // Initialize the database
        insertedHotelReservation = hotelReservationRepository.save(hotelReservation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hotelReservation
        restHotelReservationMockMvc
            .perform(delete(ENTITY_API_URL_ID, hotelReservation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hotelReservationRepository.count();
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

    protected HotelReservation getPersistedHotelReservation(HotelReservation hotelReservation) {
        return hotelReservationRepository.findById(hotelReservation.getId()).orElseThrow();
    }

    protected void assertPersistedHotelReservationToMatchAllProperties(HotelReservation expectedHotelReservation) {
        assertHotelReservationAllPropertiesEquals(expectedHotelReservation, getPersistedHotelReservation(expectedHotelReservation));
    }

    protected void assertPersistedHotelReservationToMatchUpdatableProperties(HotelReservation expectedHotelReservation) {
        assertHotelReservationAllUpdatablePropertiesEquals(
            expectedHotelReservation,
            getPersistedHotelReservation(expectedHotelReservation)
        );
    }
}
