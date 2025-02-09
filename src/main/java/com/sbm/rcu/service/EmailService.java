package com.sbm.rcu.service;

import com.sbm.rcu.domain.Email;
import com.sbm.rcu.repository.EmailRepository;
import com.sbm.rcu.service.dto.EmailDTO;
import com.sbm.rcu.service.mapper.EmailMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.sbm.rcu.domain.Email}.
 */
@Service
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    private final EmailRepository emailRepository;

    private final EmailMapper emailMapper;

    public EmailService(EmailRepository emailRepository, EmailMapper emailMapper) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    /**
     * Save a email.
     *
     * @param emailDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailDTO save(EmailDTO emailDTO) {
        LOG.debug("Request to save Email : {}", emailDTO);
        Email email = emailMapper.toEntity(emailDTO);
        email = emailRepository.save(email);
        return emailMapper.toDto(email);
    }

    /**
     * Update a email.
     *
     * @param emailDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailDTO update(EmailDTO emailDTO) {
        LOG.debug("Request to update Email : {}", emailDTO);
        Email email = emailMapper.toEntity(emailDTO);
        email = emailRepository.save(email);
        return emailMapper.toDto(email);
    }

    /**
     * Partially update a email.
     *
     * @param emailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmailDTO> partialUpdate(EmailDTO emailDTO) {
        LOG.debug("Request to partially update Email : {}", emailDTO);

        return emailRepository
            .findById(emailDTO.getId())
            .map(existingEmail -> {
                emailMapper.partialUpdate(existingEmail, emailDTO);

                return existingEmail;
            })
            .map(emailRepository::save)
            .map(emailMapper::toDto);
    }

    /**
     * Get all the emails.
     *
     * @return the list of entities.
     */
    public List<EmailDTO> findAll() {
        LOG.debug("Request to get all Emails");
        return emailRepository.findAll().stream().map(emailMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one email by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<EmailDTO> findOne(String id) {
        LOG.debug("Request to get Email : {}", id);
        return emailRepository.findById(id).map(emailMapper::toDto);
    }

    /**
     * Delete the email by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete Email : {}", id);
        emailRepository.deleteById(id);
    }
}
