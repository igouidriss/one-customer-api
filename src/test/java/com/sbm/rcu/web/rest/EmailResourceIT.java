package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.EmailAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.Email;
import com.sbm.rcu.repository.EmailRepository;
import com.sbm.rcu.service.dto.EmailDTO;
import com.sbm.rcu.service.mapper.EmailMapper;
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
 * Integration tests for the {@link EmailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmailResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/emails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private MockMvc restEmailMockMvc;

    private Email email;

    private Email insertedEmail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createEntity() {
        return new Email().type(DEFAULT_TYPE).value(DEFAULT_VALUE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createUpdatedEntity() {
        return new Email().type(UPDATED_TYPE).value(UPDATED_VALUE);
    }

    @BeforeEach
    public void initTest() {
        email = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmail != null) {
            emailRepository.delete(insertedEmail);
            insertedEmail = null;
        }
    }

    @Test
    void createEmail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);
        var returnedEmailDTO = om.readValue(
            restEmailMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmailDTO.class
        );

        // Validate the Email in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEmail = emailMapper.toEntity(returnedEmailDTO);
        assertEmailUpdatableFieldsEquals(returnedEmail, getPersistedEmail(returnedEmail));

        insertedEmail = returnedEmail;
    }

    @Test
    void createEmailWithExistingId() throws Exception {
        // Create the Email with an existing ID
        email.setId("existing_id");
        EmailDTO emailDTO = emailMapper.toDto(email);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEmails() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.save(email);

        // Get all the emailList
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(email.getId())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    void getEmail() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.save(email);

        // Get the email
        restEmailMockMvc
            .perform(get(ENTITY_API_URL_ID, email.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(email.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    void getNonExistingEmail() throws Exception {
        // Get the email
        restEmailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingEmail() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.save(email);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the email
        Email updatedEmail = emailRepository.findById(email.getId()).orElseThrow();
        updatedEmail.type(UPDATED_TYPE).value(UPDATED_VALUE);
        EmailDTO emailDTO = emailMapper.toDto(updatedEmail);

        restEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmailToMatchAllProperties(updatedEmail);
    }

    @Test
    void putNonExistingEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(UUID.randomUUID().toString());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(UUID.randomUUID().toString());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(UUID.randomUUID().toString());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEmailWithPatch() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.save(email);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the email using partial update
        Email partialUpdatedEmail = new Email();
        partialUpdatedEmail.setId(email.getId());

        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmail))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmailUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEmail, email), getPersistedEmail(email));
    }

    @Test
    void fullUpdateEmailWithPatch() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.save(email);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the email using partial update
        Email partialUpdatedEmail = new Email();
        partialUpdatedEmail.setId(email.getId());

        partialUpdatedEmail.type(UPDATED_TYPE).value(UPDATED_VALUE);

        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmail))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmailUpdatableFieldsEquals(partialUpdatedEmail, getPersistedEmail(partialUpdatedEmail));
    }

    @Test
    void patchNonExistingEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(UUID.randomUUID().toString());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(UUID.randomUUID().toString());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(UUID.randomUUID().toString());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(emailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEmail() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.save(email);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the email
        restEmailMockMvc
            .perform(delete(ENTITY_API_URL_ID, email.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return emailRepository.count();
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

    protected Email getPersistedEmail(Email email) {
        return emailRepository.findById(email.getId()).orElseThrow();
    }

    protected void assertPersistedEmailToMatchAllProperties(Email expectedEmail) {
        assertEmailAllPropertiesEquals(expectedEmail, getPersistedEmail(expectedEmail));
    }

    protected void assertPersistedEmailToMatchUpdatableProperties(Email expectedEmail) {
        assertEmailAllUpdatablePropertiesEquals(expectedEmail, getPersistedEmail(expectedEmail));
    }
}
