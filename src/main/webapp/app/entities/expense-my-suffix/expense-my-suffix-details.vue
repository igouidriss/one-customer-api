<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="expense">
        <h2 class="jh-entity-heading" data-cy="expenseDetailsHeading">
          <span v-text="t$('rcuApplicationApp.expense.detail.title')"></span> {{ expense.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.expenseType')"></span>
          </dt>
          <dd>
            <span>{{ expense.expenseType }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.amount')"></span>
          </dt>
          <dd>
            <span>{{ expense.amount }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.depositAmount')"></span>
          </dt>
          <dd>
            <span>{{ expense.depositAmount }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.totalAmount')"></span>
          </dt>
          <dd>
            <span>{{ expense.totalAmount }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.shift')"></span>
          </dt>
          <dd>
            <span>{{ expense.shift }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.date')"></span>
          </dt>
          <dd>
            <span>{{ expense.date }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.arrivalDate')"></span>
          </dt>
          <dd>
            <span v-if="expense.arrivalDate">{{ formatDateLong(expense.arrivalDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.leaveDate')"></span>
          </dt>
          <dd>
            <span v-if="expense.leaveDate">{{ formatDateLong(expense.leaveDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.guestCount')"></span>
          </dt>
          <dd>
            <span>{{ expense.guestCount }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.hotelName')"></span>
          </dt>
          <dd>
            <span>{{ expense.hotelName }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.hotelId')"></span>
          </dt>
          <dd>
            <span>{{ expense.hotelId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.restaurantName')"></span>
          </dt>
          <dd>
            <span>{{ expense.restaurantName }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.restaurantId')"></span>
          </dt>
          <dd>
            <span>{{ expense.restaurantId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.clientId')"></span>
          </dt>
          <dd>
            <span>{{ expense.clientId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.metadata')"></span>
          </dt>
          <dd>
            <div v-if="expense.metadata">
              <router-link :to="{ name: 'MetadataMySuffixView', params: { metadataId: expense.metadata.id } }">{{
                expense.metadata.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.expense.expenses')"></span>
          </dt>
          <dd>
            <div v-if="expense.expenses">
              <router-link :to="{ name: 'ExpensesMySuffixView', params: { expensesId: expense.expenses.id } }">{{
                expense.expenses.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="expense.id"
          :to="{ name: 'ExpenseMySuffixEdit', params: { expenseId: expense.id } }"
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

<script lang="ts" src="./expense-my-suffix-details.component.ts"></script>
