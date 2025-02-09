package com.sbm.rcu.domain;

import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Représente un objet \"cancelled\" (annulation).
 */
@Document(collection = "cancelled")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cancelled implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * ex.: 'Client request', 'demerge_request'...
     */
    @Field("cancel_reason")
    private String cancelReason;

    /**
     * ex.: true / false
     */
    @Field("is_it_cancelled")
    private Boolean isItCancelled;

    /**
     * ex.: '2025-02-09T18:16:16.898010'
     */
    @Field("cancel_date")
    private Instant cancelDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Cancelled id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCancelReason() {
        return this.cancelReason;
    }

    public Cancelled cancelReason(String cancelReason) {
        this.setCancelReason(cancelReason);
        return this;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Boolean getIsItCancelled() {
        return this.isItCancelled;
    }

    public Cancelled isItCancelled(Boolean isItCancelled) {
        this.setIsItCancelled(isItCancelled);
        return this;
    }

    public void setIsItCancelled(Boolean isItCancelled) {
        this.isItCancelled = isItCancelled;
    }

    public Instant getCancelDate() {
        return this.cancelDate;
    }

    public Cancelled cancelDate(Instant cancelDate) {
        this.setCancelDate(cancelDate);
        return this;
    }

    public void setCancelDate(Instant cancelDate) {
        this.cancelDate = cancelDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cancelled)) {
            return false;
        }
        return getId() != null && getId().equals(((Cancelled) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cancelled{" +
            "id=" + getId() +
            ", cancelReason='" + getCancelReason() + "'" +
            ", isItCancelled='" + getIsItCancelled() + "'" +
            ", cancelDate='" + getCancelDate() + "'" +
            "}";
    }
}
