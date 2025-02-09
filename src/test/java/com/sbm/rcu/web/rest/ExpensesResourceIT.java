package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.ExpensesAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.Expenses;
import com.sbm.rcu.repository.ExpensesRepository;
import com.sbm.rcu.service.dto.ExpensesDTO;
import com.sbm.rcu.service.mapper.ExpensesMapper;
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
 * Integration tests for the {@link ExpensesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExpensesResourceIT {

    private static final Long DEFAULT_TOTAL_EXPENSE = 1L;
    private static final Long UPDATED_TOTAL_EXPENSE = 2L;

    private static final String ENTITY_API_URL = "/api/expenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private ExpensesMapper expensesMapper;

    @Autowired
    private MockMvc restExpensesMockMvc;

    private Expenses expenses;

    private Expenses insertedExpenses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expenses createEntity() {
        return new Expenses().totalExpense(DEFAULT_TOTAL_EXPENSE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expenses createUpdatedEntity() {
        return new Expenses().totalExpense(UPDATED_TOTAL_EXPENSE);
    }

    @BeforeEach
    public void initTest() {
        expenses = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedExpenses != null) {
            expensesRepository.delete(insertedExpenses);
            insertedExpenses = null;
        }
    }

    @Test
    void createExpenses() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);
        var returnedExpensesDTO = om.readValue(
            restExpensesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(expensesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ExpensesDTO.class
        );

        // Validate the Expenses in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedExpenses = expensesMapper.toEntity(returnedExpensesDTO);
        assertExpensesUpdatableFieldsEquals(returnedExpenses, getPersistedExpenses(returnedExpenses));

        insertedExpenses = returnedExpenses;
    }

    @Test
    void createExpensesWithExistingId() throws Exception {
        // Create the Expenses with an existing ID
        expenses.setId("existing_id");
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpensesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(expensesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllExpenses() throws Exception {
        // Initialize the database
        insertedExpenses = expensesRepository.save(expenses);

        // Get all the expensesList
        restExpensesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expenses.getId())))
            .andExpect(jsonPath("$.[*].totalExpense").value(hasItem(DEFAULT_TOTAL_EXPENSE.intValue())));
    }

    @Test
    void getExpenses() throws Exception {
        // Initialize the database
        insertedExpenses = expensesRepository.save(expenses);

        // Get the expenses
        restExpensesMockMvc
            .perform(get(ENTITY_API_URL_ID, expenses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expenses.getId()))
            .andExpect(jsonPath("$.totalExpense").value(DEFAULT_TOTAL_EXPENSE.intValue()));
    }

    @Test
    void getNonExistingExpenses() throws Exception {
        // Get the expenses
        restExpensesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingExpenses() throws Exception {
        // Initialize the database
        insertedExpenses = expensesRepository.save(expenses);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the expenses
        Expenses updatedExpenses = expensesRepository.findById(expenses.getId()).orElseThrow();
        updatedExpenses.totalExpense(UPDATED_TOTAL_EXPENSE);
        ExpensesDTO expensesDTO = expensesMapper.toDto(updatedExpenses);

        restExpensesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expensesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(expensesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Expenses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedExpensesToMatchAllProperties(updatedExpenses);
    }

    @Test
    void putNonExistingExpenses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expenses.setId(UUID.randomUUID().toString());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expensesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(expensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchExpenses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expenses.setId(UUID.randomUUID().toString());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(expensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamExpenses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expenses.setId(UUID.randomUUID().toString());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(expensesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Expenses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateExpensesWithPatch() throws Exception {
        // Initialize the database
        insertedExpenses = expensesRepository.save(expenses);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the expenses using partial update
        Expenses partialUpdatedExpenses = new Expenses();
        partialUpdatedExpenses.setId(expenses.getId());

        restExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpenses.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExpenses))
            )
            .andExpect(status().isOk());

        // Validate the Expenses in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExpensesUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedExpenses, expenses), getPersistedExpenses(expenses));
    }

    @Test
    void fullUpdateExpensesWithPatch() throws Exception {
        // Initialize the database
        insertedExpenses = expensesRepository.save(expenses);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the expenses using partial update
        Expenses partialUpdatedExpenses = new Expenses();
        partialUpdatedExpenses.setId(expenses.getId());

        partialUpdatedExpenses.totalExpense(UPDATED_TOTAL_EXPENSE);

        restExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpenses.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExpenses))
            )
            .andExpect(status().isOk());

        // Validate the Expenses in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExpensesUpdatableFieldsEquals(partialUpdatedExpenses, getPersistedExpenses(partialUpdatedExpenses));
    }

    @Test
    void patchNonExistingExpenses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expenses.setId(UUID.randomUUID().toString());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, expensesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(expensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchExpenses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expenses.setId(UUID.randomUUID().toString());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(expensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamExpenses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expenses.setId(UUID.randomUUID().toString());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(expensesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Expenses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteExpenses() throws Exception {
        // Initialize the database
        insertedExpenses = expensesRepository.save(expenses);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the expenses
        restExpensesMockMvc
            .perform(delete(ENTITY_API_URL_ID, expenses.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return expensesRepository.count();
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

    protected Expenses getPersistedExpenses(Expenses expenses) {
        return expensesRepository.findById(expenses.getId()).orElseThrow();
    }

    protected void assertPersistedExpensesToMatchAllProperties(Expenses expectedExpenses) {
        assertExpensesAllPropertiesEquals(expectedExpenses, getPersistedExpenses(expectedExpenses));
    }

    protected void assertPersistedExpensesToMatchUpdatableProperties(Expenses expectedExpenses) {
        assertExpensesAllUpdatablePropertiesEquals(expectedExpenses, getPersistedExpenses(expectedExpenses));
    }
}
