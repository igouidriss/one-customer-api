package com.sbm.rcu.service.impl;

import com.sbm.rcu.domain.RestorationReservation;
import com.sbm.rcu.repository.RestorationReservationRepository;
import com.sbm.rcu.service.RestorationReservationService;
import com.sbm.rcu.service.dto.RestorationReservationDTO;
import com.sbm.rcu.service.mapper.RestorationReservationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.RestorationReservation}.
 */
@Service
public class RestorationReservationServiceImpl implements RestorationReservationService {

    private static final Logger LOG = LoggerFactory.getLogger(RestorationReservationServiceImpl.class);

    private final RestorationReservationRepository restorationReservationRepository;

    private final RestorationReservationMapper restorationReservationMapper;

    public RestorationReservationServiceImpl(
        RestorationReservationRepository restorationReservationRepository,
        RestorationReservationMapper restorationReservationMapper
    ) {
        this.restorationReservationRepository = restorationReservationRepository;
        this.restorationReservationMapper = restorationReservationMapper;
    }

    @Override
    public RestorationReservationDTO save(RestorationReservationDTO restorationReservationDTO) {
        LOG.debug("Request to save RestorationReservation : {}", restorationReservationDTO);
        RestorationReservation restorationReservation = restorationReservationMapper.toEntity(restorationReservationDTO);
        restorationReservation = restorationReservationRepository.save(restorationReservation);
        return restorationReservationMapper.toDto(restorationReservation);
    }

    @Override
    public RestorationReservationDTO update(RestorationReservationDTO restorationReservationDTO) {
        LOG.debug("Request to update RestorationReservation : {}", restorationReservationDTO);
        RestorationReservation restorationReservation = restorationReservationMapper.toEntity(restorationReservationDTO);
        restorationReservation = restorationReservationRepository.save(restorationReservation);
        return restorationReservationMapper.toDto(restorationReservation);
    }

    @Override
    public Optional<RestorationReservationDTO> partialUpdate(RestorationReservationDTO restorationReservationDTO) {
        LOG.debug("Request to partially update RestorationReservation : {}", restorationReservationDTO);

        return restorationReservationRepository
            .findById(restorationReservationDTO.getId())
            .map(existingRestorationReservation -> {
                restorationReservationMapper.partialUpdate(existingRestorationReservation, restorationReservationDTO);

                return existingRestorationReservation;
            })
            .map(restorationReservationRepository::save)
            .map(restorationReservationMapper::toDto);
    }

    @Override
    public Page<RestorationReservationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all RestorationReservations");
        return restorationReservationRepository.findAll(pageable).map(restorationReservationMapper::toDto);
    }

    @Override
    public Optional<RestorationReservationDTO> findOne(String id) {
        LOG.debug("Request to get RestorationReservation : {}", id);
        return restorationReservationRepository.findById(id).map(restorationReservationMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete RestorationReservation : {}", id);
        restorationReservationRepository.deleteById(id);
    }
}
