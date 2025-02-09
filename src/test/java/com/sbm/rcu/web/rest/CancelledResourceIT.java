package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.CancelledAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.Cancelled;
import com.sbm.rcu.repository.CancelledRepository;
import com.sbm.rcu.service.dto.CancelledDTO;
import com.sbm.rcu.service.mapper.CancelledMapper;
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
 * Integration tests for the {@link CancelledResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CancelledResourceIT {

    private static final String DEFAULT_CANCEL_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CANCEL_REASON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_IT_CANCELLED = false;
    private static final Boolean UPDATED_IS_IT_CANCELLED = true;

    private static final Instant DEFAULT_CANCEL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CANCEL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/cancelleds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CancelledRepository cancelledRepository;

    @Autowired
    private CancelledMapper cancelledMapper;

    @Autowired
    private MockMvc restCancelledMockMvc;

    private Cancelled cancelled;

    private Cancelled insertedCancelled;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cancelled createEntity() {
        return new Cancelled().cancelReason(DEFAULT_CANCEL_REASON).isItCancelled(DEFAULT_IS_IT_CANCELLED).cancelDate(DEFAULT_CANCEL_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cancelled createUpdatedEntity() {
        return new Cancelled().cancelReason(UPDATED_CANCEL_REASON).isItCancelled(UPDATED_IS_IT_CANCELLED).cancelDate(UPDATED_CANCEL_DATE);
    }

    @BeforeEach
    public void initTest() {
        cancelled = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCancelled != null) {
            cancelledRepository.delete(insertedCancelled);
            insertedCancelled = null;
        }
    }

    @Test
    void createCancelled() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cancelled
        CancelledDTO cancelledDTO = cancelledMapper.toDto(cancelled);
        var returnedCancelledDTO = om.readValue(
            restCancelledMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cancelledDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CancelledDTO.class
        );

        // Validate the Cancelled in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCancelled = cancelledMapper.toEntity(returnedCancelledDTO);
        assertCancelledUpdatableFieldsEquals(returnedCancelled, getPersistedCancelled(returnedCancelled));

        insertedCancelled = returnedCancelled;
    }

    @Test
    void createCancelledWithExistingId() throws Exception {
        // Create the Cancelled with an existing ID
        cancelled.setId("existing_id");
        CancelledDTO cancelledDTO = cancelledMapper.toDto(cancelled);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCancelledMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cancelledDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cancelled in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCancelleds() throws Exception {
        // Initialize the database
        insertedCancelled = cancelledRepository.save(cancelled);

        // Get all the cancelledList
        restCancelledMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cancelled.getId())))
            .andExpect(jsonPath("$.[*].cancelReason").value(hasItem(DEFAULT_CANCEL_REASON)))
            .andExpect(jsonPath("$.[*].isItCancelled").value(hasItem(DEFAULT_IS_IT_CANCELLED)))
            .andExpect(jsonPath("$.[*].cancelDate").value(hasItem(DEFAULT_CANCEL_DATE.toString())));
    }

    @Test
    void getCancelled() throws Exception {
        // Initialize the database
        insertedCancelled = cancelledRepository.save(cancelled);

        // Get the cancelled
        restCancelledMockMvc
            .perform(get(ENTITY_API_URL_ID, cancelled.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cancelled.getId()))
            .andExpect(jsonPath("$.cancelReason").value(DEFAULT_CANCEL_REASON))
            .andExpect(jsonPath("$.isItCancelled").value(DEFAULT_IS_IT_CANCELLED))
            .andExpect(jsonPath("$.cancelDate").value(DEFAULT_CANCEL_DATE.toString()));
    }

    @Test
    void getNonExistingCancelled() throws Exception {
        // Get the cancelled
        restCancelledMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCancelled() throws Exception {
        // Initialize the database
        insertedCancelled = cancelledRepository.save(cancelled);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cancelled
        Cancelled updatedCancelled = cancelledRepository.findById(cancelled.getId()).orElseThrow();
        updatedCancelled.cancelReason(UPDATED_CANCEL_REASON).isItCancelled(UPDATED_IS_IT_CANCELLED).cancelDate(UPDATED_CANCEL_DATE);
        CancelledDTO cancelledDTO = cancelledMapper.toDto(updatedCancelled);

        restCancelledMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cancelledDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cancelledDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cancelled in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCancelledToMatchAllProperties(updatedCancelled);
    }

    @Test
    void putNonExistingCancelled() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cancelled.setId(UUID.randomUUID().toString());

        // Create the Cancelled
        CancelledDTO cancelledDTO = cancelledMapper.toDto(cancelled);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCancelledMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cancelledDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cancelledDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cancelled in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCancelled() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cancelled.setId(UUID.randomUUID().toString());

        // Create the Cancelled
        CancelledDTO cancelledDTO = cancelledMapper.toDto(cancelled);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCancelledMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cancelledDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cancelled in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCancelled() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cancelled.setId(UUID.randomUUID().toString());

        // Create the Cancelled
        CancelledDTO cancelledDTO = cancelledMapper.toDto(cancelled);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCancelledMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cancelledDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cancelled in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCancelledWithPatch() throws Exception {
        // Initialize the database
        insertedCancelled = cancelledRepository.save(cancelled);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cancelled using partial update
        Cancelled partialUpdatedCancelled = new Cancelled();
        partialUpdatedCancelled.setId(cancelled.getId());

        partialUpdatedCancelled.isItCancelled(UPDATED_IS_IT_CANCELLED);

        restCancelledMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCancelled.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCancelled))
            )
            .andExpect(status().isOk());

        // Validate the Cancelled in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCancelledUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCancelled, cancelled),
            getPersistedCancelled(cancelled)
        );
    }

    @Test
    void fullUpdateCancelledWithPatch() throws Exception {
        // Initialize the database
        insertedCancelled = cancelledRepository.save(cancelled);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cancelled using partial update
        Cancelled partialUpdatedCancelled = new Cancelled();
        partialUpdatedCancelled.setId(cancelled.getId());

        partialUpdatedCancelled.cancelReason(UPDATED_CANCEL_REASON).isItCancelled(UPDATED_IS_IT_CANCELLED).cancelDate(UPDATED_CANCEL_DATE);

        restCancelledMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCancelled.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCancelled))
            )
            .andExpect(status().isOk());

        // Validate the Cancelled in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCancelledUpdatableFieldsEquals(partialUpdatedCancelled, getPersistedCancelled(partialUpdatedCancelled));
    }

    @Test
    void patchNonExistingCancelled() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cancelled.setId(UUID.randomUUID().toString());

        // Create the Cancelled
        CancelledDTO cancelledDTO = cancelledMapper.toDto(cancelled);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCancelledMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cancelledDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cancelledDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cancelled in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCancelled() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cancelled.setId(UUID.randomUUID().toString());

        // Create the Cancelled
        CancelledDTO cancelledDTO = cancelledMapper.toDto(cancelled);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCancelledMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cancelledDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cancelled in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCancelled() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cancelled.setId(UUID.randomUUID().toString());

        // Create the Cancelled
        CancelledDTO cancelledDTO = cancelledMapper.toDto(cancelled);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCancelledMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cancelledDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cancelled in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCancelled() throws Exception {
        // Initialize the database
        insertedCancelled = cancelledRepository.save(cancelled);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cancelled
        restCancelledMockMvc
            .perform(delete(ENTITY_API_URL_ID, cancelled.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cancelledRepository.count();
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

    protected Cancelled getPersistedCancelled(Cancelled cancelled) {
        return cancelledRepository.findById(cancelled.getId()).orElseThrow();
    }

    protected void assertPersistedCancelledToMatchAllProperties(Cancelled expectedCancelled) {
        assertCancelledAllPropertiesEquals(expectedCancelled, getPersistedCancelled(expectedCancelled));
    }

    protected void assertPersistedCancelledToMatchUpdatableProperties(Cancelled expectedCancelled) {
        assertCancelledAllUpdatablePropertiesEquals(expectedCancelled, getPersistedCancelled(expectedCancelled));
    }
}
