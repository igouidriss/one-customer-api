<template>
  <div>
    <h2 id="page-heading" data-cy="OneCustomerHeading">
      <span v-text="t$('rcuApplicationApp.oneCustomer.home.title')" id="one-customer-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('rcuApplicationApp.oneCustomer.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'OneCustomerMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-one-customer-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('rcuApplicationApp.oneCustomer.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && oneCustomers && oneCustomers.length === 0">
      <span v-text="t$('rcuApplicationApp.oneCustomer.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="oneCustomers && oneCustomers.length > 0">
      <table class="table table-striped" aria-describedby="oneCustomers">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('domaine')">
              <span v-text="t$('rcuApplicationApp.oneCustomer.domaine')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'domaine'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('aggregateId')">
              <span v-text="t$('rcuApplicationApp.oneCustomer.aggregateId')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'aggregateId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('aggregateType')">
              <span v-text="t$('rcuApplicationApp.oneCustomer.aggregateType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'aggregateType'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('timestamp')">
              <span v-text="t$('rcuApplicationApp.oneCustomer.timestamp')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'timestamp'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('goldenRecord.id')">
              <span v-text="t$('rcuApplicationApp.oneCustomer.goldenRecord')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'goldenRecord.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="oneCustomer in oneCustomers" :key="oneCustomer.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OneCustomerMySuffixView', params: { oneCustomerId: oneCustomer.id } }">{{
                oneCustomer.id
              }}</router-link>
            </td>
            <td>{{ oneCustomer.domaine }}</td>
            <td>{{ oneCustomer.aggregateId }}</td>
            <td>{{ oneCustomer.aggregateType }}</td>
            <td>{{ formatDateShort(oneCustomer.timestamp) || '' }}</td>
            <td>
              <div v-if="oneCustomer.goldenRecord">
                <router-link :to="{ name: 'GoldenRecordMySuffixView', params: { goldenRecordId: oneCustomer.goldenRecord.id } }">{{
                  oneCustomer.goldenRecord.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'OneCustomerMySuffixView', params: { oneCustomerId: oneCustomer.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'OneCustomerMySuffixEdit', params: { oneCustomerId: oneCustomer.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(oneCustomer)"
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
          id="rcuApplicationApp.oneCustomer.delete.question"
          data-cy="oneCustomerDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-oneCustomer-heading" v-text="t$('rcuApplicationApp.oneCustomer.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-oneCustomer"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeOneCustomerMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="oneCustomers && oneCustomers.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./one-customer-my-suffix.component.ts"></script>
