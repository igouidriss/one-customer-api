package com.sbm.rcu.service.impl;

import com.sbm.rcu.domain.SourceReference;
import com.sbm.rcu.repository.SourceReferenceRepository;
import com.sbm.rcu.service.SourceReferenceService;
import com.sbm.rcu.service.dto.SourceReferenceDTO;
import com.sbm.rcu.service.mapper.SourceReferenceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.SourceReference}.
 */
@Service
public class SourceReferenceServiceImpl implements SourceReferenceService {

    private static final Logger LOG = LoggerFactory.getLogger(SourceReferenceServiceImpl.class);

    private final SourceReferenceRepository sourceReferenceRepository;

    private final SourceReferenceMapper sourceReferenceMapper;

    public SourceReferenceServiceImpl(SourceReferenceRepository sourceReferenceRepository, SourceReferenceMapper sourceReferenceMapper) {
        this.sourceReferenceRepository = sourceReferenceRepository;
        this.sourceReferenceMapper = sourceReferenceMapper;
    }

    @Override
    public SourceReferenceDTO save(SourceReferenceDTO sourceReferenceDTO) {
        LOG.debug("Request to save SourceReference : {}", sourceReferenceDTO);
        SourceReference sourceReference = sourceReferenceMapper.toEntity(sourceReferenceDTO);
        sourceReference = sourceReferenceRepository.save(sourceReference);
        return sourceReferenceMapper.toDto(sourceReference);
    }

    @Override
    public SourceReferenceDTO update(SourceReferenceDTO sourceReferenceDTO) {
        LOG.debug("Request to update SourceReference : {}", sourceReferenceDTO);
        SourceReference sourceReference = sourceReferenceMapper.toEntity(sourceReferenceDTO);
        sourceReference = sourceReferenceRepository.save(sourceReference);
        return sourceReferenceMapper.toDto(sourceReference);
    }

    @Override
    public Optional<SourceReferenceDTO> partialUpdate(SourceReferenceDTO sourceReferenceDTO) {
        LOG.debug("Request to partially update SourceReference : {}", sourceReferenceDTO);

        return sourceReferenceRepository
            .findById(sourceReferenceDTO.getId())
            .map(existingSourceReference -> {
                sourceReferenceMapper.partialUpdate(existingSourceReference, sourceReferenceDTO);

                return existingSourceReference;
            })
            .map(sourceReferenceRepository::save)
            .map(sourceReferenceMapper::toDto);
    }

    @Override
    public List<SourceReferenceDTO> findAll() {
        LOG.debug("Request to get all SourceReferences");
        return sourceReferenceRepository
            .findAll()
            .stream()
            .map(sourceReferenceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<SourceReferenceDTO> findOne(String id) {
        LOG.debug("Request to get SourceReference : {}", id);
        return sourceReferenceRepository.findById(id).map(sourceReferenceMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete SourceReference : {}", id);
        sourceReferenceRepository.deleteById(id);
    }
}
