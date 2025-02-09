package com.sbm.rcu.service.impl;

import com.sbm.rcu.domain.Payload;
import com.sbm.rcu.repository.PayloadRepository;
import com.sbm.rcu.service.PayloadService;
import com.sbm.rcu.service.dto.PayloadDTO;
import com.sbm.rcu.service.mapper.PayloadMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.Payload}.
 */
@Service
public class PayloadServiceImpl implements PayloadService {

    private static final Logger LOG = LoggerFactory.getLogger(PayloadServiceImpl.class);

    private final PayloadRepository payloadRepository;

    private final PayloadMapper payloadMapper;

    public PayloadServiceImpl(PayloadRepository payloadRepository, PayloadMapper payloadMapper) {
        this.payloadRepository = payloadRepository;
        this.payloadMapper = payloadMapper;
    }

    @Override
    public PayloadDTO save(PayloadDTO payloadDTO) {
        LOG.debug("Request to save Payload : {}", payloadDTO);
        Payload payload = payloadMapper.toEntity(payloadDTO);
        payload = payloadRepository.save(payload);
        return payloadMapper.toDto(payload);
    }

    @Override
    public PayloadDTO update(PayloadDTO payloadDTO) {
        LOG.debug("Request to update Payload : {}", payloadDTO);
        Payload payload = payloadMapper.toEntity(payloadDTO);
        payload = payloadRepository.save(payload);
        return payloadMapper.toDto(payload);
    }

    @Override
    public Optional<PayloadDTO> partialUpdate(PayloadDTO payloadDTO) {
        LOG.debug("Request to partially update Payload : {}", payloadDTO);

        return payloadRepository
            .findById(payloadDTO.getId())
            .map(existingPayload -> {
                payloadMapper.partialUpdate(existingPayload, payloadDTO);

                return existingPayload;
            })
            .map(payloadRepository::save)
            .map(payloadMapper::toDto);
    }

    @Override
    public List<PayloadDTO> findAll() {
        LOG.debug("Request to get all Payloads");
        return payloadRepository.findAll().stream().map(payloadMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<PayloadDTO> findOne(String id) {
        LOG.debug("Request to get Payload : {}", id);
        return payloadRepository.findById(id).map(payloadMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Payload : {}", id);
        payloadRepository.deleteById(id);
    }
}
