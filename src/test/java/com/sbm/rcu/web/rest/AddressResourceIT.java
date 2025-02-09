package com.sbm.rcu.web.rest;

import static com.sbm.rcu.domain.AddressAsserts.*;
import static com.sbm.rcu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.IntegrationTest;
import com.sbm.rcu.domain.Address;
import com.sbm.rcu.repository.AddressRepository;
import com.sbm.rcu.service.dto.AddressDTO;
import com.sbm.rcu.service.mapper.AddressMapper;
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
 * Integration tests for the {@link AddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddressResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_3 = "AAAAAAAAAA";
    private static final String UPDATED_LINE_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    private Address insertedAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity() {
        return new Address()
            .type(DEFAULT_TYPE)
            .zipCode(DEFAULT_ZIP_CODE)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .line1(DEFAULT_LINE_1)
            .line2(DEFAULT_LINE_2)
            .line3(DEFAULT_LINE_3);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity() {
        return new Address()
            .type(UPDATED_TYPE)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2)
            .line3(UPDATED_LINE_3);
    }

    @BeforeEach
    public void initTest() {
        address = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAddress != null) {
            addressRepository.delete(insertedAddress);
            insertedAddress = null;
        }
    }

    @Test
    void createAddress() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        var returnedAddressDTO = om.readValue(
            restAddressMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AddressDTO.class
        );

        // Validate the Address in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAddress = addressMapper.toEntity(returnedAddressDTO);
        assertAddressUpdatableFieldsEquals(returnedAddress, getPersistedAddress(returnedAddress));

        insertedAddress = returnedAddress;
    }

    @Test
    void createAddressWithExistingId() throws Exception {
        // Create the Address with an existing ID
        address.setId("existing_id");
        AddressDTO addressDTO = addressMapper.toDto(address);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAddresses() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.save(address);

        // Get all the addressList
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].line1").value(hasItem(DEFAULT_LINE_1)))
            .andExpect(jsonPath("$.[*].line2").value(hasItem(DEFAULT_LINE_2)))
            .andExpect(jsonPath("$.[*].line3").value(hasItem(DEFAULT_LINE_3)));
    }

    @Test
    void getAddress() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.save(address);

        // Get the address
        restAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.line1").value(DEFAULT_LINE_1))
            .andExpect(jsonPath("$.line2").value(DEFAULT_LINE_2))
            .andExpect(jsonPath("$.line3").value(DEFAULT_LINE_3));
    }

    @Test
    void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingAddress() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.save(address);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).orElseThrow();
        updatedAddress
            .type(UPDATED_TYPE)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2)
            .line3(UPDATED_LINE_3);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAddressToMatchAllProperties(updatedAddress);
    }

    @Test
    void putNonExistingAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(UUID.randomUUID().toString());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(UUID.randomUUID().toString());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(UUID.randomUUID().toString());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.save(address);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress.city(UPDATED_CITY);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAddressUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAddress, address), getPersistedAddress(address));
    }

    @Test
    void fullUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.save(address);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .type(UPDATED_TYPE)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2)
            .line3(UPDATED_LINE_3);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAddressUpdatableFieldsEquals(partialUpdatedAddress, getPersistedAddress(partialUpdatedAddress));
    }

    @Test
    void patchNonExistingAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(UUID.randomUUID().toString());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(UUID.randomUUID().toString());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(UUID.randomUUID().toString());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAddress() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.save(address);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the address
        restAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, address.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return addressRepository.count();
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

    protected Address getPersistedAddress(Address address) {
        return addressRepository.findById(address.getId()).orElseThrow();
    }

    protected void assertPersistedAddressToMatchAllProperties(Address expectedAddress) {
        assertAddressAllPropertiesEquals(expectedAddress, getPersistedAddress(expectedAddress));
    }

    protected void assertPersistedAddressToMatchUpdatableProperties(Address expectedAddress) {
        assertAddressAllUpdatablePropertiesEquals(expectedAddress, getPersistedAddress(expectedAddress));
    }
}
