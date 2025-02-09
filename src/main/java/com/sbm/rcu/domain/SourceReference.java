package com.sbm.rcu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Liste de références (sourceReferences) ex.: {source:'opera', value:'opera_client_1'}.
 */
@Document(collection = "source_reference")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SourceReference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("source")
    private String source;

    @Field("value")
    private String value;

    @DBRef
    @Field("goldenRecord")
    @JsonIgnoreProperties(value = { "cancelled", "payload", "sourceReferences" }, allowSetters = true)
    private GoldenRecord goldenRecord;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public SourceReference id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return this.source;
    }

    public SourceReference source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return this.value;
    }

    public SourceReference value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public GoldenRecord getGoldenRecord() {
        return this.goldenRecord;
    }

    public void setGoldenRecord(GoldenRecord goldenRecord) {
        this.goldenRecord = goldenRecord;
    }

    public SourceReference goldenRecord(GoldenRecord goldenRecord) {
        this.setGoldenRecord(goldenRecord);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SourceReference)) {
            return false;
        }
        return getId() != null && getId().equals(((SourceReference) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SourceReference{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
