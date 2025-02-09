<template>
  <div>
    <h2 id="page-heading" data-cy="HotelReservationHeading">
      <span v-text="t$('rcuApplicationApp.hotelReservation.home.title')" id="hotel-reservation-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('rcuApplicationApp.hotelReservation.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'HotelReservationMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-hotel-reservation-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('rcuApplicationApp.hotelReservation.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && hotelReservations && hotelReservations.length === 0">
      <span v-text="t$('rcuApplicationApp.hotelReservation.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="hotelReservations && hotelReservations.length > 0">
      <table class="table table-striped" aria-describedby="hotelReservations">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('aggregateId')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.aggregateId')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'aggregateId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('aggregateType')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.aggregateType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'aggregateType'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('clientId')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.clientId')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'clientId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('domaine')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.domaine')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'domaine'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('source')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.source')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'source'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('reservationTimestamp')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.reservationTimestamp')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'reservationTimestamp'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('projection')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.projection')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'projection'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('date')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.date')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'date'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('totalAmount')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.totalAmount')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'totalAmount'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('arrivalDate')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.arrivalDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'arrivalDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('leaveDate')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.leaveDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'leaveDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('guestCount')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.guestCount')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'guestCount'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('hotelName')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.hotelName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hotelName'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('hotelId')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.hotelId')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hotelId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('cancelled.id')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.cancelled')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cancelled.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('expenses.id')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.expenses')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expenses.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('metadata.id')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.metadata')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'metadata.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('oneCustomer.id')">
              <span v-text="t$('rcuApplicationApp.hotelReservation.oneCustomer')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'oneCustomer.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="hotelReservation in hotelReservations" :key="hotelReservation.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'HotelReservationMySuffixView', params: { hotelReservationId: hotelReservation.id } }">{{
                hotelReservation.id
              }}</router-link>
            </td>
            <td>{{ hotelReservation.aggregateId }}</td>
            <td>{{ hotelReservation.aggregateType }}</td>
            <td>{{ hotelReservation.clientId }}</td>
            <td>{{ hotelReservation.domaine }}</td>
            <td>{{ hotelReservation.source }}</td>
            <td>{{ formatDateShort(hotelReservation.reservationTimestamp) || '' }}</td>
            <td>{{ hotelReservation.projection }}</td>
            <td>{{ hotelReservation.date }}</td>
            <td>{{ hotelReservation.totalAmount }}</td>
            <td>{{ formatDateShort(hotelReservation.arrivalDate) || '' }}</td>
            <td>{{ formatDateShort(hotelReservation.leaveDate) || '' }}</td>
            <td>{{ hotelReservation.guestCount }}</td>
            <td>{{ hotelReservation.hotelName }}</td>
            <td>{{ hotelReservation.hotelId }}</td>
            <td>
              <div v-if="hotelReservation.cancelled">
                <router-link :to="{ name: 'CancelledMySuffixView', params: { cancelledId: hotelReservation.cancelled.id } }">{{
                  hotelReservation.cancelled.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="hotelReservation.expenses">
                <router-link :to="{ name: 'ExpensesMySuffixView', params: { expensesId: hotelReservation.expenses.id } }">{{
                  hotelReservation.expenses.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="hotelReservation.metadata">
                <router-link :to="{ name: 'MetadataMySuffixView', params: { metadataId: hotelReservation.metadata.id } }">{{
                  hotelReservation.metadata.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="hotelReservation.oneCustomer">
                <router-link :to="{ name: 'OneCustomerMySuffixView', params: { oneCustomerId: hotelReservation.oneCustomer.id } }">{{
                  hotelReservation.oneCustomer.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'HotelReservationMySuffixView', params: { hotelReservationId: hotelReservation.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'HotelReservationMySuffixEdit', params: { hotelReservationId: hotelReservation.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(hotelReservation)"
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
          id="rcuApplicationApp.hotelReservation.delete.question"
          data-cy="hotelReservationDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-hotelReservation-heading" v-text="t$('rcuApplicationApp.hotelReservation.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-hotelReservation"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeHotelReservationMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./hotel-reservation-my-suffix.component.ts"></script>
