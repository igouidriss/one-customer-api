package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.ExpenseAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.Expense;
import com.sbm.rcu.repository.ExpenseRepository;
import com.sbm.rcu.service.dto.ExpenseDTO;
import com.sbm.rcu.service.mapper.ExpenseMapper;
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
 * Integration tests for the {@link ExpenseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExpenseResourceIT {

    private static final String DEFAULT_EXPENSE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EXPENSE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final Long DEFAULT_DEPOSIT_AMOUNT = 1L;
    private static final Long UPDATED_DEPOSIT_AMOUNT = 2L;

    private static final Long DEFAULT_TOTAL_AMOUNT = 1L;
    private static final Long UPDATED_TOTAL_AMOUNT = 2L;

    private static final String DEFAULT_SHIFT = "AAAAAAAAAA";
    private static final String UPDATED_SHIFT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final String DEFAULT_RESTAURANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESTAURANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESTAURANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RESTAURANT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/expenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseMapper expenseMapper;

    @Autowired
    private MockMvc restExpenseMockMvc;

    private Expense expense;

    private Expense insertedExpense;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expense createEntity() {
        return new Expense()
            .expenseType(DEFAULT_EXPENSE_TYPE)
            .amount(DEFAULT_AMOUNT)
            .depositAmount(DEFAULT_DEPOSIT_AMOUNT)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .shift(DEFAULT_SHIFT)
            .date(DEFAULT_DATE)
            .arrivalDate(DEFAULT_ARRIVAL_DATE)
            .leaveDate(DEFAULT_LEAVE_DATE)
            .guestCount(DEFAULT_GUEST_COUNT)
            .hotelName(DEFAULT_HOTEL_NAME)
            .hotelId(DEFAULT_HOTEL_ID)
            .restaurantName(DEFAULT_RESTAURANT_NAME)
            .restaurantId(DEFAULT_RESTAURANT_ID)
            .clientId(DEFAULT_CLIENT_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expense createUpdatedEntity() {
        return new Expense()
            .expenseType(UPDATED_EXPENSE_TYPE)
            .amount(UPDATED_AMOUNT)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .shift(UPDATED_SHIFT)
            .date(UPDATED_DATE)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .leaveDate(UPDATED_LEAVE_DATE)
            .guestCount(UPDATED_GUEST_COUNT)
            .hotelName(UPDATED_HOTEL_NAME)
            .hotelId(UPDATED_HOTEL_ID)
            .restaurantName(UPDATED_RESTAURANT_NAME)
            .restaurantId(UPDATED_RESTAURANT_ID)
            .clientId(UPDATED_CLIENT_ID);
    }

    @BeforeEach
    public void initTest() {
        expense = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedExpense != null) {
            expenseRepository.delete(insertedExpense);
            insertedExpense = null;
        }
    }

    @Test
    void createExpense() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);
        var returnedExpenseDTO = om.readValue(
            restExpenseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(expenseDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ExpenseDTO.class
        );

        // Validate the Expense in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedExpense = expenseMapper.toEntity(returnedExpenseDTO);
        assertExpenseUpdatableFieldsEquals(returnedExpense, getPersistedExpense(returnedExpense));

        insertedExpense = returnedExpense;
    }

    @Test
    void createExpenseWithExistingId() throws Exception {
        // Create the Expense with an existing ID
        expense.setId("existing_id");
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(expenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllExpenses() throws Exception {
        // Initialize the database
        insertedExpense = expenseRepository.save(expense);

        // Get all the expenseList
        restExpenseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expense.getId())))
            .andExpect(jsonPath("$.[*].expenseType").value(hasItem(DEFAULT_EXPENSE_TYPE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].depositAmount").value(hasItem(DEFAULT_DEPOSIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].shift").value(hasItem(DEFAULT_SHIFT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].arrivalDate").value(hasItem(DEFAULT_ARRIVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].leaveDate").value(hasItem(DEFAULT_LEAVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].guestCount").value(hasItem(DEFAULT_GUEST_COUNT)))
            .andExpect(jsonPath("$.[*].hotelName").value(hasItem(DEFAULT_HOTEL_NAME)))
            .andExpect(jsonPath("$.[*].hotelId").value(hasItem(DEFAULT_HOTEL_ID)))
            .andExpect(jsonPath("$.[*].restaurantName").value(hasItem(DEFAULT_RESTAURANT_NAME)))
            .andExpect(jsonPath("$.[*].restaurantId").value(hasItem(DEFAULT_RESTAURANT_ID)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)));
    }

    @Test
    void getExpense() throws Exception {
        // Initialize the database
        insertedExpense = expenseRepository.save(expense);

        // Get the expense
        restExpenseMockMvc
            .perform(get(ENTITY_API_URL_ID, expense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expense.getId()))
            .andExpect(jsonPath("$.expenseType").value(DEFAULT_EXPENSE_TYPE))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.depositAmount").value(DEFAULT_DEPOSIT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.shift").value(DEFAULT_SHIFT))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.arrivalDate").value(DEFAULT_ARRIVAL_DATE.toString()))
            .andExpect(jsonPath("$.leaveDate").value(DEFAULT_LEAVE_DATE.toString()))
            .andExpect(jsonPath("$.guestCount").value(DEFAULT_GUEST_COUNT))
            .andExpect(jsonPath("$.hotelName").value(DEFAULT_HOTEL_NAME))
            .andExpect(jsonPath("$.hotelId").value(DEFAULT_HOTEL_ID))
            .andExpect(jsonPath("$.restaurantName").value(DEFAULT_RESTAURANT_NAME))
            .andExpect(jsonPath("$.restaurantId").value(DEFAULT_RESTAURANT_ID))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID));
    }

    @Test
    void getNonExistingExpense() throws Exception {
        // Get the expense
        restExpenseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingExpense() throws Exception {
        // Initialize the database
        insertedExpense = expenseRepository.save(expense);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the expense
        Expense updatedExpense = expenseRepository.findById(expense.getId()).orElseThrow();
        updatedExpense
            .expenseType(UPDATED_EXPENSE_TYPE)
            .amount(UPDATED_AMOUNT)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .shift(UPDATED_SHIFT)
            .date(UPDATED_DATE)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .leaveDate(UPDATED_LEAVE_DATE)
            .guestCount(UPDATED_GUEST_COUNT)
            .hotelName(UPDATED_HOTEL_NAME)
            .hotelId(UPDATED_HOTEL_ID)
            .restaurantName(UPDATED_RESTAURANT_NAME)
            .restaurantId(UPDATED_RESTAURANT_ID)
            .clientId(UPDATED_CLIENT_ID);
        ExpenseDTO expenseDTO = expenseMapper.toDto(updatedExpense);

        restExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expenseDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(expenseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Expense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedExpenseToMatchAllProperties(updatedExpense);
    }

    @Test
    void putNonExistingExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expense.setId(UUID.randomUUID().toString());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expenseDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(expenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expense.setId(UUID.randomUUID().toString());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(expenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expense.setId(UUID.randomUUID().toString());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(expenseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Expense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateExpenseWithPatch() throws Exception {
        // Initialize the database
        insertedExpense = expenseRepository.save(expense);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the expense using partial update
        Expense partialUpdatedExpense = new Expense();
        partialUpdatedExpense.setId(expense.getId());

        partialUpdatedExpense
            .amount(UPDATED_AMOUNT)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .date(UPDATED_DATE)
            .leaveDate(UPDATED_LEAVE_DATE)
            .guestCount(UPDATED_GUEST_COUNT)
            .hotelName(UPDATED_HOTEL_NAME)
            .hotelId(UPDATED_HOTEL_ID)
            .restaurantId(UPDATED_RESTAURANT_ID)
            .clientId(UPDATED_CLIENT_ID);

        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpense.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExpense))
            )
            .andExpect(status().isOk());

        // Validate the Expense in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExpenseUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedExpense, expense), getPersistedExpense(expense));
    }

    @Test
    void fullUpdateExpenseWithPatch() throws Exception {
        // Initialize the database
        insertedExpense = expenseRepository.save(expense);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the expense using partial update
        Expense partialUpdatedExpense = new Expense();
        partialUpdatedExpense.setId(expense.getId());

        partialUpdatedExpense
            .expenseType(UPDATED_EXPENSE_TYPE)
            .amount(UPDATED_AMOUNT)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .shift(UPDATED_SHIFT)
            .date(UPDATED_DATE)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .leaveDate(UPDATED_LEAVE_DATE)
            .guestCount(UPDATED_GUEST_COUNT)
            .hotelName(UPDATED_HOTEL_NAME)
            .hotelId(UPDATED_HOTEL_ID)
            .restaurantName(UPDATED_RESTAURANT_NAME)
            .restaurantId(UPDATED_RESTAURANT_ID)
            .clientId(UPDATED_CLIENT_ID);

        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpense.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExpense))
            )
            .andExpect(status().isOk());

        // Validate the Expense in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExpenseUpdatableFieldsEquals(partialUpdatedExpense, getPersistedExpense(partialUpdatedExpense));
    }

    @Test
    void patchNonExistingExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expense.setId(UUID.randomUUID().toString());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, expenseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(expenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expense.setId(UUID.randomUUID().toString());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(expenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        expense.setId(UUID.randomUUID().toString());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(expenseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Expense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteExpense() throws Exception {
        // Initialize the database
        insertedExpense = expenseRepository.save(expense);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the expense
        restExpenseMockMvc
            .perform(delete(ENTITY_API_URL_ID, expense.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return expenseRepository.count();
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

    protected Expense getPersistedExpense(Expense expense) {
        return expenseRepository.findById(expense.getId()).orElseThrow();
    }

    protected void assertPersistedExpenseToMatchAllProperties(Expense expectedExpense) {
        assertExpenseAllPropertiesEquals(expectedExpense, getPersistedExpense(expectedExpense));
    }

    protected void assertPersistedExpenseToMatchUpdatableProperties(Expense expectedExpense) {
        assertExpenseAllUpdatablePropertiesEquals(expectedExpense, getPersistedExpense(expectedExpense));
    }
}
