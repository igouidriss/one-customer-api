package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.MetadataAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.Metadata;
import com.sbm.rcu.repository.MetadataRepository;
import com.sbm.rcu.service.dto.MetadataDTO;
import com.sbm.rcu.service.mapper.MetadataMapper;
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
 * Integration tests for the {@link MetadataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetadataResourceIT {

    private static final String DEFAULT_ID_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_ID_EVENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_META_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_META_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/metadata";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MetadataRepository metadataRepository;

    @Autowired
    private MetadataMapper metadataMapper;

    @Autowired
    private MockMvc restMetadataMockMvc;

    private Metadata metadata;

    private Metadata insertedMetadata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Metadata createEntity() {
        return new Metadata().idEvent(DEFAULT_ID_EVENT).metaTimestamp(DEFAULT_META_TIMESTAMP);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Metadata createUpdatedEntity() {
        return new Metadata().idEvent(UPDATED_ID_EVENT).metaTimestamp(UPDATED_META_TIMESTAMP);
    }

    @BeforeEach
    public void initTest() {
        metadata = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedMetadata != null) {
            metadataRepository.delete(insertedMetadata);
            insertedMetadata = null;
        }
    }

    @Test
    void createMetadata() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Metadata
        MetadataDTO metadataDTO = metadataMapper.toDto(metadata);
        var returnedMetadataDTO = om.readValue(
            restMetadataMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metadataDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MetadataDTO.class
        );

        // Validate the Metadata in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMetadata = metadataMapper.toEntity(returnedMetadataDTO);
        assertMetadataUpdatableFieldsEquals(returnedMetadata, getPersistedMetadata(returnedMetadata));

        insertedMetadata = returnedMetadata;
    }

    @Test
    void createMetadataWithExistingId() throws Exception {
        // Create the Metadata with an existing ID
        metadata.setId("existing_id");
        MetadataDTO metadataDTO = metadataMapper.toDto(metadata);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetadataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metadataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Metadata in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllMetadata() throws Exception {
        // Initialize the database
        insertedMetadata = metadataRepository.save(metadata);

        // Get all the metadataList
        restMetadataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metadata.getId())))
            .andExpect(jsonPath("$.[*].idEvent").value(hasItem(DEFAULT_ID_EVENT)))
            .andExpect(jsonPath("$.[*].metaTimestamp").value(hasItem(DEFAULT_META_TIMESTAMP.toString())));
    }

    @Test
    void getMetadata() throws Exception {
        // Initialize the database
        insertedMetadata = metadataRepository.save(metadata);

        // Get the metadata
        restMetadataMockMvc
            .perform(get(ENTITY_API_URL_ID, metadata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metadata.getId()))
            .andExpect(jsonPath("$.idEvent").value(DEFAULT_ID_EVENT))
            .andExpect(jsonPath("$.metaTimestamp").value(DEFAULT_META_TIMESTAMP.toString()));
    }

    @Test
    void getNonExistingMetadata() throws Exception {
        // Get the metadata
        restMetadataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingMetadata() throws Exception {
        // Initialize the database
        insertedMetadata = metadataRepository.save(metadata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the metadata
        Metadata updatedMetadata = metadataRepository.findById(metadata.getId()).orElseThrow();
        updatedMetadata.idEvent(UPDATED_ID_EVENT).metaTimestamp(UPDATED_META_TIMESTAMP);
        MetadataDTO metadataDTO = metadataMapper.toDto(updatedMetadata);

        restMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metadataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(metadataDTO))
            )
            .andExpect(status().isOk());

        // Validate the Metadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMetadataToMatchAllProperties(updatedMetadata);
    }

    @Test
    void putNonExistingMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metadata.setId(UUID.randomUUID().toString());

        // Create the Metadata
        MetadataDTO metadataDTO = metadataMapper.toDto(metadata);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metadataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(metadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Metadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metadata.setId(UUID.randomUUID().toString());

        // Create the Metadata
        MetadataDTO metadataDTO = metadataMapper.toDto(metadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(metadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Metadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metadata.setId(UUID.randomUUID().toString());

        // Create the Metadata
        MetadataDTO metadataDTO = metadataMapper.toDto(metadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetadataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metadataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Metadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMetadataWithPatch() throws Exception {
        // Initialize the database
        insertedMetadata = metadataRepository.save(metadata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the metadata using partial update
        Metadata partialUpdatedMetadata = new Metadata();
        partialUpdatedMetadata.setId(metadata.getId());

        restMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetadata.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMetadata))
            )
            .andExpect(status().isOk());

        // Validate the Metadata in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMetadataUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMetadata, metadata), getPersistedMetadata(metadata));
    }

    @Test
    void fullUpdateMetadataWithPatch() throws Exception {
        // Initialize the database
        insertedMetadata = metadataRepository.save(metadata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the metadata using partial update
        Metadata partialUpdatedMetadata = new Metadata();
        partialUpdatedMetadata.setId(metadata.getId());

        partialUpdatedMetadata.idEvent(UPDATED_ID_EVENT).metaTimestamp(UPDATED_META_TIMESTAMP);

        restMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetadata.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMetadata))
            )
            .andExpect(status().isOk());

        // Validate the Metadata in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMetadataUpdatableFieldsEquals(partialUpdatedMetadata, getPersistedMetadata(partialUpdatedMetadata));
    }

    @Test
    void patchNonExistingMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metadata.setId(UUID.randomUUID().toString());

        // Create the Metadata
        MetadataDTO metadataDTO = metadataMapper.toDto(metadata);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metadataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(metadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Metadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metadata.setId(UUID.randomUUID().toString());

        // Create the Metadata
        MetadataDTO metadataDTO = metadataMapper.toDto(metadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(metadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Metadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metadata.setId(UUID.randomUUID().toString());

        // Create the Metadata
        MetadataDTO metadataDTO = metadataMapper.toDto(metadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetadataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(metadataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Metadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMetadata() throws Exception {
        // Initialize the database
        insertedMetadata = metadataRepository.save(metadata);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the metadata
        restMetadataMockMvc
            .perform(delete(ENTITY_API_URL_ID, metadata.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return metadataRepository.count();
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

    protected Metadata getPersistedMetadata(Metadata metadata) {
        return metadataRepository.findById(metadata.getId()).orElseThrow();
    }

    protected void assertPersistedMetadataToMatchAllProperties(Metadata expectedMetadata) {
        assertMetadataAllPropertiesEquals(expectedMetadata, getPersistedMetadata(expectedMetadata));
    }

    protected void assertPersistedMetadataToMatchUpdatableProperties(Metadata expectedMetadata) {
        assertMetadataAllUpdatablePropertiesEquals(expectedMetadata, getPersistedMetadata(expectedMetadata));
    }
}
