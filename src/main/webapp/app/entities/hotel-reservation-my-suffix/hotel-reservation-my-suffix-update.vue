<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.hotelReservation.home.createOrEditLabel"
          data-cy="HotelReservationCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.hotelReservation.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="hotelReservation.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="hotelReservation.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.aggregateId')"
              for="hotel-reservation-my-suffix-aggregateId"
            ></label>
            <input
              type="text"
              class="form-control"
              name="aggregateId"
              id="hotel-reservation-my-suffix-aggregateId"
              data-cy="aggregateId"
              :class="{ valid: !v$.aggregateId.$invalid, invalid: v$.aggregateId.$invalid }"
              v-model="v$.aggregateId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.aggregateType')"
              for="hotel-reservation-my-suffix-aggregateType"
            ></label>
            <input
              type="text"
              class="form-control"
              name="aggregateType"
              id="hotel-reservation-my-suffix-aggregateType"
              data-cy="aggregateType"
              :class="{ valid: !v$.aggregateType.$invalid, invalid: v$.aggregateType.$invalid }"
              v-model="v$.aggregateType.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.clientId')"
              for="hotel-reservation-my-suffix-clientId"
            ></label>
            <input
              type="text"
              class="form-control"
              name="clientId"
              id="hotel-reservation-my-suffix-clientId"
              data-cy="clientId"
              :class="{ valid: !v$.clientId.$invalid, invalid: v$.clientId.$invalid }"
              v-model="v$.clientId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.domaine')"
              for="hotel-reservation-my-suffix-domaine"
            ></label>
            <input
              type="text"
              class="form-control"
              name="domaine"
              id="hotel-reservation-my-suffix-domaine"
              data-cy="domaine"
              :class="{ valid: !v$.domaine.$invalid, invalid: v$.domaine.$invalid }"
              v-model="v$.domaine.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.source')"
              for="hotel-reservation-my-suffix-source"
            ></label>
            <input
              type="text"
              class="form-control"
              name="source"
              id="hotel-reservation-my-suffix-source"
              data-cy="source"
              :class="{ valid: !v$.source.$invalid, invalid: v$.source.$invalid }"
              v-model="v$.source.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.reservationTimestamp')"
              for="hotel-reservation-my-suffix-reservationTimestamp"
            ></label>
            <div class="d-flex">
              <input
                id="hotel-reservation-my-suffix-reservationTimestamp"
                data-cy="reservationTimestamp"
                type="datetime-local"
                class="form-control"
                name="reservationTimestamp"
                :class="{ valid: !v$.reservationTimestamp.$invalid, invalid: v$.reservationTimestamp.$invalid }"
                :value="convertDateTimeFromServer(v$.reservationTimestamp.$model)"
                @change="updateInstantField('reservationTimestamp', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.projection')"
              for="hotel-reservation-my-suffix-projection"
            ></label>
            <input
              type="text"
              class="form-control"
              name="projection"
              id="hotel-reservation-my-suffix-projection"
              data-cy="projection"
              :class="{ valid: !v$.projection.$invalid, invalid: v$.projection.$invalid }"
              v-model="v$.projection.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.date')"
              for="hotel-reservation-my-suffix-date"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="hotel-reservation-my-suffix-date"
                  v-model="v$.date.$model"
                  name="date"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="hotel-reservation-my-suffix-date"
                data-cy="date"
                type="text"
                class="form-control"
                name="date"
                :class="{ valid: !v$.date.$invalid, invalid: v$.date.$invalid }"
                v-model="v$.date.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.totalAmount')"
              for="hotel-reservation-my-suffix-totalAmount"
            ></label>
            <input
              type="number"
              class="form-control"
              name="totalAmount"
              id="hotel-reservation-my-suffix-totalAmount"
              data-cy="totalAmount"
              :class="{ valid: !v$.totalAmount.$invalid, invalid: v$.totalAmount.$invalid }"
              v-model.number="v$.totalAmount.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.arrivalDate')"
              for="hotel-reservation-my-suffix-arrivalDate"
            ></label>
            <div class="d-flex">
              <input
                id="hotel-reservation-my-suffix-arrivalDate"
                data-cy="arrivalDate"
                type="datetime-local"
                class="form-control"
                name="arrivalDate"
                :class="{ valid: !v$.arrivalDate.$invalid, invalid: v$.arrivalDate.$invalid }"
                :value="convertDateTimeFromServer(v$.arrivalDate.$model)"
                @change="updateInstantField('arrivalDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.leaveDate')"
              for="hotel-reservation-my-suffix-leaveDate"
            ></label>
            <div class="d-flex">
              <input
                id="hotel-reservation-my-suffix-leaveDate"
                data-cy="leaveDate"
                type="datetime-local"
                class="form-control"
                name="leaveDate"
                :class="{ valid: !v$.leaveDate.$invalid, invalid: v$.leaveDate.$invalid }"
                :value="convertDateTimeFromServer(v$.leaveDate.$model)"
                @change="updateInstantField('leaveDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.guestCount')"
              for="hotel-reservation-my-suffix-guestCount"
            ></label>
            <input
              type="number"
              class="form-control"
              name="guestCount"
              id="hotel-reservation-my-suffix-guestCount"
              data-cy="guestCount"
              :class="{ valid: !v$.guestCount.$invalid, invalid: v$.guestCount.$invalid }"
              v-model.number="v$.guestCount.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.hotelName')"
              for="hotel-reservation-my-suffix-hotelName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="hotelName"
              id="hotel-reservation-my-suffix-hotelName"
              data-cy="hotelName"
              :class="{ valid: !v$.hotelName.$invalid, invalid: v$.hotelName.$invalid }"
              v-model="v$.hotelName.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.hotelId')"
              for="hotel-reservation-my-suffix-hotelId"
            ></label>
            <input
              type="text"
              class="form-control"
              name="hotelId"
              id="hotel-reservation-my-suffix-hotelId"
              data-cy="hotelId"
              :class="{ valid: !v$.hotelId.$invalid, invalid: v$.hotelId.$invalid }"
              v-model="v$.hotelId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.cancelled')"
              for="hotel-reservation-my-suffix-cancelled"
            ></label>
            <select
              class="form-control"
              id="hotel-reservation-my-suffix-cancelled"
              data-cy="cancelled"
              name="cancelled"
              v-model="hotelReservation.cancelled"
            >
              <option :value="null"></option>
              <option
                :value="
                  hotelReservation.cancelled && cancelledOption.id === hotelReservation.cancelled.id
                    ? hotelReservation.cancelled
                    : cancelledOption
                "
                v-for="cancelledOption in cancelleds"
                :key="cancelledOption.id"
              >
                {{ cancelledOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.expenses')"
              for="hotel-reservation-my-suffix-expenses"
            ></label>
            <select
              class="form-control"
              id="hotel-reservation-my-suffix-expenses"
              data-cy="expenses"
              name="expenses"
              v-model="hotelReservation.expenses"
            >
              <option :value="null"></option>
              <option
                :value="
                  hotelReservation.expenses && expensesOption.id === hotelReservation.expenses.id
                    ? hotelReservation.expenses
                    : expensesOption
                "
                v-for="expensesOption in expenses"
                :key="expensesOption.id"
              >
                {{ expensesOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.metadata')"
              for="hotel-reservation-my-suffix-metadata"
            ></label>
            <select
              class="form-control"
              id="hotel-reservation-my-suffix-metadata"
              data-cy="metadata"
              name="metadata"
              v-model="hotelReservation.metadata"
            >
              <option :value="null"></option>
              <option
                :value="
                  hotelReservation.metadata && metadataOption.id === hotelReservation.metadata.id
                    ? hotelReservation.metadata
                    : metadataOption
                "
                v-for="metadataOption in metadata"
                :key="metadataOption.id"
              >
                {{ metadataOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.hotelReservation.oneCustomer')"
              for="hotel-reservation-my-suffix-oneCustomer"
            ></label>
            <select
              class="form-control"
              id="hotel-reservation-my-suffix-oneCustomer"
              data-cy="oneCustomer"
              name="oneCustomer"
              v-model="hotelReservation.oneCustomer"
            >
              <option :value="null"></option>
              <option
                :value="
                  hotelReservation.oneCustomer && oneCustomerOption.id === hotelReservation.oneCustomer.id
                    ? hotelReservation.oneCustomer
                    : oneCustomerOption
                "
                v-for="oneCustomerOption in oneCustomers"
                :key="oneCustomerOption.id"
              >
                {{ oneCustomerOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./hotel-reservation-my-suffix-update.component.ts"></script>
