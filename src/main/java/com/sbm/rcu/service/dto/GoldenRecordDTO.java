package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.GoldenRecord} entity.
 */
@Schema(description = "Entité correspondant à `golden_record`.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GoldenRecordDTO implements Serializable {

    private String id;

    @Schema(description = "ex.: 'mdmId_1'")
    private String aggregateId;

    @Schema(description = "ex.: 'GoldenRecord'")
    private String aggregateType;

    @Schema(description = "ex.: 'client'")
    private String domaine;

    @Schema(description = "ex.: 'mdmId_1'")
    private String mdmId;

    @Schema(description = "ex.: 'mdm'")
    private String source;

    @Schema(description = "ex.: '2025-02-09T20:19:53.618778Z'")
    private Instant recordTimestamp;

    private CancelledDTO cancelled;

    private PayloadDTO payload;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getMdmId() {
        return mdmId;
    }

    public void setMdmId(String mdmId) {
        this.mdmId = mdmId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getRecordTimestamp() {
        return recordTimestamp;
    }

    public void setRecordTimestamp(Instant recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public CancelledDTO getCancelled() {
        return cancelled;
    }

    public void setCancelled(CancelledDTO cancelled) {
        this.cancelled = cancelled;
    }

    public PayloadDTO getPayload() {
        return payload;
    }

    public void setPayload(PayloadDTO payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoldenRecordDTO)) {
            return false;
        }

        GoldenRecordDTO goldenRecordDTO = (GoldenRecordDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, goldenRecordDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoldenRecordDTO{" +
            "id='" + getId() + "'" +
            ", aggregateId='" + getAggregateId() + "'" +
            ", aggregateType='" + getAggregateType() + "'" +
            ", domaine='" + getDomaine() + "'" +
            ", mdmId='" + getMdmId() + "'" +
            ", source='" + getSource() + "'" +
            ", recordTimestamp='" + getRecordTimestamp() + "'" +
            ", cancelled=" + getCancelled() +
            ", payload=" + getPayload() +
            "}";
    }
}
