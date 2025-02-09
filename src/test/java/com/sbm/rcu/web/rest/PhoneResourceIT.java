package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.PhoneAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.Phone;
import com.sbm.rcu.repository.PhoneRepository;
import com.sbm.rcu.service.dto.PhoneDTO;
import com.sbm.rcu.service.mapper.PhoneMapper;
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
 * Integration tests for the {@link PhoneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhoneResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private PhoneMapper phoneMapper;

    @Autowired
    private MockMvc restPhoneMockMvc;

    private Phone phone;

    private Phone insertedPhone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phone createEntity() {
        return new Phone().type(DEFAULT_TYPE).number(DEFAULT_NUMBER);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phone createUpdatedEntity() {
        return new Phone().type(UPDATED_TYPE).number(UPDATED_NUMBER);
    }

    @BeforeEach
    public void initTest() {
        phone = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPhone != null) {
            phoneRepository.delete(insertedPhone);
            insertedPhone = null;
        }
    }

    @Test
    void createPhone() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Phone
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);
        var returnedPhoneDTO = om.readValue(
            restPhoneMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(phoneDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PhoneDTO.class
        );

        // Validate the Phone in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPhone = phoneMapper.toEntity(returnedPhoneDTO);
        assertPhoneUpdatableFieldsEquals(returnedPhone, getPersistedPhone(returnedPhone));

        insertedPhone = returnedPhone;
    }

    @Test
    void createPhoneWithExistingId() throws Exception {
        // Create the Phone with an existing ID
        phone.setId("existing_id");
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(phoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Phone in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPhones() throws Exception {
        // Initialize the database
        insertedPhone = phoneRepository.save(phone);

        // Get all the phoneList
        restPhoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phone.getId())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }

    @Test
    void getPhone() throws Exception {
        // Initialize the database
        insertedPhone = phoneRepository.save(phone);

        // Get the phone
        restPhoneMockMvc
            .perform(get(ENTITY_API_URL_ID, phone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phone.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    void getNonExistingPhone() throws Exception {
        // Get the phone
        restPhoneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPhone() throws Exception {
        // Initialize the database
        insertedPhone = phoneRepository.save(phone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the phone
        Phone updatedPhone = phoneRepository.findById(phone.getId()).orElseThrow();
        updatedPhone.type(UPDATED_TYPE).number(UPDATED_NUMBER);
        PhoneDTO phoneDTO = phoneMapper.toDto(updatedPhone);

        restPhoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phoneDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(phoneDTO))
            )
            .andExpect(status().isOk());

        // Validate the Phone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPhoneToMatchAllProperties(updatedPhone);
    }

    @Test
    void putNonExistingPhone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phone.setId(UUID.randomUUID().toString());

        // Create the Phone
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phoneDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(phoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPhone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phone.setId(UUID.randomUUID().toString());

        // Create the Phone
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(phoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPhone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phone.setId(UUID.randomUUID().toString());

        // Create the Phone
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(phoneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Phone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePhoneWithPatch() throws Exception {
        // Initialize the database
        insertedPhone = phoneRepository.save(phone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the phone using partial update
        Phone partialUpdatedPhone = new Phone();
        partialUpdatedPhone.setId(phone.getId());

        partialUpdatedPhone.type(UPDATED_TYPE).number(UPDATED_NUMBER);

        restPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhone.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPhone))
            )
            .andExpect(status().isOk());

        // Validate the Phone in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPhoneUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPhone, phone), getPersistedPhone(phone));
    }

    @Test
    void fullUpdatePhoneWithPatch() throws Exception {
        // Initialize the database
        insertedPhone = phoneRepository.save(phone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the phone using partial update
        Phone partialUpdatedPhone = new Phone();
        partialUpdatedPhone.setId(phone.getId());

        partialUpdatedPhone.type(UPDATED_TYPE).number(UPDATED_NUMBER);

        restPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhone.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPhone))
            )
            .andExpect(status().isOk());

        // Validate the Phone in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPhoneUpdatableFieldsEquals(partialUpdatedPhone, getPersistedPhone(partialUpdatedPhone));
    }

    @Test
    void patchNonExistingPhone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phone.setId(UUID.randomUUID().toString());

        // Create the Phone
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phoneDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(phoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPhone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phone.setId(UUID.randomUUID().toString());

        // Create the Phone
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(phoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPhone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phone.setId(UUID.randomUUID().toString());

        // Create the Phone
        PhoneDTO phoneDTO = phoneMapper.toDto(phone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(phoneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Phone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePhone() throws Exception {
        // Initialize the database
        insertedPhone = phoneRepository.save(phone);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the phone
        restPhoneMockMvc
            .perform(delete(ENTITY_API_URL_ID, phone.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return phoneRepository.count();
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

    protected Phone getPersistedPhone(Phone phone) {
        return phoneRepository.findById(phone.getId()).orElseThrow();
    }

    protected void assertPersistedPhoneToMatchAllProperties(Phone expectedPhone) {
        assertPhoneAllPropertiesEquals(expectedPhone, getPersistedPhone(expectedPhone));
    }

    protected void assertPersistedPhoneToMatchUpdatableProperties(Phone expectedPhone) {
        assertPhoneAllUpdatablePropertiesEquals(expectedPhone, getPersistedPhone(expectedPhone));
    }
}
