<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="restorationReservation">
        <h2 class="jh-entity-heading" data-cy="restorationReservationDetailsHeading">
          <span v-text="t$('rcuApplicationApp.restorationReservation.detail.title')"></span> {{ restorationReservation.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.aggregateId')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.aggregateId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.aggregateType')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.aggregateType }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.clientId')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.clientId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.domaine')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.domaine }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.source')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.source }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.reservationTimestamp')"></span>
          </dt>
          <dd>
            <span v-if="restorationReservation.reservationTimestamp">{{
              formatDateLong(restorationReservation.reservationTimestamp)
            }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.projection')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.projection }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.date')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.date }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.depositAmount')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.depositAmount }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.totalAmount')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.totalAmount }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.shift')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.shift }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.guestCount')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.guestCount }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.arrivalDate')"></span>
          </dt>
          <dd>
            <span v-if="restorationReservation.arrivalDate">{{ formatDateLong(restorationReservation.arrivalDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.restaurantName')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.restaurantName }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.restaurantId')"></span>
          </dt>
          <dd>
            <span>{{ restorationReservation.restaurantId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.cancelled')"></span>
          </dt>
          <dd>
            <div v-if="restorationReservation.cancelled">
              <router-link :to="{ name: 'CancelledMySuffixView', params: { cancelledId: restorationReservation.cancelled.id } }">{{
                restorationReservation.cancelled.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.expenses')"></span>
          </dt>
          <dd>
            <div v-if="restorationReservation.expenses">
              <router-link :to="{ name: 'ExpensesMySuffixView', params: { expensesId: restorationReservation.expenses.id } }">{{
                restorationReservation.expenses.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.metadata')"></span>
          </dt>
          <dd>
            <div v-if="restorationReservation.metadata">
              <router-link :to="{ name: 'MetadataMySuffixView', params: { metadataId: restorationReservation.metadata.id } }">{{
                restorationReservation.metadata.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.restorationReservation.oneCustomer')"></span>
          </dt>
          <dd>
            <div v-if="restorationReservation.oneCustomer">
              <router-link :to="{ name: 'OneCustomerMySuffixView', params: { oneCustomerId: restorationReservation.oneCustomer.id } }">{{
                restorationReservation.oneCustomer.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="restorationReservation.id"
          :to="{ name: 'RestorationReservationMySuffixEdit', params: { restorationReservationId: restorationReservation.id } }"
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

<script lang="ts" src="./restoration-reservation-my-suffix-details.component.ts"></script>
