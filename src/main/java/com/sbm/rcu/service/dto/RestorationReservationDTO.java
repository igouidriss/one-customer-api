package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.RestorationReservation} entity.
 */
@Schema(description = "Chaque réservation de restauration (bloc de la liste `restauration`).")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RestorationReservationDTO implements Serializable {

    private String id;

    private String aggregateId;

    private String aggregateType;

    private String clientId;

    private String domaine;

    private String source;

    @Schema(description = "ex.: '2025-02-09T18:16:25.018944Z'")
    private Instant reservationTimestamp;

    private String projection;

    @Schema(description = "ex.: '2025-02-09'")
    private LocalDate date;

    @Schema(description = "ex.: 2, 6, 82")
    private Long depositAmount;

    @Schema(description = "ex.: 144, 298, 193")
    private Long totalAmount;

    @Schema(description = "ex.: 'DINNER'")
    private String shift;

    @Schema(description = "ex.: 4")
    private Integer guestCount;

    @Schema(description = "ex.: '2025-02-10T18:16:09.867311'")
    private Instant arrivalDate;

    private String restaurantName;

    private String restaurantId;

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

    public Long getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Long depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public Integer getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public Instant getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
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
        if (!(o instanceof RestorationReservationDTO)) {
            return false;
        }

        RestorationReservationDTO restorationReservationDTO = (RestorationReservationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, restorationReservationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestorationReservationDTO{" +
            "id='" + getId() + "'" +
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
            ", cancelled=" + getCancelled() +
            ", expenses=" + getExpenses() +
            ", metadata=" + getMetadata() +
            ", oneCustomer=" + getOneCustomer() +
            "}";
    }
}
