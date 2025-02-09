package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.Cancelled} entity.
 */
@Schema(description = "Représente un objet \"cancelled\" (annulation).")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CancelledDTO implements Serializable {

    private String id;

    @Schema(description = "ex.: 'Client request', 'demerge_request'...")
    private String cancelReason;

    @Schema(description = "ex.: true / false")
    private Boolean isItCancelled;

    @Schema(description = "ex.: '2025-02-09T18:16:16.898010'")
    private Instant cancelDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Boolean getIsItCancelled() {
        return isItCancelled;
    }

    public void setIsItCancelled(Boolean isItCancelled) {
        this.isItCancelled = isItCancelled;
    }

    public Instant getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Instant cancelDate) {
        this.cancelDate = cancelDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CancelledDTO)) {
            return false;
        }

        CancelledDTO cancelledDTO = (CancelledDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cancelledDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CancelledDTO{" +
            "id='" + getId() + "'" +
            ", cancelReason='" + getCancelReason() + "'" +
            ", isItCancelled='" + getIsItCancelled() + "'" +
            ", cancelDate='" + getCancelDate() + "'" +
            "}";
    }
}
