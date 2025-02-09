package com.sbm.rcu.service;

import com.sbm.rcu.domain.Metadata;
import com.sbm.rcu.repository.MetadataRepository;
import com.sbm.rcu.service.dto.MetadataDTO;
import com.sbm.rcu.service.mapper.MetadataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.Metadata}.
 */
@Service
public class MetadataService {

    private static final Logger LOG = LoggerFactory.getLogger(MetadataService.class);

    private final MetadataRepository metadataRepository;

    private final MetadataMapper metadataMapper;

    public MetadataService(MetadataRepository metadataRepository, MetadataMapper metadataMapper) {
        this.metadataRepository = metadataRepository;
        this.metadataMapper = metadataMapper;
    }

    /**
     * Save a metadata.
     *
     * @param metadataDTO the entity to save.
     * @return the persisted entity.
     */
    public MetadataDTO save(MetadataDTO metadataDTO) {
        LOG.debug("Request to save Metadata : {}", metadataDTO);
        Metadata metadata = metadataMapper.toEntity(metadataDTO);
        metadata = metadataRepository.save(metadata);
        return metadataMapper.toDto(metadata);
    }

    /**
     * Update a metadata.
     *
     * @param metadataDTO the entity to save.
     * @return the persisted entity.
     */
    public MetadataDTO update(MetadataDTO metadataDTO) {
        LOG.debug("Request to update Metadata : {}", metadataDTO);
        Metadata metadata = metadataMapper.toEntity(metadataDTO);
        metadata = metadataRepository.save(metadata);
        return metadataMapper.toDto(metadata);
    }

    /**
     * Partially update a metadata.
     *
     * @param metadataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MetadataDTO> partialUpdate(MetadataDTO metadataDTO) {
        LOG.debug("Request to partially update Metadata : {}", metadataDTO);

        return metadataRepository
            .findById(metadataDTO.getId())
            .map(existingMetadata -> {
                metadataMapper.partialUpdate(existingMetadata, metadataDTO);

                return existingMetadata;
            })
            .map(metadataRepository::save)
            .map(metadataMapper::toDto);
    }

    /**
     * Get all the metadata.
     *
     * @return the list of entities.
     */
    public List<MetadataDTO> findAll() {
        LOG.debug("Request to get all Metadata");
        return metadataRepository.findAll().stream().map(metadataMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one metadata by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<MetadataDTO> findOne(String id) {
        LOG.debug("Request to get Metadata : {}", id);
        return metadataRepository.findById(id).map(metadataMapper::toDto);
    }

    /**
     * Delete the metadata by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete Metadata : {}", id);
        metadataRepository.deleteById(id);
    }
}
