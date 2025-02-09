package com.sbm.rcu.service;

import com.sbm.rcu.domain.Phone;
import com.sbm.rcu.repository.PhoneRepository;
import com.sbm.rcu.service.dto.PhoneDTO;
import com.sbm.rcu.service.mapper.PhoneMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.Phone}.
 */
@Service
public class PhoneService {

    private static final Logger LOG = LoggerFactory.getLogger(PhoneService.class);

    private final PhoneRepository phoneRepository;

    private final PhoneMapper phoneMapper;

    public PhoneService(PhoneRepository phoneRepository, PhoneMapper phoneMapper) {
        this.phoneRepository = phoneRepository;
        this.phoneMapper = phoneMapper;
    }

    /**
     * Save a phone.
     *
     * @param phoneDTO the entity to save.
     * @return the persisted entity.
     */
    public PhoneDTO save(PhoneDTO phoneDTO) {
        LOG.debug("Request to save Phone : {}", phoneDTO);
        Phone phone = phoneMapper.toEntity(phoneDTO);
        phone = phoneRepository.save(phone);
        return phoneMapper.toDto(phone);
    }

    /**
     * Update a phone.
     *
     * @param phoneDTO the entity to save.
     * @return the persisted entity.
     */
    public PhoneDTO update(PhoneDTO phoneDTO) {
        LOG.debug("Request to update Phone : {}", phoneDTO);
        Phone phone = phoneMapper.toEntity(phoneDTO);
        phone = phoneRepository.save(phone);
        return phoneMapper.toDto(phone);
    }

    /**
     * Partially update a phone.
     *
     * @param phoneDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PhoneDTO> partialUpdate(PhoneDTO phoneDTO) {
        LOG.debug("Request to partially update Phone : {}", phoneDTO);

        return phoneRepository
            .findById(phoneDTO.getId())
            .map(existingPhone -> {
                phoneMapper.partialUpdate(existingPhone, phoneDTO);

                return existingPhone;
            })
            .map(phoneRepository::save)
            .map(phoneMapper::toDto);
    }

    /**
     * Get all the phones.
     *
     * @return the list of entities.
     */
    public List<PhoneDTO> findAll() {
        LOG.debug("Request to get all Phones");
        return phoneRepository.findAll().stream().map(phoneMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one phone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PhoneDTO> findOne(String id) {
        LOG.debug("Request to get Phone : {}", id);
        return phoneRepository.findById(id).map(phoneMapper::toDto);
    }

    /**
     * Delete the phone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete Phone : {}", id);
        phoneRepository.deleteById(id);
    }
}
