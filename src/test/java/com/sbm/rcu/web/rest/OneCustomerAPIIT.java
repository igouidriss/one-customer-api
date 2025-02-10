package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.OneCustomerAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.OneCustomer;
import com.sbm.rcu.repository.OneCustomerRepository;
import com.sbm.rcu.service.dto.OneCustomerDTO;
import com.sbm.rcu.service.mapper.OneCustomerMapper;
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
 * Integration tests for the {@link OneCustomerAPI} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OneCustomerAPIIT {

    private static final String DEFAULT_DOMAINE = "AAAAAAAAAA";
    private static final String UPDATED_DOMAINE = "BBBBBBBBBB";

    private static final String DEFAULT_AGGREGATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_AGGREGATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AGGREGATE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_AGGREGATE_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/one-customers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OneCustomerRepository oneCustomerRepository;

    @Autowired
    private OneCustomerMapper oneCustomerMapper;

    @Autowired
    private MockMvc restOneCustomerMockMvc;

    private OneCustomer oneCustomer;

    private OneCustomer insertedOneCustomer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OneCustomer createEntity() {
        return new OneCustomer()
            .domaine(DEFAULT_DOMAINE)
            .aggregateId(DEFAULT_AGGREGATE_ID)
            .aggregateType(DEFAULT_AGGREGATE_TYPE)
            .timestamp(DEFAULT_TIMESTAMP);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OneCustomer createUpdatedEntity() {
        return new OneCustomer()
            .domaine(UPDATED_DOMAINE)
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .timestamp(UPDATED_TIMESTAMP);
    }

    @BeforeEach
    public void initTest() {
        oneCustomer = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedOneCustomer != null) {
            oneCustomerRepository.delete(insertedOneCustomer);
            insertedOneCustomer = null;
        }
    }

    @Test
    void createOneCustomer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OneCustomer
        OneCustomerDTO oneCustomerDTO = oneCustomerMapper.toDto(oneCustomer);
        var returnedOneCustomerDTO = om.readValue(
            restOneCustomerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(oneCustomerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OneCustomerDTO.class
        );

        // Validate the OneCustomer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOneCustomer = oneCustomerMapper.toEntity(returnedOneCustomerDTO);
        assertOneCustomerUpdatableFieldsEquals(returnedOneCustomer, getPersistedOneCustomer(returnedOneCustomer));

        insertedOneCustomer = returnedOneCustomer;
    }

    @Test
    void createOneCustomerWithExistingId() throws Exception {
        // Create the OneCustomer with an existing ID
        oneCustomer.setId("existing_id");
        OneCustomerDTO oneCustomerDTO = oneCustomerMapper.toDto(oneCustomer);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOneCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(oneCustomerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OneCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllOneCustomers() throws Exception {
        // Initialize the database
        insertedOneCustomer = oneCustomerRepository.save(oneCustomer);

        // Get all the oneCustomerList
        restOneCustomerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oneCustomer.getId())))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE)))
            .andExpect(jsonPath("$.[*].aggregateId").value(hasItem(DEFAULT_AGGREGATE_ID)))
            .andExpect(jsonPath("$.[*].aggregateType").value(hasItem(DEFAULT_AGGREGATE_TYPE)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }

    @Test
    void getOneCustomer() throws Exception {
        // Initialize the database
        insertedOneCustomer = oneCustomerRepository.save(oneCustomer);

        // Get the oneCustomer
        restOneCustomerMockMvc
            .perform(get(ENTITY_API_URL_ID, oneCustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oneCustomer.getId()))
            .andExpect(jsonPath("$.domaine").value(DEFAULT_DOMAINE))
            .andExpect(jsonPath("$.aggregateId").value(DEFAULT_AGGREGATE_ID))
            .andExpect(jsonPath("$.aggregateType").value(DEFAULT_AGGREGATE_TYPE))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()));
    }

    @Test
    void getNonExistingOneCustomer() throws Exception {
        // Get the oneCustomer
        restOneCustomerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingOneCustomer() throws Exception {
        // Initialize the database
        insertedOneCustomer = oneCustomerRepository.save(oneCustomer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the oneCustomer
        OneCustomer updatedOneCustomer = oneCustomerRepository.findById(oneCustomer.getId()).orElseThrow();
        updatedOneCustomer
            .domaine(UPDATED_DOMAINE)
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .timestamp(UPDATED_TIMESTAMP);
        OneCustomerDTO oneCustomerDTO = oneCustomerMapper.toDto(updatedOneCustomer);

        restOneCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oneCustomerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(oneCustomerDTO))
            )
            .andExpect(status().isOk());

        // Validate the OneCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOneCustomerToMatchAllProperties(updatedOneCustomer);
    }

    @Test
    void putNonExistingOneCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oneCustomer.setId(UUID.randomUUID().toString());

        // Create the OneCustomer
        OneCustomerDTO oneCustomerDTO = oneCustomerMapper.toDto(oneCustomer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOneCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oneCustomerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(oneCustomerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchOneCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oneCustomer.setId(UUID.randomUUID().toString());

        // Create the OneCustomer
        OneCustomerDTO oneCustomerDTO = oneCustomerMapper.toDto(oneCustomer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(oneCustomerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamOneCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oneCustomer.setId(UUID.randomUUID().toString());

        // Create the OneCustomer
        OneCustomerDTO oneCustomerDTO = oneCustomerMapper.toDto(oneCustomer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneCustomerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(oneCustomerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OneCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateOneCustomerWithPatch() throws Exception {
        // Initialize the database
        insertedOneCustomer = oneCustomerRepository.save(oneCustomer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the oneCustomer using partial update
        OneCustomer partialUpdatedOneCustomer = new OneCustomer();
        partialUpdatedOneCustomer.setId(oneCustomer.getId());

        partialUpdatedOneCustomer.domaine(UPDATED_DOMAINE).aggregateType(UPDATED_AGGREGATE_TYPE);

        restOneCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOneCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOneCustomer))
            )
            .andExpect(status().isOk());

        // Validate the OneCustomer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOneCustomerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOneCustomer, oneCustomer),
            getPersistedOneCustomer(oneCustomer)
        );
    }

    @Test
    void fullUpdateOneCustomerWithPatch() throws Exception {
        // Initialize the database
        insertedOneCustomer = oneCustomerRepository.save(oneCustomer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the oneCustomer using partial update
        OneCustomer partialUpdatedOneCustomer = new OneCustomer();
        partialUpdatedOneCustomer.setId(oneCustomer.getId());

        partialUpdatedOneCustomer
            .domaine(UPDATED_DOMAINE)
            .aggregateId(UPDATED_AGGREGATE_ID)
            .aggregateType(UPDATED_AGGREGATE_TYPE)
            .timestamp(UPDATED_TIMESTAMP);

        restOneCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOneCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOneCustomer))
            )
            .andExpect(status().isOk());

        // Validate the OneCustomer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOneCustomerUpdatableFieldsEquals(partialUpdatedOneCustomer, getPersistedOneCustomer(partialUpdatedOneCustomer));
    }

    @Test
    void patchNonExistingOneCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oneCustomer.setId(UUID.randomUUID().toString());

        // Create the OneCustomer
        OneCustomerDTO oneCustomerDTO = oneCustomerMapper.toDto(oneCustomer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOneCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, oneCustomerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(oneCustomerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchOneCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oneCustomer.setId(UUID.randomUUID().toString());

        // Create the OneCustomer
        OneCustomerDTO oneCustomerDTO = oneCustomerMapper.toDto(oneCustomer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(oneCustomerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamOneCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oneCustomer.setId(UUID.randomUUID().toString());

        // Create the OneCustomer
        OneCustomerDTO oneCustomerDTO = oneCustomerMapper.toDto(oneCustomer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneCustomerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(oneCustomerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OneCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteOneCustomer() throws Exception {
        // Initialize the database
        insertedOneCustomer = oneCustomerRepository.save(oneCustomer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the oneCustomer
        restOneCustomerMockMvc
            .perform(delete(ENTITY_API_URL_ID, oneCustomer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return oneCustomerRepository.count();
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

    protected OneCustomer getPersistedOneCustomer(OneCustomer oneCustomer) {
        return oneCustomerRepository.findById(oneCustomer.getId()).orElseThrow();
    }

    protected void assertPersistedOneCustomerToMatchAllProperties(OneCustomer expectedOneCustomer) {
        assertOneCustomerAllPropertiesEquals(expectedOneCustomer, getPersistedOneCustomer(expectedOneCustomer));
    }

    protected void assertPersistedOneCustomerToMatchUpdatableProperties(OneCustomer expectedOneCustomer) {
        assertOneCustomerAllUpdatablePropertiesEquals(expectedOneCustomer, getPersistedOneCustomer(expectedOneCustomer));
    }
}
