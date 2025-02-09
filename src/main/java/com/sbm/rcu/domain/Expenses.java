package com.sbm.rcu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Objet regroupant la somme des dépenses (ex.: total_expense) + un tableau de détails.
 */
@Document(collection = "expenses")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Expenses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("total_expense")
    private Long totalExpense;

    @DBRef
    @Field("expense")
    @JsonIgnoreProperties(value = { "metadata", "expenses" }, allowSetters = true)
    private Set<Expense> expenses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Expenses id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTotalExpense() {
        return this.totalExpense;
    }

    public Expenses totalExpense(Long totalExpense) {
        this.setTotalExpense(totalExpense);
        return this;
    }

    public void setTotalExpense(Long totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Set<Expense> getExpenses() {
        return this.expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        if (this.expenses != null) {
            this.expenses.forEach(i -> i.setExpenses(null));
        }
        if (expenses != null) {
            expenses.forEach(i -> i.setExpenses(this));
        }
        this.expenses = expenses;
    }

    public Expenses expenses(Set<Expense> expenses) {
        this.setExpenses(expenses);
        return this;
    }

    public Expenses addExpense(Expense expense) {
        this.expenses.add(expense);
        expense.setExpenses(this);
        return this;
    }

    public Expenses removeExpense(Expense expense) {
        this.expenses.remove(expense);
        expense.setExpenses(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Expenses)) {
            return false;
        }
        return getId() != null && getId().equals(((Expenses) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Expenses{" +
            "id=" + getId() +
            ", totalExpense=" + getTotalExpense() +
            "}";
    }
}
