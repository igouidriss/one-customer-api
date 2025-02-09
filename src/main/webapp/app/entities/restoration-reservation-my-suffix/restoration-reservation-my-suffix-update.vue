<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.restorationReservation.home.createOrEditLabel"
          data-cy="RestorationReservationCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.restorationReservation.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="restorationReservation.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="restorationReservation.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.aggregateId')"
              for="restoration-reservation-my-suffix-aggregateId"
            ></label>
            <input
              type="text"
              class="form-control"
              name="aggregateId"
              id="restoration-reservation-my-suffix-aggregateId"
              data-cy="aggregateId"
              :class="{ valid: !v$.aggregateId.$invalid, invalid: v$.aggregateId.$invalid }"
              v-model="v$.aggregateId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.aggregateType')"
              for="restoration-reservation-my-suffix-aggregateType"
            ></label>
            <input
              type="text"
              class="form-control"
              name="aggregateType"
              id="restoration-reservation-my-suffix-aggregateType"
              data-cy="aggregateType"
              :class="{ valid: !v$.aggregateType.$invalid, invalid: v$.aggregateType.$invalid }"
              v-model="v$.aggregateType.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.clientId')"
              for="restoration-reservation-my-suffix-clientId"
            ></label>
            <input
              type="text"
              class="form-control"
              name="clientId"
              id="restoration-reservation-my-suffix-clientId"
              data-cy="clientId"
              :class="{ valid: !v$.clientId.$invalid, invalid: v$.clientId.$invalid }"
              v-model="v$.clientId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.domaine')"
              for="restoration-reservation-my-suffix-domaine"
            ></label>
            <input
              type="text"
              class="form-control"
              name="domaine"
              id="restoration-reservation-my-suffix-domaine"
              data-cy="domaine"
              :class="{ valid: !v$.domaine.$invalid, invalid: v$.domaine.$invalid }"
              v-model="v$.domaine.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.source')"
              for="restoration-reservation-my-suffix-source"
            ></label>
            <input
              type="text"
              class="form-control"
              name="source"
              id="restoration-reservation-my-suffix-source"
              data-cy="source"
              :class="{ valid: !v$.source.$invalid, invalid: v$.source.$invalid }"
              v-model="v$.source.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.reservationTimestamp')"
              for="restoration-reservation-my-suffix-reservationTimestamp"
            ></label>
            <div class="d-flex">
              <input
                id="restoration-reservation-my-suffix-reservationTimestamp"
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
              v-text="t$('rcuApplicationApp.restorationReservation.projection')"
              for="restoration-reservation-my-suffix-projection"
            ></label>
            <input
              type="text"
              class="form-control"
              name="projection"
              id="restoration-reservation-my-suffix-projection"
              data-cy="projection"
              :class="{ valid: !v$.projection.$invalid, invalid: v$.projection.$invalid }"
              v-model="v$.projection.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.date')"
              for="restoration-reservation-my-suffix-date"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="restoration-reservation-my-suffix-date"
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
                id="restoration-reservation-my-suffix-date"
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
              v-text="t$('rcuApplicationApp.restorationReservation.depositAmount')"
              for="restoration-reservation-my-suffix-depositAmount"
            ></label>
            <input
              type="number"
              class="form-control"
              name="depositAmount"
              id="restoration-reservation-my-suffix-depositAmount"
              data-cy="depositAmount"
              :class="{ valid: !v$.depositAmount.$invalid, invalid: v$.depositAmount.$invalid }"
              v-model.number="v$.depositAmount.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.totalAmount')"
              for="restoration-reservation-my-suffix-totalAmount"
            ></label>
            <input
              type="number"
              class="form-control"
              name="totalAmount"
              id="restoration-reservation-my-suffix-totalAmount"
              data-cy="totalAmount"
              :class="{ valid: !v$.totalAmount.$invalid, invalid: v$.totalAmount.$invalid }"
              v-model.number="v$.totalAmount.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.shift')"
              for="restoration-reservation-my-suffix-shift"
            ></label>
            <input
              type="text"
              class="form-control"
              name="shift"
              id="restoration-reservation-my-suffix-shift"
              data-cy="shift"
              :class="{ valid: !v$.shift.$invalid, invalid: v$.shift.$invalid }"
              v-model="v$.shift.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.guestCount')"
              for="restoration-reservation-my-suffix-guestCount"
            ></label>
            <input
              type="number"
              class="form-control"
              name="guestCount"
              id="restoration-reservation-my-suffix-guestCount"
              data-cy="guestCount"
              :class="{ valid: !v$.guestCount.$invalid, invalid: v$.guestCount.$invalid }"
              v-model.number="v$.guestCount.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.arrivalDate')"
              for="restoration-reservation-my-suffix-arrivalDate"
            ></label>
            <div class="d-flex">
              <input
                id="restoration-reservation-my-suffix-arrivalDate"
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
              v-text="t$('rcuApplicationApp.restorationReservation.restaurantName')"
              for="restoration-reservation-my-suffix-restaurantName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="restaurantName"
              id="restoration-reservation-my-suffix-restaurantName"
              data-cy="restaurantName"
              :class="{ valid: !v$.restaurantName.$invalid, invalid: v$.restaurantName.$invalid }"
              v-model="v$.restaurantName.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.restaurantId')"
              for="restoration-reservation-my-suffix-restaurantId"
            ></label>
            <input
              type="text"
              class="form-control"
              name="restaurantId"
              id="restoration-reservation-my-suffix-restaurantId"
              data-cy="restaurantId"
              :class="{ valid: !v$.restaurantId.$invalid, invalid: v$.restaurantId.$invalid }"
              v-model="v$.restaurantId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.restorationReservation.cancelled')"
              for="restoration-reservation-my-suffix-cancelled"
            ></label>
            <select
              class="form-control"
              id="restoration-reservation-my-suffix-cancelled"
              data-cy="cancelled"
              name="cancelled"
              v-model="restorationReservation.cancelled"
            >
              <option :value="null"></option>
              <option
                :value="
                  restorationReservation.cancelled && cancelledOption.id === restorationReservation.cancelled.id
                    ? restorationReservation.cancelled
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
              v-text="t$('rcuApplicationApp.restorationReservation.expenses')"
              for="restoration-reservation-my-suffix-expenses"
            ></label>
            <select
              class="form-control"
              id="restoration-reservation-my-suffix-expenses"
              data-cy="expenses"
              name="expenses"
              v-model="restorationReservation.expenses"
            >
              <option :value="null"></option>
              <option
                :value="
                  restorationReservation.expenses && expensesOption.id === restorationReservation.expenses.id
                    ? restorationReservation.expenses
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
              v-text="t$('rcuApplicationApp.restorationReservation.metadata')"
              for="restoration-reservation-my-suffix-metadata"
            ></label>
            <select
              class="form-control"
              id="restoration-reservation-my-suffix-metadata"
              data-cy="metadata"
              name="metadata"
              v-model="restorationReservation.metadata"
            >
              <option :value="null"></option>
              <option
                :value="
                  restorationReservation.metadata && metadataOption.id === restorationReservation.metadata.id
                    ? restorationReservation.metadata
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
              v-text="t$('rcuApplicationApp.restorationReservation.oneCustomer')"
              for="restoration-reservation-my-suffix-oneCustomer"
            ></label>
            <select
              class="form-control"
              id="restoration-reservation-my-suffix-oneCustomer"
              data-cy="oneCustomer"
              name="oneCustomer"
              v-model="restorationReservation.oneCustomer"
            >
              <option :value="null"></option>
              <option
                :value="
                  restorationReservation.oneCustomer && oneCustomerOption.id === restorationReservation.oneCustomer.id
                    ? restorationReservation.oneCustomer
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
<script lang="ts" src="./restoration-reservation-my-suffix-update.component.ts"></script>
