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
 * Détails individuels des dépenses (expenses_details).
 */
@Document(collection = "expense")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * ex.: 'Minibar', 'Wine'...
     */
    @Field("expense_type")
    private String expenseType;

    /**
     * Montant partiel, ex.: 24, 174, 45, 43...
     */
    @Field("amount")
    private Long amount;

    /**
     * S’il existe (dans la partie resto par exemple), depositAmount
     * ou tout autre champ. Ici, on peut en rajouter si besoin.
     */
    @Field("deposit_amount")
    private Long depositAmount;

    /**
     * ex.: 1276, 1385, etc.
     */
    @Field("total_amount")
    private Long totalAmount;

    /**
     * ex.: 'DINNER'
     */
    @Field("shift")
    private String shift;

    @Field("date")
    private LocalDate date;

    @Field("arrival_date")
    private Instant arrivalDate;

    @Field("leave_date")
    private Instant leaveDate;

    @Field("guest_count")
    private Integer guestCount;

    /**
     * ex.: 'MC BAY' / 'COYA'...
     */
    @Field("hotel_name")
    private String hotelName;

    @Field("hotel_id")
    private String hotelId;

    /**
     * Ou version restaurantName, restaurantId si c’est un expense côté resto...
     * Par simplicité, on les appelle hotelName/hotelId (ou on scinde).
     */
    @Field("restaurant_name")
    private String restaurantName;

    @Field("restaurant_id")
    private String restaurantId;

    @Field("client_id")
    private String clientId;

    @DBRef
    @Field("metadata")
    private Metadata metadata;

    @DBRef
    @Field("expenses")
    @JsonIgnoreProperties(value = { "expenses" }, allowSetters = true)
    private Expenses expenses;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Expense id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpenseType() {
        return this.expenseType;
    }

    public Expense expenseType(String expenseType) {
        this.setExpenseType(expenseType);
        return this;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public Long getAmount() {
        return this.amount;
    }

    public Expense amount(Long amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getDepositAmount() {
        return this.depositAmount;
    }

    public Expense depositAmount(Long depositAmount) {
        this.setDepositAmount(depositAmount);
        return this;
    }

    public void setDepositAmount(Long depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Long getTotalAmount() {
        return this.totalAmount;
    }

    public Expense totalAmount(Long totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShift() {
        return this.shift;
    }

    public Expense shift(String shift) {
        this.setShift(shift);
        return this;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Expense date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getArrivalDate() {
        return this.arrivalDate;
    }

    public Expense arrivalDate(Instant arrivalDate) {
        this.setArrivalDate(arrivalDate);
        return this;
    }

    public void setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Instant getLeaveDate() {
        return this.leaveDate;
    }

    public Expense leaveDate(Instant leaveDate) {
        this.setLeaveDate(leaveDate);
        return this;
    }

    public void setLeaveDate(Instant leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Integer getGuestCount() {
        return this.guestCount;
    }

    public Expense guestCount(Integer guestCount) {
        this.setGuestCount(guestCount);
        return this;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public String getHotelName() {
        return this.hotelName;
    }

    public Expense hotelName(String hotelName) {
        this.setHotelName(hotelName);
        return this;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelId() {
        return this.hotelId;
    }

    public Expense hotelId(String hotelId) {
        this.setHotelId(hotelId);
        return this;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }

    public Expense restaurantName(String restaurantName) {
        this.setRestaurantName(restaurantName);
        return this;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantId() {
        return this.restaurantId;
    }

    public Expense restaurantId(String restaurantId) {
        this.setRestaurantId(restaurantId);
        return this;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getClientId() {
        return this.clientId;
    }

    public Expense clientId(String clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Expense metadata(Metadata metadata) {
        this.setMetadata(metadata);
        return this;
    }

    public Expenses getExpenses() {
        return this.expenses;
    }

    public void setExpenses(Expenses expenses) {
        this.expenses = expenses;
    }

    public Expense expenses(Expenses expenses) {
        this.setExpenses(expenses);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Expense)) {
            return false;
        }
        return getId() != null && getId().equals(((Expense) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Expense{" +
            "id=" + getId() +
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
            "}";
    }
}
