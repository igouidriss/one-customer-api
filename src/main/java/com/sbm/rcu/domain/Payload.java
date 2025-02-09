package com.sbm.rcu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Le payload du GoldenRecord, contenant lastName, firstName, etc.
 */
@Document(collection = "payload")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * ex.: 'IGOULALENE'
     */
    @Field("last_name")
    private String lastName;

    /**
     * ex.: 'Robert'
     */
    @Field("first_name")
    private String firstName;

    /**
     * ex.: '1997-01-01'
     */
    @Field("birth_date")
    private LocalDate birthDate;

    /**
     * ex.: 'FR'
     */
    @Field("lang")
    private String lang;

    /**
     * ex.: false
     */
    @Field("is_vip")
    private Boolean isVip;

    @DBRef
    @Field("metadata")
    private Metadata metadata;

    @DBRef
    @Field("email")
    @JsonIgnoreProperties(value = { "payload" }, allowSetters = true)
    private Set<Email> emails = new HashSet<>();

    @DBRef
    @Field("phone")
    @JsonIgnoreProperties(value = { "payload" }, allowSetters = true)
    private Set<Phone> phones = new HashSet<>();

    @DBRef
    @Field("address")
    @JsonIgnoreProperties(value = { "payload" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Payload id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Payload lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Payload firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public Payload birthDate(LocalDate birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getLang() {
        return this.lang;
    }

    public Payload lang(String lang) {
        this.setLang(lang);
        return this;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean getIsVip() {
        return this.isVip;
    }

    public Payload isVip(Boolean isVip) {
        this.setIsVip(isVip);
        return this;
    }

    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Payload metadata(Metadata metadata) {
        this.setMetadata(metadata);
        return this;
    }

    public Set<Email> getEmails() {
        return this.emails;
    }

    public void setEmails(Set<Email> emails) {
        if (this.emails != null) {
            this.emails.forEach(i -> i.setPayload(null));
        }
        if (emails != null) {
            emails.forEach(i -> i.setPayload(this));
        }
        this.emails = emails;
    }

    public Payload emails(Set<Email> emails) {
        this.setEmails(emails);
        return this;
    }

    public Payload addEmail(Email email) {
        this.emails.add(email);
        email.setPayload(this);
        return this;
    }

    public Payload removeEmail(Email email) {
        this.emails.remove(email);
        email.setPayload(null);
        return this;
    }

    public Set<Phone> getPhones() {
        return this.phones;
    }

    public void setPhones(Set<Phone> phones) {
        if (this.phones != null) {
            this.phones.forEach(i -> i.setPayload(null));
        }
        if (phones != null) {
            phones.forEach(i -> i.setPayload(this));
        }
        this.phones = phones;
    }

    public Payload phones(Set<Phone> phones) {
        this.setPhones(phones);
        return this;
    }

    public Payload addPhone(Phone phone) {
        this.phones.add(phone);
        phone.setPayload(this);
        return this;
    }

    public Payload removePhone(Phone phone) {
        this.phones.remove(phone);
        phone.setPayload(null);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setPayload(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setPayload(this));
        }
        this.addresses = addresses;
    }

    public Payload addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Payload addAddress(Address address) {
        this.addresses.add(address);
        address.setPayload(this);
        return this;
    }

    public Payload removeAddress(Address address) {
        this.addresses.remove(address);
        address.setPayload(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payload)) {
            return false;
        }
        return getId() != null && getId().equals(((Payload) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payload{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", lang='" + getLang() + "'" +
            ", isVip='" + getIsVip() + "'" +
            "}";
    }
}
