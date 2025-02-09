package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.PayloadAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.Payload;
import com.sbm.rcu.repository.PayloadRepository;
import com.sbm.rcu.service.dto.PayloadDTO;
import com.sbm.rcu.service.mapper.PayloadMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PayloadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayloadResourceIT {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LANG = "AAAAAAAAAA";
    private static final String UPDATED_LANG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VIP = false;
    private static final Boolean UPDATED_IS_VIP = true;

    private static final String ENTITY_API_URL = "/api/payloads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PayloadRepository payloadRepository;

    @Autowired
    private PayloadMapper payloadMapper;

    @Autowired
    private MockMvc restPayloadMockMvc;

    private Payload payload;

    private Payload insertedPayload;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payload createEntity() {
        return new Payload()
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .lang(DEFAULT_LANG)
            .isVip(DEFAULT_IS_VIP);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payload createUpdatedEntity() {
        return new Payload()
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .lang(UPDATED_LANG)
            .isVip(UPDATED_IS_VIP);
    }

    @BeforeEach
    public void initTest() {
        payload = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPayload != null) {
            payloadRepository.delete(insertedPayload);
            insertedPayload = null;
        }
    }

    @Test
    void createPayload() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Payload
        PayloadDTO payloadDTO = payloadMapper.toDto(payload);
        var returnedPayloadDTO = om.readValue(
            restPayloadMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(payloadDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PayloadDTO.class
        );

        // Validate the Payload in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPayload = payloadMapper.toEntity(returnedPayloadDTO);
        assertPayloadUpdatableFieldsEquals(returnedPayload, getPersistedPayload(returnedPayload));

        insertedPayload = returnedPayload;
    }

    @Test
    void createPayloadWithExistingId() throws Exception {
        // Create the Payload with an existing ID
        payload.setId("existing_id");
        PayloadDTO payloadDTO = payloadMapper.toDto(payload);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayloadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(payloadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payload in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPayloads() throws Exception {
        // Initialize the database
        insertedPayload = payloadRepository.save(payload);

        // Get all the payloadList
        restPayloadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payload.getId())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG)))
            .andExpect(jsonPath("$.[*].isVip").value(hasItem(DEFAULT_IS_VIP)));
    }

    @Test
    void getPayload() throws Exception {
        // Initialize the database
        insertedPayload = payloadRepository.save(payload);

        // Get the payload
        restPayloadMockMvc
            .perform(get(ENTITY_API_URL_ID, payload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payload.getId()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG))
            .andExpect(jsonPath("$.isVip").value(DEFAULT_IS_VIP));
    }

    @Test
    void getNonExistingPayload() throws Exception {
        // Get the payload
        restPayloadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPayload() throws Exception {
        // Initialize the database
        insertedPayload = payloadRepository.save(payload);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the payload
        Payload updatedPayload = payloadRepository.findById(payload.getId()).orElseThrow();
        updatedPayload
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .lang(UPDATED_LANG)
            .isVip(UPDATED_IS_VIP);
        PayloadDTO payloadDTO = payloadMapper.toDto(updatedPayload);

        restPayloadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payloadDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(payloadDTO))
            )
            .andExpect(status().isOk());

        // Validate the Payload in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPayloadToMatchAllProperties(updatedPayload);
    }

    @Test
    void putNonExistingPayload() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        payload.setId(UUID.randomUUID().toString());

        // Create the Payload
        PayloadDTO payloadDTO = payloadMapper.toDto(payload);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payloadDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(payloadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payload in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPayload() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        payload.setId(UUID.randomUUID().toString());

        // Create the Payload
        PayloadDTO payloadDTO = payloadMapper.toDto(payload);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(payloadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payload in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPayload() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        payload.setId(UUID.randomUUID().toString());

        // Create the Payload
        PayloadDTO payloadDTO = payloadMapper.toDto(payload);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(payloadDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payload in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePayloadWithPatch() throws Exception {
        // Initialize the database
        insertedPayload = payloadRepository.save(payload);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the payload using partial update
        Payload partialUpdatedPayload = new Payload();
        partialUpdatedPayload.setId(payload.getId());

        partialUpdatedPayload.isVip(UPDATED_IS_VIP);

        restPayloadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayload.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPayload))
            )
            .andExpect(status().isOk());

        // Validate the Payload in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPayloadUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPayload, payload), getPersistedPayload(payload));
    }

    @Test
    void fullUpdatePayloadWithPatch() throws Exception {
        // Initialize the database
        insertedPayload = payloadRepository.save(payload);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the payload using partial update
        Payload partialUpdatedPayload = new Payload();
        partialUpdatedPayload.setId(payload.getId());

        partialUpdatedPayload
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .lang(UPDATED_LANG)
            .isVip(UPDATED_IS_VIP);

        restPayloadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayload.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPayload))
            )
            .andExpect(status().isOk());

        // Validate the Payload in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPayloadUpdatableFieldsEquals(partialUpdatedPayload, getPersistedPayload(partialUpdatedPayload));
    }

    @Test
    void patchNonExistingPayload() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        payload.setId(UUID.randomUUID().toString());

        // Create the Payload
        PayloadDTO payloadDTO = payloadMapper.toDto(payload);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payloadDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(payloadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payload in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPayload() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        payload.setId(UUID.randomUUID().toString());

        // Create the Payload
        PayloadDTO payloadDTO = payloadMapper.toDto(payload);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(payloadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payload in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPayload() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        payload.setId(UUID.randomUUID().toString());

        // Create the Payload
        PayloadDTO payloadDTO = payloadMapper.toDto(payload);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(payloadDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payload in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePayload() throws Exception {
        // Initialize the database
        insertedPayload = payloadRepository.save(payload);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the payload
        restPayloadMockMvc
            .perform(delete(ENTITY_API_URL_ID, payload.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return payloadRepository.count();
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

    protected Payload getPersistedPayload(Payload payload) {
        return payloadRepository.findById(payload.getId()).orElseThrow();
    }

    protected void assertPersistedPayloadToMatchAllProperties(Payload expectedPayload) {
        assertPayloadAllPropertiesEquals(expectedPayload, getPersistedPayload(expectedPayload));
    }

    protected void assertPersistedPayloadToMatchUpdatableProperties(Payload expectedPayload) {
        assertPayloadAllUpdatablePropertiesEquals(expectedPayload, getPersistedPayload(expectedPayload));
    }
}
