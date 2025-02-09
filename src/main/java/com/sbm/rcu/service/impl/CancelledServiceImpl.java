package com.sbm.rcu.service.impl;

import com.sbm.rcu.domain.Cancelled;
import com.sbm.rcu.repository.CancelledRepository;
import com.sbm.rcu.service.CancelledService;
import com.sbm.rcu.service.dto.CancelledDTO;
import com.sbm.rcu.service.mapper.CancelledMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.Cancelled}.
 */
@Service
public class CancelledServiceImpl implements CancelledService {

    private static final Logger LOG = LoggerFactory.getLogger(CancelledServiceImpl.class);

    private final CancelledRepository cancelledRepository;

    private final CancelledMapper cancelledMapper;

    public CancelledServiceImpl(CancelledRepository cancelledRepository, CancelledMapper cancelledMapper) {
        this.cancelledRepository = cancelledRepository;
        this.cancelledMapper = cancelledMapper;
    }

    @Override
    public CancelledDTO save(CancelledDTO cancelledDTO) {
        LOG.debug("Request to save Cancelled : {}", cancelledDTO);
        Cancelled cancelled = cancelledMapper.toEntity(cancelledDTO);
        cancelled = cancelledRepository.save(cancelled);
        return cancelledMapper.toDto(cancelled);
    }

    @Override
    public CancelledDTO update(CancelledDTO cancelledDTO) {
        LOG.debug("Request to update Cancelled : {}", cancelledDTO);
        Cancelled cancelled = cancelledMapper.toEntity(cancelledDTO);
        cancelled = cancelledRepository.save(cancelled);
        return cancelledMapper.toDto(cancelled);
    }

    @Override
    public Optional<CancelledDTO> partialUpdate(CancelledDTO cancelledDTO) {
        LOG.debug("Request to partially update Cancelled : {}", cancelledDTO);

        return cancelledRepository
            .findById(cancelledDTO.getId())
            .map(existingCancelled -> {
                cancelledMapper.partialUpdate(existingCancelled, cancelledDTO);

                return existingCancelled;
            })
            .map(cancelledRepository::save)
            .map(cancelledMapper::toDto);
    }

    @Override
    public List<CancelledDTO> findAll() {
        LOG.debug("Request to get all Cancelleds");
        return cancelledRepository.findAll().stream().map(cancelledMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CancelledDTO> findOne(String id) {
        LOG.debug("Request to get Cancelled : {}", id);
        return cancelledRepository.findById(id).map(cancelledMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Cancelled : {}", id);
        cancelledRepository.deleteById(id);
    }
}
