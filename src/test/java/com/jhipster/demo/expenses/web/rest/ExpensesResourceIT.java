package com.jhipster.demo.expenses.web.rest;

import static com.jhipster.demo.expenses.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.demo.expenses.IntegrationTest;
import com.jhipster.demo.expenses.domain.Expenses;
import com.jhipster.demo.expenses.repository.ExpensesRepository;
import com.jhipster.demo.expenses.service.dto.ExpensesDTO;
import com.jhipster.demo.expenses.service.mapper.ExpensesMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ExpensesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExpensesResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/expenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private ExpensesMapper expensesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpensesMockMvc;

    private Expenses expenses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expenses createEntity(EntityManager em) {
        Expenses expenses = new Expenses().description(DEFAULT_DESCRIPTION).amount(DEFAULT_AMOUNT).date(DEFAULT_DATE);
        return expenses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expenses createUpdatedEntity(EntityManager em) {
        Expenses expenses = new Expenses().description(UPDATED_DESCRIPTION).amount(UPDATED_AMOUNT).date(UPDATED_DATE);
        return expenses;
    }

    @BeforeEach
    public void initTest() {
        expenses = createEntity(em);
    }

    @Test
    @Transactional
    void createExpenses() throws Exception {
        int databaseSizeBeforeCreate = expensesRepository.findAll().size();
        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);
        restExpensesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isCreated());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeCreate + 1);
        Expenses testExpenses = expensesList.get(expensesList.size() - 1);
        assertThat(testExpenses.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testExpenses.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testExpenses.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createExpensesWithExistingId() throws Exception {
        // Create the Expenses with an existing ID
        expenses.setId(1L);
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        int databaseSizeBeforeCreate = expensesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpensesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = expensesRepository.findAll().size();
        // set the field null
        expenses.setDescription(null);

        // Create the Expenses, which fails.
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        restExpensesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isBadRequest());

        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = expensesRepository.findAll().size();
        // set the field null
        expenses.setAmount(null);

        // Create the Expenses, which fails.
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        restExpensesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isBadRequest());

        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = expensesRepository.findAll().size();
        // set the field null
        expenses.setDate(null);

        // Create the Expenses, which fails.
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        restExpensesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isBadRequest());

        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        // Get all the expensesList
        restExpensesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expenses.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        // Get the expenses
        restExpensesMockMvc
            .perform(get(ENTITY_API_URL_ID, expenses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expenses.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingExpenses() throws Exception {
        // Get the expenses
        restExpensesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();

        // Update the expenses
        Expenses updatedExpenses = expensesRepository.findById(expenses.getId()).get();
        // Disconnect from session so that the updates on updatedExpenses are not directly saved in db
        em.detach(updatedExpenses);
        updatedExpenses.description(UPDATED_DESCRIPTION).amount(UPDATED_AMOUNT).date(UPDATED_DATE);
        ExpensesDTO expensesDTO = expensesMapper.toDto(updatedExpenses);

        restExpensesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expensesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expensesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
        Expenses testExpenses = expensesList.get(expensesList.size() - 1);
        assertThat(testExpenses.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testExpenses.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testExpenses.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingExpenses() throws Exception {
        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();
        expenses.setId(count.incrementAndGet());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expensesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExpenses() throws Exception {
        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();
        expenses.setId(count.incrementAndGet());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExpenses() throws Exception {
        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();
        expenses.setId(count.incrementAndGet());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExpensesWithPatch() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();

        // Update the expenses using partial update
        Expenses partialUpdatedExpenses = new Expenses();
        partialUpdatedExpenses.setId(expenses.getId());

        partialUpdatedExpenses.description(UPDATED_DESCRIPTION);

        restExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpenses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpenses))
            )
            .andExpect(status().isOk());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
        Expenses testExpenses = expensesList.get(expensesList.size() - 1);
        assertThat(testExpenses.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testExpenses.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testExpenses.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateExpensesWithPatch() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();

        // Update the expenses using partial update
        Expenses partialUpdatedExpenses = new Expenses();
        partialUpdatedExpenses.setId(expenses.getId());

        partialUpdatedExpenses.description(UPDATED_DESCRIPTION).amount(UPDATED_AMOUNT).date(UPDATED_DATE);

        restExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpenses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpenses))
            )
            .andExpect(status().isOk());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
        Expenses testExpenses = expensesList.get(expensesList.size() - 1);
        assertThat(testExpenses.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testExpenses.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testExpenses.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingExpenses() throws Exception {
        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();
        expenses.setId(count.incrementAndGet());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, expensesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExpenses() throws Exception {
        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();
        expenses.setId(count.incrementAndGet());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExpenses() throws Exception {
        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();
        expenses.setId(count.incrementAndGet());

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(expensesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        int databaseSizeBeforeDelete = expensesRepository.findAll().size();

        // Delete the expenses
        restExpensesMockMvc
            .perform(delete(ENTITY_API_URL_ID, expenses.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
