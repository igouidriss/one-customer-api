package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.SourceReference} entity.
 */
@Schema(description = "Liste de références (sourceReferences) ex.: {source:'opera', value:'opera_client_1'}.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SourceReferenceDTO implements Serializable {

    private String id;

    private String source;

    private String value;

    private GoldenRecordDTO goldenRecord;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        if (!(o instanceof SourceReferenceDTO)) {
            return false;
        }

        SourceReferenceDTO sourceReferenceDTO = (SourceReferenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sourceReferenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SourceReferenceDTO{" +
            "id='" + getId() + "'" +
            ", source='" + getSource() + "'" +
            ", value='" + getValue() + "'" +
            ", goldenRecord=" + getGoldenRecord() +
            "}";
    }
}
