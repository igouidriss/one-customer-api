<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="expenses">
        <h2 class="jh-entity-heading" data-cy="expensesDetailsHeading">
          <span v-text="t$('rcuApplicationApp.expenses.detail.title')"></span> {{ expenses.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.expenses.totalExpense')"></span>
          </dt>
          <dd>
            <span>{{ expenses.totalExpense }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="expenses.id"
          :to="{ name: 'ExpensesMySuffixEdit', params: { expensesId: expenses.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./expenses-my-suffix-details.component.ts"></script>
