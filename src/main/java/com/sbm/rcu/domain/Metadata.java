package com.sbm.rcu.domain;

import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Métadonnées retrouvées dans info.metadata, payload.metadata, expenses_details[].metadata, etc.
 */
@Document(collection = "metadata")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Metadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * ex.: '115fe25e-e5a1-4267-990f-db342fc52fc2'
     */
    @Field("id_event")
    private String idEvent;

    /**
     * ex.: '2025-02-09T18:16:16.898010'
     */
    @Field("meta_timestamp")
    private Instant metaTimestamp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Metadata id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEvent() {
        return this.idEvent;
    }

    public Metadata idEvent(String idEvent) {
        this.setIdEvent(idEvent);
        return this;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public Instant getMetaTimestamp() {
        return this.metaTimestamp;
    }

    public Metadata metaTimestamp(Instant metaTimestamp) {
        this.setMetaTimestamp(metaTimestamp);
        return this;
    }

    public void setMetaTimestamp(Instant metaTimestamp) {
        this.metaTimestamp = metaTimestamp;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Metadata)) {
            return false;
        }
        return getId() != null && getId().equals(((Metadata) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Metadata{" +
            "id=" + getId() +
            ", idEvent='" + getIdEvent() + "'" +
            ", metaTimestamp='" + getMetaTimestamp() + "'" +
            "}";
    }
}
