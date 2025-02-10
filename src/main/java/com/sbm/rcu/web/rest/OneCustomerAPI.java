package com.sbm.rcu.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.rcu.domain.ExposedOneCustomer;
import com.sbm.rcu.service.mapper.ExposedOneCustomerMapper;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.sbm.rcu.domain.OneCustomer}.
 */
@RestController
@RequestMapping("/api")
public class OneCustomerAPI {

    private static final Logger LOG = LoggerFactory.getLogger(OneCustomerAPI.class);

    private final MongoTemplate mongoTemplate;

    @Autowired
    public OneCustomerAPI(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/one-customers/search-advanced")
    public List<ExposedOneCustomer> advancedSearch(
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String birthDate,
        @RequestParam(required = false) String phone,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) Boolean isVip,
        @RequestParam(required = false) String hotel,
        @RequestParam(required = false) String restaurant
    ) {
        LOG.debug("REST request to do an advanced search on OneCustomer");

        // On va accumuler nos critères dans une liste
        List<Criteria> subCriteria = new ArrayList<>();

        // lastName -> golden_record.payload.lastName
        if (lastName != null && !lastName.isEmpty()) {
            subCriteria.add(Criteria.where("golden_record.payload.lastName").regex(lastName, "i")); // recherche partielle insensible à la casse
        }

        // firstName -> golden_record.payload.firstName
        if (firstName != null && !firstName.isEmpty()) {
            subCriteria.add(Criteria.where("golden_record.payload.firstName").regex(firstName, "i"));
        }

        // birthDate -> golden_record.payload.birthDate
        // Ici, on peut décider de faire un match exact (==) ou partiel.
        // Si c'est un champ date stocké sous forme String, .regex(...) est possible,
        // sinon on fait un .is(...) si on stocke vraiment un type Date dans Mongo
        if (birthDate != null && !birthDate.isEmpty()) {
            subCriteria.add(Criteria.where("golden_record.payload.birthDate").is(birthDate));
        }

        // phone -> golden_record.payload.phones[].number
        // Pour chercher dans un tableau : on peut faire un .elemMatch ou direct .regex sur le champ
        if (phone != null && !phone.isEmpty()) {
            // On veut matcher n'importe quel phone "number" du tableau
            subCriteria.add(Criteria.where("golden_record.payload.phones").elemMatch(Criteria.where("number").regex(phone, "i")));
        }

        // email -> golden_record.payload.emails[].value
        if (email != null && !email.isEmpty()) {
            subCriteria.add(Criteria.where("golden_record.payload.emails").elemMatch(Criteria.where("value").regex(email, "i")));
        }

        // isVip -> golden_record.payload.isVip (boolean)
        if (isVip != null) {
            subCriteria.add(Criteria.where("golden_record.payload.isVip").is(isVip));
        }

        // hotelName -> hotel[].info.hotel.name
        if (hotel != null && !hotel.isEmpty()) {
            subCriteria.add(Criteria.where("hotel").elemMatch(Criteria.where("info.hotel.name").regex(hotel, "i")));
        }

        // restaurantName -> restauration[].info.restaurant.name
        if (restaurant != null && !restaurant.isEmpty()) {
            subCriteria.add(Criteria.where("restauration").elemMatch(Criteria.where("info.restaurant.name").regex(restaurant, "i")));
        }

        // Construction du Criteria global
        Criteria criteria = new Criteria();
        if (!subCriteria.isEmpty()) {
            // On combine tous les sous-critères par AND
            criteria = new Criteria().andOperator(subCriteria.toArray(new Criteria[0]));
        }

        // Confection de la Query
        Query query = new Query(criteria);

        List<Document> documents = mongoTemplate.find(query, Document.class, "exposed_api_customer_360");

        List<ExposedOneCustomer> results = new ArrayList<>();
        for (Document doc : documents) {
            ExposedOneCustomer mapped = ExposedOneCustomerMapper.fromDocument(doc);
            results.add(mapped);
        }

        return results;
    }
}
