<template>
  <div>
    <h2 id="page-heading" data-cy="ExpensesHeading">
      <span v-text="t$('rcuApplicationApp.expenses.home.title')" id="expenses-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('rcuApplicationApp.expenses.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ExpensesMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-expenses-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('rcuApplicationApp.expenses.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && expenses && expenses.length === 0">
      <span v-text="t$('rcuApplicationApp.expenses.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="expenses && expenses.length > 0">
      <table class="table table-striped" aria-describedby="expenses">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.expenses.totalExpense')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="expenses in expenses" :key="expenses.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ExpensesMySuffixView', params: { expensesId: expenses.id } }">{{ expenses.id }}</router-link>
            </td>
            <td>{{ expenses.totalExpense }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ExpensesMySuffixView', params: { expensesId: expenses.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ExpensesMySuffixEdit', params: { expensesId: expenses.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(expenses)"
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
        <span
          id="rcuApplicationApp.expenses.delete.question"
          data-cy="expensesDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-expenses-heading" v-text="t$('rcuApplicationApp.expenses.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-expenses"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeExpensesMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./expenses-my-suffix.component.ts"></script>
