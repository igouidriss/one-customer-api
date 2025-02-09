package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.Expense} entity.
 */
@Schema(description = "Détails individuels des dépenses (expenses_details).")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExpenseDTO implements Serializable {

    private String id;

    @Schema(description = "ex.: 'Minibar', 'Wine'...")
    private String expenseType;

    @Schema(description = "Montant partiel, ex.: 24, 174, 45, 43...")
    private Long amount;

    @Schema(
        description = "S’il existe (dans la partie resto par exemple), depositAmount\nou tout autre champ. Ici, on peut en rajouter si besoin."
    )
    private Long depositAmount;

    @Schema(description = "ex.: 1276, 1385, etc.")
    private Long totalAmount;

    @Schema(description = "ex.: 'DINNER'")
    private String shift;

    private LocalDate date;

    private Instant arrivalDate;

    private Instant leaveDate;

    private Integer guestCount;

    @Schema(description = "ex.: 'MC BAY' / 'COYA'...")
    private String hotelName;

    private String hotelId;

    @Schema(
        description = "Ou version restaurantName, restaurantId si c’est un expense côté resto...\nPar simplicité, on les appelle hotelName/hotelId (ou on scinde)."
    )
    private String restaurantName;

    private String restaurantId;

    private String clientId;

    private MetadataDTO metadata;

    private ExpensesDTO expenses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MetadataDTO getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataDTO metadata) {
        this.metadata = metadata;
    }

    public ExpensesDTO getExpenses() {
        return expenses;
    }

    public void setExpenses(ExpensesDTO expenses) {
        this.expenses = expenses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpenseDTO)) {
            return false;
        }

        ExpenseDTO expenseDTO = (ExpenseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, expenseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpenseDTO{" +
            "id='" + getId() + "'" +
            ", expenseType='" + getExpenseType() + "'" +
            ", amount=" + getAmount() +
            ", depositAmount=" + getDepositAmount() +
            ", totalAmount=" + getTotalAmount() +
            ", shift='" + getShift() + "'" +
            ", date='" + getDate() + "'" +
            ", arrivalDate='" + getArrivalDate() + "'" +
            ", leaveDate='" + getLeaveDate() + "'" +
            ", guestCount=" + getGuestCount() +
            ", hotelName='" + getHotelName() + "'" +
            ", hotelId='" + getHotelId() + "'" +
            ", restaurantName='" + getRestaurantName() + "'" +
            ", restaurantId='" + getRestaurantId() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", metadata=" + getMetadata() +
            ", expenses=" + getExpenses() +
            "}";
    }
}
