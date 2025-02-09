<template>
  <div>
    <h2 id="page-heading" data-cy="RestorationReservationHeading">
      <span v-text="t$('rcuApplicationApp.restorationReservation.home.title')" id="restoration-reservation-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('rcuApplicationApp.restorationReservation.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RestorationReservationMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-restoration-reservation-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('rcuApplicationApp.restorationReservation.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && restorationReservations && restorationReservations.length === 0">
      <span v-text="t$('rcuApplicationApp.restorationReservation.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="restorationReservations && restorationReservations.length > 0">
      <table class="table table-striped" aria-describedby="restorationReservations">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('aggregateId')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.aggregateId')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'aggregateId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('aggregateType')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.aggregateType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'aggregateType'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('clientId')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.clientId')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'clientId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('domaine')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.domaine')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'domaine'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('source')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.source')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'source'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('reservationTimestamp')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.reservationTimestamp')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'reservationTimestamp'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('projection')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.projection')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'projection'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('date')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.date')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'date'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('depositAmount')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.depositAmount')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'depositAmount'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('totalAmount')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.totalAmount')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'totalAmount'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('shift')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.shift')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'shift'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('guestCount')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.guestCount')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'guestCount'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('arrivalDate')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.arrivalDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'arrivalDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('restaurantName')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.restaurantName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'restaurantName'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('restaurantId')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.restaurantId')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'restaurantId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('cancelled.id')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.cancelled')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cancelled.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('expenses.id')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.expenses')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expenses.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('metadata.id')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.metadata')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'metadata.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('oneCustomer.id')">
              <span v-text="t$('rcuApplicationApp.restorationReservation.oneCustomer')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'oneCustomer.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="restorationReservation in restorationReservations" :key="restorationReservation.id" data-cy="entityTable">
            <td>
              <router-link
                :to="{ name: 'RestorationReservationMySuffixView', params: { restorationReservationId: restorationReservation.id } }"
                >{{ restorationReservation.id }}</router-link
              >
            </td>
            <td>{{ restorationReservation.aggregateId }}</td>
            <td>{{ restorationReservation.aggregateType }}</td>
            <td>{{ restorationReservation.clientId }}</td>
            <td>{{ restorationReservation.domaine }}</td>
            <td>{{ restorationReservation.source }}</td>
            <td>{{ formatDateShort(restorationReservation.reservationTimestamp) || '' }}</td>
            <td>{{ restorationReservation.projection }}</td>
            <td>{{ restorationReservation.date }}</td>
            <td>{{ restorationReservation.depositAmount }}</td>
            <td>{{ restorationReservation.totalAmount }}</td>
            <td>{{ restorationReservation.shift }}</td>
            <td>{{ restorationReservation.guestCount }}</td>
            <td>{{ formatDateShort(restorationReservation.arrivalDate) || '' }}</td>
            <td>{{ restorationReservation.restaurantName }}</td>
            <td>{{ restorationReservation.restaurantId }}</td>
            <td>
              <div v-if="restorationReservation.cancelled">
                <router-link :to="{ name: 'CancelledMySuffixView', params: { cancelledId: restorationReservation.cancelled.id } }">{{
                  restorationReservation.cancelled.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="restorationReservation.expenses">
                <router-link :to="{ name: 'ExpensesMySuffixView', params: { expensesId: restorationReservation.expenses.id } }">{{
                  restorationReservation.expenses.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="restorationReservation.metadata">
                <router-link :to="{ name: 'MetadataMySuffixView', params: { metadataId: restorationReservation.metadata.id } }">{{
                  restorationReservation.metadata.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="restorationReservation.oneCustomer">
                <router-link :to="{ name: 'OneCustomerMySuffixView', params: { oneCustomerId: restorationReservation.oneCustomer.id } }">{{
                  restorationReservation.oneCustomer.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RestorationReservationMySuffixView', params: { restorationReservationId: restorationReservation.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RestorationReservationMySuffixEdit', params: { restorationReservationId: restorationReservation.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(restorationReservation)"
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
        <span ref="infiniteScrollEl"></span>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="rcuApplicationApp.restorationReservation.delete.question"
          data-cy="restorationReservationDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-restorationReservation-heading"
          v-text="t$('rcuApplicationApp.restorationReservation.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-restorationReservation"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeRestorationReservationMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./restoration-reservation-my-suffix.component.ts"></script>
