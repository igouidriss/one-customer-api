package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.Phone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhoneDTO implements Serializable {

    private String id;

    @Schema(description = "ex.: 'PRO', 'HOME'")
    private String type;

    @Schema(description = "ex.: '+33-123-456'")
    private String number;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
        if (!(o instanceof PhoneDTO)) {
            return false;
        }

        PhoneDTO phoneDTO = (PhoneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, phoneDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhoneDTO{" +
            "id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", number='" + getNumber() + "'" +
            ", payload=" + getPayload() +
            "}";
    }
}
