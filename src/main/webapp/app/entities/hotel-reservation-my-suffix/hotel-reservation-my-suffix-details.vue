<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="hotelReservation">
        <h2 class="jh-entity-heading" data-cy="hotelReservationDetailsHeading">
          <span v-text="t$('rcuApplicationApp.hotelReservation.detail.title')"></span> {{ hotelReservation.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.aggregateId')"></span>
          </dt>
          <dd>
            <span>{{ hotelReservation.aggregateId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.aggregateType')"></span>
          </dt>
          <dd>
            <span>{{ hotelReservation.aggregateType }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.clientId')"></span>
          </dt>
          <dd>
            <span>{{ hotelReservation.clientId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.domaine')"></span>
          </dt>
          <dd>
            <span>{{ hotelReservation.domaine }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.source')"></span>
          </dt>
          <dd>
            <span>{{ hotelReservation.source }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.reservationTimestamp')"></span>
          </dt>
          <dd>
            <span v-if="hotelReservation.reservationTimestamp">{{ formatDateLong(hotelReservation.reservationTimestamp) }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.projection')"></span>
          </dt>
          <dd>
            <span>{{ hotelReservation.projection }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.date')"></span>
          </dt>
          <dd>
            <span>{{ hotelReservation.date }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.totalAmount')"></span>
          </dt>
          <dd>
            <span>{{ hotelReservation.totalAmount }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.arrivalDate')"></span>
          </dt>
          <dd>
            <span v-if="hotelReservation.arrivalDate">{{ formatDateLong(hotelReservation.arrivalDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.leaveDate')"></span>
          </dt>
          <dd>
            <span v-if="hotelReservation.leaveDate">{{ formatDateLong(hotelReservation.leaveDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.guestCount')"></span>
          </dt>
          <dd>
            <span>{{ hotelReservation.guestCount }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.hotelName')"></span>
          </dt>
          <dd>
            <span>{{ hotelReservation.hotelName }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.hotelId')"></span>
          </dt>
          <dd>
            <span>{{ hotelReservation.hotelId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.cancelled')"></span>
          </dt>
          <dd>
            <div v-if="hotelReservation.cancelled">
              <router-link :to="{ name: 'CancelledMySuffixView', params: { cancelledId: hotelReservation.cancelled.id } }">{{
                hotelReservation.cancelled.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.expenses')"></span>
          </dt>
          <dd>
            <div v-if="hotelReservation.expenses">
              <router-link :to="{ name: 'ExpensesMySuffixView', params: { expensesId: hotelReservation.expenses.id } }">{{
                hotelReservation.expenses.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.metadata')"></span>
          </dt>
          <dd>
            <div v-if="hotelReservation.metadata">
              <router-link :to="{ name: 'MetadataMySuffixView', params: { metadataId: hotelReservation.metadata.id } }">{{
                hotelReservation.metadata.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.hotelReservation.oneCustomer')"></span>
          </dt>
          <dd>
            <div v-if="hotelReservation.oneCustomer">
              <router-link :to="{ name: 'OneCustomerMySuffixView', params: { oneCustomerId: hotelReservation.oneCustomer.id } }">{{
                hotelReservation.oneCustomer.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="hotelReservation.id"
          :to="{ name: 'HotelReservationMySuffixEdit', params: { hotelReservationId: hotelReservation.id } }"
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

<script lang="ts" src="./hotel-reservation-my-suffix-details.component.ts"></script>
