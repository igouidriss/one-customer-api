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
 * Chaque réservation d’hôtel (bloc de la liste `hotel`).
 */
@Document(collection = "hotel_reservation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HotelReservation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ex.: 'opera_client_1_reservation_1'
     */
    @Id
    private String id;

    /**
     * ex.: 'reservation_1'
     */
    @Field("aggregate_id")
    private String aggregateId;

    /**
     * ex.: 'HotelReservation'
     */
    @Field("aggregate_type")
    private String aggregateType;

    @Field("client_id")
    private String clientId;

    @Field("domaine")
    private String domaine;

    @Field("source")
    private String source;

    /**
     * ex.: '2025-02-09T18:24:12.489902Z'
     */
    @Field("reservation_timestamp")
    private Instant reservationTimestamp;

    /**
     * ex.: 'projection_hotel_opera_HotelReservation'
     */
    @Field("projection")
    private String projection;

    /**
     * ex.: '2025-02-09' => date
     */
    @Field("date")
    private LocalDate date;

    /**
     * ex.: 2195, 1971, etc.
     */
    @Field("total_amount")
    private Long totalAmount;

    /**
     * ex.: '2025-02-11T18:22:27.656143'
     */
    @Field("arrival_date")
    private Instant arrivalDate;

    /**
     * ex.: '2025-02-13T18:22:27.656175'
     */
    @Field("leave_date")
    private Instant leaveDate;

    /**
     * ex.: 4, 1, 2...
     */
    @Field("guest_count")
    private Integer guestCount;

    /**
     * ex.: 'MC BAY', 'HERMITAGE'
     */
    @Field("hotel_name")
    private String hotelName;

    /**
     * ex.: '94c52788-42fb-4d9b-8bc9-8ca1f91e4c64'
     */
    @Field("hotel_id")
    private String hotelId;

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

    public HotelReservation id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAggregateId() {
        return this.aggregateId;
    }

    public HotelReservation aggregateId(String aggregateId) {
        this.setAggregateId(aggregateId);
        return this;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateType() {
        return this.aggregateType;
    }

    public HotelReservation aggregateType(String aggregateType) {
        this.setAggregateType(aggregateType);
        return this;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String getClientId() {
        return this.clientId;
    }

    public HotelReservation clientId(String clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDomaine() {
        return this.domaine;
    }

    public HotelReservation domaine(String domaine) {
        this.setDomaine(domaine);
        return this;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getSource() {
        return this.source;
    }

    public HotelReservation source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getReservationTimestamp() {
        return this.reservationTimestamp;
    }

    public HotelReservation reservationTimestamp(Instant reservationTimestamp) {
        this.setReservationTimestamp(reservationTimestamp);
        return this;
    }

    public void setReservationTimestamp(Instant reservationTimestamp) {
        this.reservationTimestamp = reservationTimestamp;
    }

    public String getProjection() {
        return this.projection;
    }

    public HotelReservation projection(String projection) {
        this.setProjection(projection);
        return this;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public HotelReservation date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTotalAmount() {
        return this.totalAmount;
    }

    public HotelReservation totalAmount(Long totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Instant getArrivalDate() {
        return this.arrivalDate;
    }

    public HotelReservation arrivalDate(Instant arrivalDate) {
        this.setArrivalDate(arrivalDate);
        return this;
    }

    public void setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Instant getLeaveDate() {
        return this.leaveDate;
    }

    public HotelReservation leaveDate(Instant leaveDate) {
        this.setLeaveDate(leaveDate);
        return this;
    }

    public void setLeaveDate(Instant leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Integer getGuestCount() {
        return this.guestCount;
    }

    public HotelReservation guestCount(Integer guestCount) {
        this.setGuestCount(guestCount);
        return this;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public String getHotelName() {
        return this.hotelName;
    }

    public HotelReservation hotelName(String hotelName) {
        this.setHotelName(hotelName);
        return this;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelId() {
        return this.hotelId;
    }

    public HotelReservation hotelId(String hotelId) {
        this.setHotelId(hotelId);
        return this;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public Cancelled getCancelled() {
        return this.cancelled;
    }

    public void setCancelled(Cancelled cancelled) {
        this.cancelled = cancelled;
    }

    public HotelReservation cancelled(Cancelled cancelled) {
        this.setCancelled(cancelled);
        return this;
    }

    public Expenses getExpenses() {
        return this.expenses;
    }

    public void setExpenses(Expenses expenses) {
        this.expenses = expenses;
    }

    public HotelReservation expenses(Expenses expenses) {
        this.setExpenses(expenses);
        return this;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public HotelReservation metadata(Metadata metadata) {
        this.setMetadata(metadata);
        return this;
    }

    public OneCustomer getOneCustomer() {
        return this.oneCustomer;
    }

    public void setOneCustomer(OneCustomer oneCustomer) {
        this.oneCustomer = oneCustomer;
    }

    public HotelReservation oneCustomer(OneCustomer oneCustomer) {
        this.setOneCustomer(oneCustomer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelReservation)) {
            return false;
        }
        return getId() != null && getId().equals(((HotelReservation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelReservation{" +
            "id=" + getId() +
            ", aggregateId='" + getAggregateId() + "'" +
            ", aggregateType='" + getAggregateType() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", domaine='" + getDomaine() + "'" +
            ", source='" + getSource() + "'" +
            ", reservationTimestamp='" + getReservationTimestamp() + "'" +
            ", projection='" + getProjection() + "'" +
            ", date='" + getDate() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", arrivalDate='" + getArrivalDate() + "'" +
            ", leaveDate='" + getLeaveDate() + "'" +
            ", guestCount=" + getGuestCount() +
            ", hotelName='" + getHotelName() + "'" +
            ", hotelId='" + getHotelId() + "'" +
            "}";
    }
}
