package com.sbm.rcu.service.impl;

import com.sbm.rcu.domain.OneCustomer;
import com.sbm.rcu.repository.OneCustomerRepository;
import com.sbm.rcu.service.OneCustomerService;
import com.sbm.rcu.service.dto.OneCustomerDTO;
import com.sbm.rcu.service.mapper.OneCustomerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.OneCustomer}.
 */
@Service
public class OneCustomerServiceImpl implements OneCustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(OneCustomerServiceImpl.class);

    private final OneCustomerRepository oneCustomerRepository;

    private final OneCustomerMapper oneCustomerMapper;

    public OneCustomerServiceImpl(OneCustomerRepository oneCustomerRepository, OneCustomerMapper oneCustomerMapper) {
        this.oneCustomerRepository = oneCustomerRepository;
        this.oneCustomerMapper = oneCustomerMapper;
    }

    @Override
    public OneCustomerDTO save(OneCustomerDTO oneCustomerDTO) {
        LOG.debug("Request to save OneCustomer : {}", oneCustomerDTO);
        OneCustomer oneCustomer = oneCustomerMapper.toEntity(oneCustomerDTO);
        oneCustomer = oneCustomerRepository.save(oneCustomer);
        return oneCustomerMapper.toDto(oneCustomer);
    }

    @Override
    public OneCustomerDTO update(OneCustomerDTO oneCustomerDTO) {
        LOG.debug("Request to update OneCustomer : {}", oneCustomerDTO);
        OneCustomer oneCustomer = oneCustomerMapper.toEntity(oneCustomerDTO);
        oneCustomer = oneCustomerRepository.save(oneCustomer);
        return oneCustomerMapper.toDto(oneCustomer);
    }

    @Override
    public Optional<OneCustomerDTO> partialUpdate(OneCustomerDTO oneCustomerDTO) {
        LOG.debug("Request to partially update OneCustomer : {}", oneCustomerDTO);

        return oneCustomerRepository
            .findById(oneCustomerDTO.getId())
            .map(existingOneCustomer -> {
                oneCustomerMapper.partialUpdate(existingOneCustomer, oneCustomerDTO);

                return existingOneCustomer;
            })
            .map(oneCustomerRepository::save)
            .map(oneCustomerMapper::toDto);
    }

    @Override
    public Page<OneCustomerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all OneCustomers");
        return oneCustomerRepository.findAll(pageable).map(oneCustomerMapper::toDto);
    }

    @Override
    public Optional<OneCustomerDTO> findOne(String id) {
        LOG.debug("Request to get OneCustomer : {}", id);
        return oneCustomerRepository.findById(id).map(oneCustomerMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete OneCustomer : {}", id);
        oneCustomerRepository.deleteById(id);
    }
}
