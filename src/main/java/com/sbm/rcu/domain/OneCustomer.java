package com.sbm.rcu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Représente la racine du document : domaine = 'one_customer'.
 */
@Document(collection = "one_customer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OneCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Clé '_id' du JSON
     */
    @Id
    private String id;

    /**
     * 'one_customer'
     */
    @Field("domaine")
    private String domaine;

    /**
     * ex.: 'mdmId_1'
     */
    @Field("aggregate_id")
    private String aggregateId;

    /**
     * ex.: 'GoldenRecord'
     */
    @Field("aggregate_type")
    private String aggregateType;

    /**
     * ex.: '2025-02-09T20:19:53.668906Z'
     */
    @Field("timestamp")
    private Instant timestamp;

    @DBRef
    @Field("goldenRecord")
    private GoldenRecord goldenRecord;

    @DBRef
    @Field("hotelReservations")
    @JsonIgnoreProperties(value = { "cancelled", "expenses", "metadata", "oneCustomer" }, allowSetters = true)
    private Set<HotelReservation> hotelReservations = new HashSet<>();

    @DBRef
    @Field("restorationReservations")
    @JsonIgnoreProperties(value = { "cancelled", "expenses", "metadata", "oneCustomer" }, allowSetters = true)
    private Set<RestorationReservation> restorationReservations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public OneCustomer id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomaine() {
        return this.domaine;
    }

    public OneCustomer domaine(String domaine) {
        this.setDomaine(domaine);
        return this;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getAggregateId() {
        return this.aggregateId;
    }

    public OneCustomer aggregateId(String aggregateId) {
        this.setAggregateId(aggregateId);
        return this;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateType() {
        return this.aggregateType;
    }

    public OneCustomer aggregateType(String aggregateType) {
        this.setAggregateType(aggregateType);
        return this;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public OneCustomer timestamp(Instant timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public GoldenRecord getGoldenRecord() {
        return this.goldenRecord;
    }

    public void setGoldenRecord(GoldenRecord goldenRecord) {
        this.goldenRecord = goldenRecord;
    }

    public OneCustomer goldenRecord(GoldenRecord goldenRecord) {
        this.setGoldenRecord(goldenRecord);
        return this;
    }

    public Set<HotelReservation> getHotelReservations() {
        return this.hotelReservations;
    }

    public void setHotelReservations(Set<HotelReservation> hotelReservations) {
        if (this.hotelReservations != null) {
            this.hotelReservations.forEach(i -> i.setOneCustomer(null));
        }
        if (hotelReservations != null) {
            hotelReservations.forEach(i -> i.setOneCustomer(this));
        }
        this.hotelReservations = hotelReservations;
    }

    public OneCustomer hotelReservations(Set<HotelReservation> hotelReservations) {
        this.setHotelReservations(hotelReservations);
        return this;
    }

    public OneCustomer addHotelReservations(HotelReservation hotelReservation) {
        this.hotelReservations.add(hotelReservation);
        hotelReservation.setOneCustomer(this);
        return this;
    }

    public OneCustomer removeHotelReservations(HotelReservation hotelReservation) {
        this.hotelReservations.remove(hotelReservation);
        hotelReservation.setOneCustomer(null);
        return this;
    }

    public Set<RestorationReservation> getRestorationReservations() {
        return this.restorationReservations;
    }

    public void setRestorationReservations(Set<RestorationReservation> restorationReservations) {
        if (this.restorationReservations != null) {
            this.restorationReservations.forEach(i -> i.setOneCustomer(null));
        }
        if (restorationReservations != null) {
            restorationReservations.forEach(i -> i.setOneCustomer(this));
        }
        this.restorationReservations = restorationReservations;
    }

    public OneCustomer restorationReservations(Set<RestorationReservation> restorationReservations) {
        this.setRestorationReservations(restorationReservations);
        return this;
    }

    public OneCustomer addRestorationReservations(RestorationReservation restorationReservation) {
        this.restorationReservations.add(restorationReservation);
        restorationReservation.setOneCustomer(this);
        return this;
    }

    public OneCustomer removeRestorationReservations(RestorationReservation restorationReservation) {
        this.restorationReservations.remove(restorationReservation);
        restorationReservation.setOneCustomer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OneCustomer)) {
            return false;
        }
        return getId() != null && getId().equals(((OneCustomer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OneCustomer{" +
            "id=" + getId() +
            ", domaine='" + getDomaine() + "'" +
            ", aggregateId='" + getAggregateId() + "'" +
            ", aggregateType='" + getAggregateType() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
