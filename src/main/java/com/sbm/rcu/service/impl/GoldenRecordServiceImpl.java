package com.sbm.rcu.service.impl;

import com.sbm.rcu.domain.GoldenRecord;
import com.sbm.rcu.repository.GoldenRecordRepository;
import com.sbm.rcu.service.GoldenRecordService;
import com.sbm.rcu.service.dto.GoldenRecordDTO;
import com.sbm.rcu.service.mapper.GoldenRecordMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.GoldenRecord}.
 */
@Service
public class GoldenRecordServiceImpl implements GoldenRecordService {

    private static final Logger LOG = LoggerFactory.getLogger(GoldenRecordServiceImpl.class);

    private final GoldenRecordRepository goldenRecordRepository;

    private final GoldenRecordMapper goldenRecordMapper;

    public GoldenRecordServiceImpl(GoldenRecordRepository goldenRecordRepository, GoldenRecordMapper goldenRecordMapper) {
        this.goldenRecordRepository = goldenRecordRepository;
        this.goldenRecordMapper = goldenRecordMapper;
    }

    @Override
    public GoldenRecordDTO save(GoldenRecordDTO goldenRecordDTO) {
        LOG.debug("Request to save GoldenRecord : {}", goldenRecordDTO);
        GoldenRecord goldenRecord = goldenRecordMapper.toEntity(goldenRecordDTO);
        goldenRecord = goldenRecordRepository.save(goldenRecord);
        return goldenRecordMapper.toDto(goldenRecord);
    }

    @Override
    public GoldenRecordDTO update(GoldenRecordDTO goldenRecordDTO) {
        LOG.debug("Request to update GoldenRecord : {}", goldenRecordDTO);
        GoldenRecord goldenRecord = goldenRecordMapper.toEntity(goldenRecordDTO);
        goldenRecord = goldenRecordRepository.save(goldenRecord);
        return goldenRecordMapper.toDto(goldenRecord);
    }

    @Override
    public Optional<GoldenRecordDTO> partialUpdate(GoldenRecordDTO goldenRecordDTO) {
        LOG.debug("Request to partially update GoldenRecord : {}", goldenRecordDTO);

        return goldenRecordRepository
            .findById(goldenRecordDTO.getId())
            .map(existingGoldenRecord -> {
                goldenRecordMapper.partialUpdate(existingGoldenRecord, goldenRecordDTO);

                return existingGoldenRecord;
            })
            .map(goldenRecordRepository::save)
            .map(goldenRecordMapper::toDto);
    }

    @Override
    public List<GoldenRecordDTO> findAll() {
        LOG.debug("Request to get all GoldenRecords");
        return goldenRecordRepository.findAll().stream().map(goldenRecordMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<GoldenRecordDTO> findOne(String id) {
        LOG.debug("Request to get GoldenRecord : {}", id);
        return goldenRecordRepository.findById(id).map(goldenRecordMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete GoldenRecord : {}", id);
        goldenRecordRepository.deleteById(id);
    }
}
