<template>
  <div>
    <h2 id="page-heading" data-cy="ExpenseHeading">
      <span v-text="t$('rcuApplicationApp.expense.home.title')" id="expense-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('rcuApplicationApp.expense.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ExpenseMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-expense-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('rcuApplicationApp.expense.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && expenses && expenses.length === 0">
      <span v-text="t$('rcuApplicationApp.expense.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="expenses && expenses.length > 0">
      <table class="table table-striped" aria-describedby="expenses">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.expenseType')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.amount')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.depositAmount')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.totalAmount')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.shift')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.date')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.arrivalDate')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.leaveDate')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.guestCount')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.hotelName')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.hotelId')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.restaurantName')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.restaurantId')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.clientId')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.metadata')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expense.expenses')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="expense in expenses" :key="expense.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ExpenseMySuffixView', params: { expenseId: expense.id } }">{{ expense.id }}</router-link>
            </td>
            <td>{{ expense.expenseType }}</td>
            <td>{{ expense.amount }}</td>
            <td>{{ expense.depositAmount }}</td>
            <td>{{ expense.totalAmount }}</td>
            <td>{{ expense.shift }}</td>
            <td>{{ expense.date }}</td>
            <td>{{ formatDateShort(expense.arrivalDate) || '' }}</td>
            <td>{{ formatDateShort(expense.leaveDate) || '' }}</td>
            <td>{{ expense.guestCount }}</td>
            <td>{{ expense.hotelName }}</td>
            <td>{{ expense.hotelId }}</td>
            <td>{{ expense.restaurantName }}</td>
            <td>{{ expense.restaurantId }}</td>
            <td>{{ expense.clientId }}</td>
            <td>
              <div v-if="expense.metadata">
                <router-link :to="{ name: 'MetadataMySuffixView', params: { metadataId: expense.metadata.id } }">{{
                  expense.metadata.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="expense.expenses">
                <router-link :to="{ name: 'ExpensesMySuffixView', params: { expensesId: expense.expenses.id } }">{{
                  expense.expenses.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ExpenseMySuffixView', params: { expenseId: expense.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ExpenseMySuffixEdit', params: { expenseId: expense.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(expense)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="rcuApplicationApp.expense.delete.question" data-cy="expenseDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-expense-heading" v-text="t$('rcuApplicationApp.expense.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-expense"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeExpenseMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./expense-my-suffix.component.ts"></script>
