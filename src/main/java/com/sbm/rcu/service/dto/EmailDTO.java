package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.Email} entity.
 */
@Schema(description = "Entités correspondantes aux tableaux `emails`, `phones`, `addresses`.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmailDTO implements Serializable {

    private String id;

    @Schema(description = "type = 'PRO', 'HOME', etc.")
    private String type;

    @Schema(description = "value = 'john.doe@company.com', etc.")
    private String value;

    private PayloadDTO payload;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        if (!(o instanceof EmailDTO)) {
            return false;
        }

        EmailDTO emailDTO = (EmailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, emailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailDTO{" +
            "id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", payload=" + getPayload() +
            "}";
    }
}
