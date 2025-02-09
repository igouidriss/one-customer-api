package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.HotelReservation} entity.
 */
@Schema(description = "Chaque réservation d’hôtel (bloc de la liste `hotel`).")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HotelReservationDTO implements Serializable {

    @Schema(description = "ex.: 'opera_client_1_reservation_1'")
    private String id;

    @Schema(description = "ex.: 'reservation_1'")
    private String aggregateId;

    @Schema(description = "ex.: 'HotelReservation'")
    private String aggregateType;

    private String clientId;

    private String domaine;

    private String source;

    @Schema(description = "ex.: '2025-02-09T18:24:12.489902Z'")
    private Instant reservationTimestamp;

    @Schema(description = "ex.: 'projection_hotel_opera_HotelReservation'")
    private String projection;

    @Schema(description = "ex.: '2025-02-09' => date")
    private LocalDate date;

    @Schema(description = "ex.: 2195, 1971, etc.")
    private Long totalAmount;

    @Schema(description = "ex.: '2025-02-11T18:22:27.656143'")
    private Instant arrivalDate;

    @Schema(description = "ex.: '2025-02-13T18:22:27.656175'")
    private Instant leaveDate;

    @Schema(description = "ex.: 4, 1, 2...")
    private Integer guestCount;

    @Schema(description = "ex.: 'MC BAY', 'HERMITAGE'")
    private String hotelName;

    @Schema(description = "ex.: '94c52788-42fb-4d9b-8bc9-8ca1f91e4c64'")
    private String hotelId;

    private CancelledDTO cancelled;

    private ExpensesDTO expenses;

    private MetadataDTO metadata;

    private OneCustomerDTO oneCustomer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getReservationTimestamp() {
        return reservationTimestamp;
    }

    public void setReservationTimestamp(Instant reservationTimestamp) {
        this.reservationTimestamp = reservationTimestamp;
    }

    public String getProjection() {
        return projection;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Instant getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Instant getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Instant leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Integer getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public CancelledDTO getCancelled() {
        return cancelled;
    }

    public void setCancelled(CancelledDTO cancelled) {
        this.cancelled = cancelled;
    }

    public ExpensesDTO getExpenses() {
        return expenses;
    }

    public void setExpenses(ExpensesDTO expenses) {
        this.expenses = expenses;
    }

    public MetadataDTO getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataDTO metadata) {
        this.metadata = metadata;
    }

    public OneCustomerDTO getOneCustomer() {
        return oneCustomer;
    }

    public void setOneCustomer(OneCustomerDTO oneCustomer) {
        this.oneCustomer = oneCustomer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelReservationDTO)) {
            return false;
        }

        HotelReservationDTO hotelReservationDTO = (HotelReservationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hotelReservationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelReservationDTO{" +
            "id='" + getId() + "'" +
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
            ", cancelled=" + getCancelled() +
            ", expenses=" + getExpenses() +
            ", metadata=" + getMetadata() +
            ", oneCustomer=" + getOneCustomer() +
            "}";
    }
}
