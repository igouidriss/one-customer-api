package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.SourceReferenceAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.SourceReference;
import com.sbm.rcu.repository.SourceReferenceRepository;
import com.sbm.rcu.service.dto.SourceReferenceDTO;
import com.sbm.rcu.service.mapper.SourceReferenceMapper;
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
 * Integration tests for the {@link SourceReferenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SourceReferenceResourceIT {

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/source-references";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SourceReferenceRepository sourceReferenceRepository;

    @Autowired
    private SourceReferenceMapper sourceReferenceMapper;

    @Autowired
    private MockMvc restSourceReferenceMockMvc;

    private SourceReference sourceReference;

    private SourceReference insertedSourceReference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SourceReference createEntity() {
        return new SourceReference().source(DEFAULT_SOURCE).value(DEFAULT_VALUE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SourceReference createUpdatedEntity() {
        return new SourceReference().source(UPDATED_SOURCE).value(UPDATED_VALUE);
    }

    @BeforeEach
    public void initTest() {
        sourceReference = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSourceReference != null) {
            sourceReferenceRepository.delete(insertedSourceReference);
            insertedSourceReference = null;
        }
    }

    @Test
    void createSourceReference() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SourceReference
        SourceReferenceDTO sourceReferenceDTO = sourceReferenceMapper.toDto(sourceReference);
        var returnedSourceReferenceDTO = om.readValue(
            restSourceReferenceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceReferenceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SourceReferenceDTO.class
        );

        // Validate the SourceReference in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSourceReference = sourceReferenceMapper.toEntity(returnedSourceReferenceDTO);
        assertSourceReferenceUpdatableFieldsEquals(returnedSourceReference, getPersistedSourceReference(returnedSourceReference));

        insertedSourceReference = returnedSourceReference;
    }

    @Test
    void createSourceReferenceWithExistingId() throws Exception {
        // Create the SourceReference with an existing ID
        sourceReference.setId("existing_id");
        SourceReferenceDTO sourceReferenceDTO = sourceReferenceMapper.toDto(sourceReference);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceReferenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SourceReference in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSourceReferences() throws Exception {
        // Initialize the database
        insertedSourceReference = sourceReferenceRepository.save(sourceReference);

        // Get all the sourceReferenceList
        restSourceReferenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceReference.getId())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    void getSourceReference() throws Exception {
        // Initialize the database
        insertedSourceReference = sourceReferenceRepository.save(sourceReference);

        // Get the sourceReference
        restSourceReferenceMockMvc
            .perform(get(ENTITY_API_URL_ID, sourceReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sourceReference.getId()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    void getNonExistingSourceReference() throws Exception {
        // Get the sourceReference
        restSourceReferenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSourceReference() throws Exception {
        // Initialize the database
        insertedSourceReference = sourceReferenceRepository.save(sourceReference);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sourceReference
        SourceReference updatedSourceReference = sourceReferenceRepository.findById(sourceReference.getId()).orElseThrow();
        updatedSourceReference.source(UPDATED_SOURCE).value(UPDATED_VALUE);
        SourceReferenceDTO sourceReferenceDTO = sourceReferenceMapper.toDto(updatedSourceReference);

        restSourceReferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sourceReferenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sourceReferenceDTO))
            )
            .andExpect(status().isOk());

        // Validate the SourceReference in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSourceReferenceToMatchAllProperties(updatedSourceReference);
    }

    @Test
    void putNonExistingSourceReference() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceReference.setId(UUID.randomUUID().toString());

        // Create the SourceReference
        SourceReferenceDTO sourceReferenceDTO = sourceReferenceMapper.toDto(sourceReference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceReferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sourceReferenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sourceReferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceReference in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSourceReference() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceReference.setId(UUID.randomUUID().toString());

        // Create the SourceReference
        SourceReferenceDTO sourceReferenceDTO = sourceReferenceMapper.toDto(sourceReference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceReferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sourceReferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceReference in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSourceReference() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceReference.setId(UUID.randomUUID().toString());

        // Create the SourceReference
        SourceReferenceDTO sourceReferenceDTO = sourceReferenceMapper.toDto(sourceReference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceReferenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceReferenceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SourceReference in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSourceReferenceWithPatch() throws Exception {
        // Initialize the database
        insertedSourceReference = sourceReferenceRepository.save(sourceReference);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sourceReference using partial update
        SourceReference partialUpdatedSourceReference = new SourceReference();
        partialUpdatedSourceReference.setId(sourceReference.getId());

        restSourceReferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSourceReference.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSourceReference))
            )
            .andExpect(status().isOk());

        // Validate the SourceReference in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSourceReferenceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSourceReference, sourceReference),
            getPersistedSourceReference(sourceReference)
        );
    }

    @Test
    void fullUpdateSourceReferenceWithPatch() throws Exception {
        // Initialize the database
        insertedSourceReference = sourceReferenceRepository.save(sourceReference);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sourceReference using partial update
        SourceReference partialUpdatedSourceReference = new SourceReference();
        partialUpdatedSourceReference.setId(sourceReference.getId());

        partialUpdatedSourceReference.source(UPDATED_SOURCE).value(UPDATED_VALUE);

        restSourceReferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSourceReference.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSourceReference))
            )
            .andExpect(status().isOk());

        // Validate the SourceReference in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSourceReferenceUpdatableFieldsEquals(
            partialUpdatedSourceReference,
            getPersistedSourceReference(partialUpdatedSourceReference)
        );
    }

    @Test
    void patchNonExistingSourceReference() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceReference.setId(UUID.randomUUID().toString());

        // Create the SourceReference
        SourceReferenceDTO sourceReferenceDTO = sourceReferenceMapper.toDto(sourceReference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceReferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sourceReferenceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sourceReferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceReference in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSourceReference() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceReference.setId(UUID.randomUUID().toString());

        // Create the SourceReference
        SourceReferenceDTO sourceReferenceDTO = sourceReferenceMapper.toDto(sourceReference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceReferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sourceReferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceReference in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSourceReference() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sourceReference.setId(UUID.randomUUID().toString());

        // Create the SourceReference
        SourceReferenceDTO sourceReferenceDTO = sourceReferenceMapper.toDto(sourceReference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceReferenceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sourceReferenceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SourceReference in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSourceReference() throws Exception {
        // Initialize the database
        insertedSourceReference = sourceReferenceRepository.save(sourceReference);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sourceReference
        restSourceReferenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, sourceReference.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sourceReferenceRepository.count();
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

    protected SourceReference getPersistedSourceReference(SourceReference sourceReference) {
        return sourceReferenceRepository.findById(sourceReference.getId()).orElseThrow();
    }

    protected void assertPersistedSourceReferenceToMatchAllProperties(SourceReference expectedSourceReference) {
        assertSourceReferenceAllPropertiesEquals(expectedSourceReference, getPersistedSourceReference(expectedSourceReference));
    }

    protected void assertPersistedSourceReferenceToMatchUpdatableProperties(SourceReference expectedSourceReference) {
        assertSourceReferenceAllUpdatablePropertiesEquals(expectedSourceReference, getPersistedSourceReference(expectedSourceReference));
    }
}
