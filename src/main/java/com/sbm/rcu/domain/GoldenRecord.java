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
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * Entité correspondant à `golden_record`.
 */
@Document(collection = "golden_record")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GoldenRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

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
     * ex.: 'client'
     */
    @Field("domaine")
    private String domaine;

    /**
     * ex.: 'mdmId_1'
     */
    @Field("mdm_id")
    private String mdmId;

    /**
     * ex.: 'mdm'
     */
    @Field("source")
    private String source;

    /**
     * ex.: '2025-02-09T20:19:53.618778Z'
     */
    @Field(targetType = FieldType.DATE_TIME)
    private Instant timestamp;

    @DBRef
    @Field("cancelled")
    private Cancelled cancelled;

    @DBRef
    @Field("payload")
    private Payload payload;

    @DBRef
    @Field("sourceReference")
    @JsonIgnoreProperties(value = { "goldenRecord" }, allowSetters = true)
    private Set<SourceReference> sourceReferences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public GoldenRecord id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAggregateId() {
        return this.aggregateId;
    }

    public GoldenRecord aggregateId(String aggregateId) {
        this.setAggregateId(aggregateId);
        return this;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateType() {
        return this.aggregateType;
    }

    public GoldenRecord aggregateType(String aggregateType) {
        this.setAggregateType(aggregateType);
        return this;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String getDomaine() {
        return this.domaine;
    }

    public GoldenRecord domaine(String domaine) {
        this.setDomaine(domaine);
        return this;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getMdmId() {
        return this.mdmId;
    }

    public GoldenRecord mdmId(String mdmId) {
        this.setMdmId(mdmId);
        return this;
    }

    public void setMdmId(String mdmId) {
        this.mdmId = mdmId;
    }

    public String getSource() {
        return this.source;
    }

    public GoldenRecord source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public GoldenRecord recordTimestamp(Instant recordTimestamp) {
        this.setTimestamp(recordTimestamp);
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Cancelled getCancelled() {
        return this.cancelled;
    }

    public void setCancelled(Cancelled cancelled) {
        this.cancelled = cancelled;
    }

    public GoldenRecord cancelled(Cancelled cancelled) {
        this.setCancelled(cancelled);
        return this;
    }

    public Payload getPayload() {
        return this.payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public GoldenRecord payload(Payload payload) {
        this.setPayload(payload);
        return this;
    }

    public Set<SourceReference> getSourceReferences() {
        return this.sourceReferences;
    }

    public void setSourceReferences(Set<SourceReference> sourceReferences) {
        if (this.sourceReferences != null) {
            this.sourceReferences.forEach(i -> i.setGoldenRecord(null));
        }
        if (sourceReferences != null) {
            sourceReferences.forEach(i -> i.setGoldenRecord(this));
        }
        this.sourceReferences = sourceReferences;
    }

    public GoldenRecord sourceReferences(Set<SourceReference> sourceReferences) {
        this.setSourceReferences(sourceReferences);
        return this;
    }

    public GoldenRecord addSourceReference(SourceReference sourceReference) {
        this.sourceReferences.add(sourceReference);
        sourceReference.setGoldenRecord(this);
        return this;
    }

    public GoldenRecord removeSourceReference(SourceReference sourceReference) {
        this.sourceReferences.remove(sourceReference);
        sourceReference.setGoldenRecord(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoldenRecord)) {
            return false;
        }
        return getId() != null && getId().equals(((GoldenRecord) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoldenRecord{" +
               "id=" + getId() +
               ", aggregateId='" + getAggregateId() + "'" +
               ", aggregateType='" + getAggregateType() + "'" +
               ", domaine='" + getDomaine() + "'" +
               ", mdmId='" + getMdmId() + "'" +
               ", source='" + getSource() + "'" +
               ", recordTimestamp='" + getTimestamp() + "'" +
               "}";
    }
}
