package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.GoldenRecordAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.GoldenRecord;
import com.sbm.rcu.repository.GoldenRecordRepository;
import com.sbm.rcu.service.dto.GoldenRecordDTO;
import com.sbm.rcu.service.mapper.GoldenRecordMapper;
import java.time.Instant;
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
 * Integration tests for the {@link GoldenRecordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GoldenRecordResourceIT {

    private static final String DEFAULT_AGGREGATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_AGGREGATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AGGREGATE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_AGGREGATE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAINE = "AAAAAAAAAA";
    private static final String UPDATED_DOMAINE = "BBBBBBBBBB";

    private static final String DEFAULT_MDM_ID = "AAAAAAAAAA";
    private static final String UPDATED_MDM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_RECORD_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECORD_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/golden-records";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GoldenRecordRepository goldenRecordRepository;

    @Autowired
    private GoldenRecordMapper goldenRecordMapper;

    @Autowired
    private MockMvc restGoldenRecordMockMvc;

    private GoldenRecord goldenRecord;

    private GoldenRecord insertedGoldenRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldenRecord createEntity() {
        return new GoldenRecord()
            .aggregateId(DEFAULT_AGGREGATE_ID)
            .aggregateType(DEFAULT_AGGREGATE_TYPE)
            .domaine(DEFAULT_DOMAINE)
            .mdmId(DEFAULT_MDM_ID)
            .source(DEFAULT_SOURCE)
            .recordTimestamp(DEFAULT_RECORD_TIMESTAMP);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldenRecord createUpdatedEntity() {
        return new GoldenRecord()
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .domaine(UPDATED_DOMAINE)
            .mdmId(UPDATED_MDM_ID)
            .source(UPDATED_SOURCE)
            .recordTimestamp(UPDATED_RECORD_TIMESTAMP);
    }

    @BeforeEach
    public void initTest() {
        goldenRecord = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedGoldenRecord != null) {
            goldenRecordRepository.delete(insertedGoldenRecord);
            insertedGoldenRecord = null;
        }
    }

    @Test
    void createGoldenRecord() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GoldenRecord
        GoldenRecordDTO goldenRecordDTO = goldenRecordMapper.toDto(goldenRecord);
        var returnedGoldenRecordDTO = om.readValue(
            restGoldenRecordMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(goldenRecordDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GoldenRecordDTO.class
        );

        // Validate the GoldenRecord in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedGoldenRecord = goldenRecordMapper.toEntity(returnedGoldenRecordDTO);
        assertGoldenRecordUpdatableFieldsEquals(returnedGoldenRecord, getPersistedGoldenRecord(returnedGoldenRecord));

        insertedGoldenRecord = returnedGoldenRecord;
    }

    @Test
    void createGoldenRecordWithExistingId() throws Exception {
        // Create the GoldenRecord with an existing ID
        goldenRecord.setId("existing_id");
        GoldenRecordDTO goldenRecordDTO = goldenRecordMapper.toDto(goldenRecord);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoldenRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(goldenRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoldenRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllGoldenRecords() throws Exception {
        // Initialize the database
        insertedGoldenRecord = goldenRecordRepository.save(goldenRecord);

        // Get all the goldenRecordList
        restGoldenRecordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goldenRecord.getId())))
            .andExpect(jsonPath("$.[*].aggregateId").value(hasItem(DEFAULT_AGGREGATE_ID)))
            .andExpect(jsonPath("$.[*].aggregateType").value(hasItem(DEFAULT_AGGREGATE_TYPE)))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE)))
            .andExpect(jsonPath("$.[*].mdmId").value(hasItem(DEFAULT_MDM_ID)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].recordTimestamp").value(hasItem(DEFAULT_RECORD_TIMESTAMP.toString())));
    }

    @Test
    void getGoldenRecord() throws Exception {
        // Initialize the database
        insertedGoldenRecord = goldenRecordRepository.save(goldenRecord);

        // Get the goldenRecord
        restGoldenRecordMockMvc
            .perform(get(ENTITY_API_URL_ID, goldenRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goldenRecord.getId()))
            .andExpect(jsonPath("$.aggregateId").value(DEFAULT_AGGREGATE_ID))
            .andExpect(jsonPath("$.aggregateType").value(DEFAULT_AGGREGATE_TYPE))
            .andExpect(jsonPath("$.domaine").value(DEFAULT_DOMAINE))
            .andExpect(jsonPath("$.mdmId").value(DEFAULT_MDM_ID))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.recordTimestamp").value(DEFAULT_RECORD_TIMESTAMP.toString()));
    }

    @Test
    void getNonExistingGoldenRecord() throws Exception {
        // Get the goldenRecord
        restGoldenRecordMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGoldenRecord() throws Exception {
        // Initialize the database
        insertedGoldenRecord = goldenRecordRepository.save(goldenRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the goldenRecord
        GoldenRecord updatedGoldenRecord = goldenRecordRepository.findById(goldenRecord.getId()).orElseThrow();
        updatedGoldenRecord
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .domaine(UPDATED_DOMAINE)
            .mdmId(UPDATED_MDM_ID)
            .source(UPDATED_SOURCE)
            .recordTimestamp(UPDATED_RECORD_TIMESTAMP);
        GoldenRecordDTO goldenRecordDTO = goldenRecordMapper.toDto(updatedGoldenRecord);

        restGoldenRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goldenRecordDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(goldenRecordDTO))
            )
            .andExpect(status().isOk());

        // Validate the GoldenRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGoldenRecordToMatchAllProperties(updatedGoldenRecord);
    }

    @Test
    void putNonExistingGoldenRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        goldenRecord.setId(UUID.randomUUID().toString());

        // Create the GoldenRecord
        GoldenRecordDTO goldenRecordDTO = goldenRecordMapper.toDto(goldenRecord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoldenRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goldenRecordDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(goldenRecordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldenRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGoldenRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        goldenRecord.setId(UUID.randomUUID().toString());

        // Create the GoldenRecord
        GoldenRecordDTO goldenRecordDTO = goldenRecordMapper.toDto(goldenRecord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldenRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(goldenRecordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldenRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGoldenRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        goldenRecord.setId(UUID.randomUUID().toString());

        // Create the GoldenRecord
        GoldenRecordDTO goldenRecordDTO = goldenRecordMapper.toDto(goldenRecord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldenRecordMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(goldenRecordDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoldenRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGoldenRecordWithPatch() throws Exception {
        // Initialize the database
        insertedGoldenRecord = goldenRecordRepository.save(goldenRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the goldenRecord using partial update
        GoldenRecord partialUpdatedGoldenRecord = new GoldenRecord();
        partialUpdatedGoldenRecord.setId(goldenRecord.getId());

        partialUpdatedGoldenRecord.mdmId(UPDATED_MDM_ID).recordTimestamp(UPDATED_RECORD_TIMESTAMP);

        restGoldenRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoldenRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGoldenRecord))
            )
            .andExpect(status().isOk());

        // Validate the GoldenRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGoldenRecordUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGoldenRecord, goldenRecord),
            getPersistedGoldenRecord(goldenRecord)
        );
    }

    @Test
    void fullUpdateGoldenRecordWithPatch() throws Exception {
        // Initialize the database
        insertedGoldenRecord = goldenRecordRepository.save(goldenRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the goldenRecord using partial update
        GoldenRecord partialUpdatedGoldenRecord = new GoldenRecord();
        partialUpdatedGoldenRecord.setId(goldenRecord.getId());

        partialUpdatedGoldenRecord
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .domaine(UPDATED_DOMAINE)
            .mdmId(UPDATED_MDM_ID)
            .source(UPDATED_SOURCE)
            .recordTimestamp(UPDATED_RECORD_TIMESTAMP);

        restGoldenRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoldenRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGoldenRecord))
            )
            .andExpect(status().isOk());

        // Validate the GoldenRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGoldenRecordUpdatableFieldsEquals(partialUpdatedGoldenRecord, getPersistedGoldenRecord(partialUpdatedGoldenRecord));
    }

    @Test
    void patchNonExistingGoldenRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        goldenRecord.setId(UUID.randomUUID().toString());

        // Create the GoldenRecord
        GoldenRecordDTO goldenRecordDTO = goldenRecordMapper.toDto(goldenRecord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoldenRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, goldenRecordDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(goldenRecordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldenRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGoldenRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        goldenRecord.setId(UUID.randomUUID().toString());

        // Create the GoldenRecord
        GoldenRecordDTO goldenRecordDTO = goldenRecordMapper.toDto(goldenRecord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldenRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(goldenRecordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldenRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGoldenRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        goldenRecord.setId(UUID.randomUUID().toString());

        // Create the GoldenRecord
        GoldenRecordDTO goldenRecordDTO = goldenRecordMapper.toDto(goldenRecord);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldenRecordMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(goldenRecordDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoldenRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGoldenRecord() throws Exception {
        // Initialize the database
        insertedGoldenRecord = goldenRecordRepository.save(goldenRecord);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the goldenRecord
        restGoldenRecordMockMvc
            .perform(delete(ENTITY_API_URL_ID, goldenRecord.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return goldenRecordRepository.count();
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

    protected GoldenRecord getPersistedGoldenRecord(GoldenRecord goldenRecord) {
        return goldenRecordRepository.findById(goldenRecord.getId()).orElseThrow();
    }

    protected void assertPersistedGoldenRecordToMatchAllProperties(GoldenRecord expectedGoldenRecord) {
        assertGoldenRecordAllPropertiesEquals(expectedGoldenRecord, getPersistedGoldenRecord(expectedGoldenRecord));
    }

    protected void assertPersistedGoldenRecordToMatchUpdatableProperties(GoldenRecord expectedGoldenRecord) {
        assertGoldenRecordAllUpdatablePropertiesEquals(expectedGoldenRecord, getPersistedGoldenRecord(expectedGoldenRecord));
    }
}
