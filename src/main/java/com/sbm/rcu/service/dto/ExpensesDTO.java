package com.sbm.rcu.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbm.rcu.domain.Expenses} entity.
 */
@Schema(description = "Objet regroupant la somme des dépenses (ex.: total_expense) + un tableau de détails.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExpensesDTO implements Serializable {

    private String id;

    private Long totalExpense;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Long totalExpense) {
        this.totalExpense = totalExpense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpensesDTO)) {
            return false;
        }

        ExpensesDTO expensesDTO = (ExpensesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, expensesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpensesDTO{" +
            "id='" + getId() + "'" +
            ", totalExpense=" + getTotalExpense() +
            "}";
    }
}
