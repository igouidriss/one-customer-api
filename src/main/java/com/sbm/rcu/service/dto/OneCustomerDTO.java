package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.OneCustomer} entity.
 */
@Schema(description = "Représente la racine du document : domaine = 'one_customer'.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OneCustomerDTO implements Serializable {

    @Schema(description = "Clé '_id' du JSON")
    private String id;

    @Schema(description = "'one_customer'")
    private String domaine;

    @Schema(description = "ex.: 'mdmId_1'")
    private String aggregateId;

    @Schema(description = "ex.: 'GoldenRecord'")
    private String aggregateType;

    @Schema(description = "ex.: '2025-02-09T20:19:53.668906Z'")
    private Instant timestamp;

    private GoldenRecordDTO goldenRecord;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public GoldenRecordDTO getGoldenRecord() {
        return goldenRecord;
    }

    public void setGoldenRecord(GoldenRecordDTO goldenRecord) {
        this.goldenRecord = goldenRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OneCustomerDTO)) {
            return false;
        }

        OneCustomerDTO oneCustomerDTO = (OneCustomerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, oneCustomerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OneCustomerDTO{" +
            "id='" + getId() + "'" +
            ", domaine='" + getDomaine() + "'" +
            ", aggregateId='" + getAggregateId() + "'" +
            ", aggregateType='" + getAggregateType() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", goldenRecord=" + getGoldenRecord() +
            "}";
    }
}
