package com.sbm.rcu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Entités correspondantes aux tableaux `emails`, `phones`, `addresses`.
 */
@Document(collection = "email")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * type = 'PRO', 'HOME', etc.
     */
    @Field("type")
    private String type;

    /**
     * value = 'john.doe@company.com', etc.
     */
    @Field("value")
    private String value;

    @DBRef
    @Field("payload")
    @JsonIgnoreProperties(value = { "metadata", "emails", "phones", "addresses" }, allowSetters = true)
    private Payload payload;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Email id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Email type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public Email value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Payload getPayload() {
        return this.payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Email payload(Payload payload) {
        this.setPayload(payload);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }
        return getId() != null && getId().equals(((Email) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Email{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
