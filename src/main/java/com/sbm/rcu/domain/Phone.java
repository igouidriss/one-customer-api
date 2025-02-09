package com.sbm.rcu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Phone.
 */
@Document(collection = "phone")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * ex.: 'PRO', 'HOME'
     */
    @Field("type")
    private String type;

    /**
     * ex.: '+33-123-456'
     */
    @Field("number")
    private String number;

    @DBRef
    @Field("payload")
    @JsonIgnoreProperties(value = { "metadata", "emails", "phones", "addresses" }, allowSetters = true)
    private Payload payload;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Phone id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Phone type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return this.number;
    }

    public Phone number(String number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Payload getPayload() {
        return this.payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Phone payload(Payload payload) {
        this.setPayload(payload);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phone)) {
            return false;
        }
        return getId() != null && getId().equals(((Phone) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Phone{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", number='" + getNumber() + "'" +
            "}";
    }
}
