package com.sbm.rcu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Chaque réservation de restauration (bloc de la liste `restauration`).
 */
@Document(collection = "restoration_reservation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RestorationReservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("aggregate_id")
    private String aggregateId;

    @Field("aggregate_type")
    private String aggregateType;

    @Field("client_id")
    private String clientId;

    @Field("domaine")
    private String domaine;

    @Field("source")
    private String source;

    /**
     * ex.: '2025-02-09T18:16:25.018944Z'
     */
    @Field("reservation_timestamp")
    private Instant reservationTimestamp;

    @Field("projection")
    private String projection;

    /**
     * ex.: '2025-02-09'
     */
    @Field("date")
    private LocalDate date;

    /**
     * ex.: 2, 6, 82
     */
    @Field("deposit_amount")
    private Long depositAmount;

    /**
     * ex.: 144, 298, 193
     */
    @Field("total_amount")
    private Long totalAmount;

    /**
     * ex.: 'DINNER'
     */
    @Field("shift")
    private String shift;

    /**
     * ex.: 4
     */
    @Field("guest_count")
    private Integer guestCount;

    /**
     * ex.: '2025-02-10T18:16:09.867311'
     */
    @Field("arrival_date")
    private Instant arrivalDate;

    @Field("restaurant_name")
    private String restaurantName;

    @Field("restaurant_id")
    private String restaurantId;

    @DBRef
    @Field("cancelled")
    private Cancelled cancelled;

    @DBRef
    @Field("expenses")
    private Expenses expenses;

    @DBRef
    @Field("metadata")
    private Metadata metadata;

    @DBRef
    @Field("oneCustomer")
    @JsonIgnoreProperties(value = { "goldenRecord", "hotelReservations", "restorationReservations" }, allowSetters = true)
    private OneCustomer oneCustomer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public RestorationReservation id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAggregateId() {
        return this.aggregateId;
    }

    public RestorationReservation aggregateId(String aggregateId) {
        this.setAggregateId(aggregateId);
        return this;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateType() {
        return this.aggregateType;
    }

    public RestorationReservation aggregateType(String aggregateType) {
        this.setAggregateType(aggregateType);
        return this;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String getClientId() {
        return this.clientId;
    }

    public RestorationReservation clientId(String clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDomaine() {
        return this.domaine;
    }

    public RestorationReservation domaine(String domaine) {
        this.setDomaine(domaine);
        return this;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getSource() {
        return this.source;
    }

    public RestorationReservation source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getReservationTimestamp() {
        return this.reservationTimestamp;
    }

    public RestorationReservation reservationTimestamp(Instant reservationTimestamp) {
        this.setReservationTimestamp(reservationTimestamp);
        return this;
    }

    public void setReservationTimestamp(Instant reservationTimestamp) {
        this.reservationTimestamp = reservationTimestamp;
    }

    public String getProjection() {
        return this.projection;
    }

    public RestorationReservation projection(String projection) {
        this.setProjection(projection);
        return this;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public RestorationReservation date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getDepositAmount() {
        return this.depositAmount;
    }

    public RestorationReservation depositAmount(Long depositAmount) {
        this.setDepositAmount(depositAmount);
        return this;
    }

    public void setDepositAmount(Long depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Long getTotalAmount() {
        return this.totalAmount;
    }

    public RestorationReservation totalAmount(Long totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShift() {
        return this.shift;
    }

    public RestorationReservation shift(String shift) {
        this.setShift(shift);
        return this;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public Integer getGuestCount() {
        return this.guestCount;
    }

    public RestorationReservation guestCount(Integer guestCount) {
        this.setGuestCount(guestCount);
        return this;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public Instant getArrivalDate() {
        return this.arrivalDate;
    }

    public RestorationReservation arrivalDate(Instant arrivalDate) {
        this.setArrivalDate(arrivalDate);
        return this;
    }

    public void setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }

    public RestorationReservation restaurantName(String restaurantName) {
        this.setRestaurantName(restaurantName);
        return this;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantId() {
        return this.restaurantId;
    }

    public RestorationReservation restaurantId(String restaurantId) {
        this.setRestaurantId(restaurantId);
        return this;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Cancelled getCancelled() {
        return this.cancelled;
    }

    public void setCancelled(Cancelled cancelled) {
        this.cancelled = cancelled;
    }

    public RestorationReservation cancelled(Cancelled cancelled) {
        this.setCancelled(cancelled);
        return this;
    }

    public Expenses getExpenses() {
        return this.expenses;
    }

    public void setExpenses(Expenses expenses) {
        this.expenses = expenses;
    }

    public RestorationReservation expenses(Expenses expenses) {
        this.setExpenses(expenses);
        return this;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public RestorationReservation metadata(Metadata metadata) {
        this.setMetadata(metadata);
        return this;
    }

    public OneCustomer getOneCustomer() {
        return this.oneCustomer;
    }

    public void setOneCustomer(OneCustomer oneCustomer) {
        this.oneCustomer = oneCustomer;
    }

    public RestorationReservation oneCustomer(OneCustomer oneCustomer) {
        this.setOneCustomer(oneCustomer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestorationReservation)) {
            return false;
        }
        return getId() != null && getId().equals(((RestorationReservation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestorationReservation{" +
            "id=" + getId() +
            ", aggregateId='" + getAggregateId() + "'" +
            ", aggregateType='" + getAggregateType() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", domaine='" + getDomaine() + "'" +
            ", source='" + getSource() + "'" +
            ", reservationTimestamp='" + getReservationTimestamp() + "'" +
            ", projection='" + getProjection() + "'" +
            ", date='" + getDate() + "'" +
            ", depositAmount=" + getDepositAmount() +
            ", totalAmount=" + getTotalAmount() +
            ", shift='" + getShift() + "'" +
            ", guestCount=" + getGuestCount() +
            ", arrivalDate='" + getArrivalDate() + "'" +
            ", restaurantName='" + getRestaurantName() + "'" +
            ", restaurantId='" + getRestaurantId() + "'" +
            "}";
    }
}
