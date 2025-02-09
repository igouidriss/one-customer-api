package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.Metadata} entity.
 */
@Schema(description = "Métadonnées retrouvées dans info.metadata, payload.metadata, expenses_details[].metadata, etc.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetadataDTO implements Serializable {

    private String id;

    @Schema(description = "ex.: '115fe25e-e5a1-4267-990f-db342fc52fc2'")
    private String idEvent;

    @Schema(description = "ex.: '2025-02-09T18:16:16.898010'")
    private Instant metaTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public Instant getMetaTimestamp() {
        return metaTimestamp;
    }

    public void setMetaTimestamp(Instant metaTimestamp) {
        this.metaTimestamp = metaTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetadataDTO)) {
            return false;
        }

        MetadataDTO metadataDTO = (MetadataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metadataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetadataDTO{" +
            "id='" + getId() + "'" +
            ", idEvent='" + getIdEvent() + "'" +
            ", metaTimestamp='" + getMetaTimestamp() + "'" +
            "}";
    }
}
