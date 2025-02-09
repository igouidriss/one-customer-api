package com.sbm.rcu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Address.
 */
@Document(collection = "address")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("type")
    private String type;

    @Field("zip_code")
    private String zipCode;

    @Field("city")
    private String city;

    @Field("country")
    private String country;

    @Field("line_1")
    private String line1;

    @Field("line_2")
    private String line2;

    @Field("line_3")
    private String line3;

    @DBRef
    @Field("payload")
    @JsonIgnoreProperties(value = { "metadata", "emails", "phones", "addresses" }, allowSetters = true)
    private Payload payload;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Address id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Address type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public Address zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return this.city;
    }

    public Address city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public Address country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLine1() {
        return this.line1;
    }

    public Address line1(String line1) {
        this.setLine1(line1);
        return this;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return this.line2;
    }

    public Address line2(String line2) {
        this.setLine2(line2);
        return this;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return this.line3;
    }

    public Address line3(String line3) {
        this.setLine3(line3);
        return this;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public Payload getPayload() {
        return this.payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Address payload(Payload payload) {
        this.setPayload(payload);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return getId() != null && getId().equals(((Address) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", line1='" + getLine1() + "'" +
            ", line2='" + getLine2() + "'" +
            ", line3='" + getLine3() + "'" +
            "}";
    }
}
