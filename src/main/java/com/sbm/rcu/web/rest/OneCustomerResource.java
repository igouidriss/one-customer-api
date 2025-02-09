package com.sbm.rcu.web.rest;

import com.sbm.rcu.domain.OneCustomer;
import com.sbm.rcu.repository.OneCustomerRepository;
import com.sbm.rcu.service.OneCustomerService;
import com.sbm.rcu.service.dto.OneCustomerDTO;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.sbm.rcu.domain.OneCustomer}.
 */
@RestController
@RequestMapping("/api/one-customers")
public class OneCustomerResource {

    private static final Logger LOG = LoggerFactory.getLogger(OneCustomerResource.class);

    private static final String ENTITY_NAME = "oneCustomer";

    private final MongoTemplate mongoTemplate;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OneCustomerService oneCustomerService;

    private final OneCustomerRepository oneCustomerRepository;

    public OneCustomerResource(
        MongoTemplate mongoTemplate,
        OneCustomerService oneCustomerService,
        OneCustomerRepository oneCustomerRepository
    ) {
        this.mongoTemplate = mongoTemplate;
        this.oneCustomerService = oneCustomerService;
        this.oneCustomerRepository = oneCustomerRepository;
    }

    //    @GetMapping("")
    public ResponseEntity<List<OneCustomerDTO>> getAllOneCustomers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of OneCustomers");
        Page<OneCustomerDTO> page = oneCustomerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/advanced-search")
    public List<OneCustomer> advancedSearch(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String city
    ) {
        Criteria criteria = new Criteria();
        List<Criteria> subCriteria = new ArrayList<>();

        if (name != null) {
            subCriteria.add(Criteria.where("firstName").regex(name, "i"));
        }
        if (email != null) {
            subCriteria.add(Criteria.where("email").regex(email, "i"));
        }
        if (city != null) {
            subCriteria.add(Criteria.where("address.city").regex(city, "i"));
        }
        if (!subCriteria.isEmpty()) {
            criteria = new Criteria().andOperator(subCriteria.toArray(new Criteria[0]));
        }

        Query query = new Query(criteria);
        return mongoTemplate.find(query, OneCustomer.class);
    }
}
