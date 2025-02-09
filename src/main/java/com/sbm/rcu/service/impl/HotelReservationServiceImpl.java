package com.sbm.rcu.service.impl;

import com.sbm.rcu.domain.HotelReservation;
import com.sbm.rcu.repository.HotelReservationRepository;
import com.sbm.rcu.service.HotelReservationService;
import com.sbm.rcu.service.dto.HotelReservationDTO;
import com.sbm.rcu.service.mapper.HotelReservationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.HotelReservation}.
 */
@Service
public class HotelReservationServiceImpl implements HotelReservationService {

    private static final Logger LOG = LoggerFactory.getLogger(HotelReservationServiceImpl.class);

    private final HotelReservationRepository hotelReservationRepository;

    private final HotelReservationMapper hotelReservationMapper;

    public HotelReservationServiceImpl(
        HotelReservationRepository hotelReservationRepository,
        HotelReservationMapper hotelReservationMapper
    ) {
        this.hotelReservationRepository = hotelReservationRepository;
        this.hotelReservationMapper = hotelReservationMapper;
    }

    @Override
    public HotelReservationDTO save(HotelReservationDTO hotelReservationDTO) {
        LOG.debug("Request to save HotelReservation : {}", hotelReservationDTO);
        HotelReservation hotelReservation = hotelReservationMapper.toEntity(hotelReservationDTO);
        hotelReservation = hotelReservationRepository.save(hotelReservation);
        return hotelReservationMapper.toDto(hotelReservation);
    }

    @Override
    public HotelReservationDTO update(HotelReservationDTO hotelReservationDTO) {
        LOG.debug("Request to update HotelReservation : {}", hotelReservationDTO);
        HotelReservation hotelReservation = hotelReservationMapper.toEntity(hotelReservationDTO);
        hotelReservation = hotelReservationRepository.save(hotelReservation);
        return hotelReservationMapper.toDto(hotelReservation);
    }

    @Override
    public Optional<HotelReservationDTO> partialUpdate(HotelReservationDTO hotelReservationDTO) {
        LOG.debug("Request to partially update HotelReservation : {}", hotelReservationDTO);

        return hotelReservationRepository
            .findById(hotelReservationDTO.getId())
            .map(existingHotelReservation -> {
                hotelReservationMapper.partialUpdate(existingHotelReservation, hotelReservationDTO);

                return existingHotelReservation;
            })
            .map(hotelReservationRepository::save)
            .map(hotelReservationMapper::toDto);
    }

    @Override
    public Page<HotelReservationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all HotelReservations");
        return hotelReservationRepository.findAll(pageable).map(hotelReservationMapper::toDto);
    }

    @Override
    public Optional<HotelReservationDTO> findOne(String id) {
        LOG.debug("Request to get HotelReservation : {}", id);
        return hotelReservationRepository.findById(id).map(hotelReservationMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete HotelReservation : {}", id);
        hotelReservationRepository.deleteById(id);
    }
}
