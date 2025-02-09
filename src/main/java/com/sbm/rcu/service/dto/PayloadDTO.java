package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.Payload} entity.
 */
@Schema(description = "Le payload du GoldenRecord, contenant lastName, firstName, etc.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PayloadDTO implements Serializable {

    private String id;

    @Schema(description = "ex.: 'IGOULALENE'")
    private String lastName;

    @Schema(description = "ex.: 'Robert'")
    private String firstName;

    @Schema(description = "ex.: '1997-01-01'")
    private LocalDate birthDate;

    @Schema(description = "ex.: 'FR'")
    private String lang;

    @Schema(description = "ex.: false")
    private Boolean isVip;

    private MetadataDTO metadata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean getIsVip() {
        return isVip;
    }

    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    public MetadataDTO getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataDTO metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PayloadDTO)) {
            return false;
        }

        PayloadDTO payloadDTO = (PayloadDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, payloadDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayloadDTO{" +
            "id='" + getId() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", lang='" + getLang() + "'" +
            ", isVip='" + getIsVip() + "'" +
            ", metadata=" + getMetadata() +
            "}";
    }
}
