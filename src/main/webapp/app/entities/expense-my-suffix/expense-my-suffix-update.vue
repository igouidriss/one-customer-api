<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.expense.home.createOrEditLabel"
          data-cy="ExpenseCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.expense.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="expense.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="expense.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.expense.expenseType')"
              for="expense-my-suffix-expenseType"
            ></label>
            <input
              type="text"
              class="form-control"
              name="expenseType"
              id="expense-my-suffix-expenseType"
              data-cy="expenseType"
              :class="{ valid: !v$.expenseType.$invalid, invalid: v$.expenseType.$invalid }"
              v-model="v$.expenseType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.expense.amount')" for="expense-my-suffix-amount"></label>
            <input
              type="number"
              class="form-control"
              name="amount"
              id="expense-my-suffix-amount"
              data-cy="amount"
              :class="{ valid: !v$.amount.$invalid, invalid: v$.amount.$invalid }"
              v-model.number="v$.amount.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.expense.depositAmount')"
              for="expense-my-suffix-depositAmount"
            ></label>
            <input
              type="number"
              class="form-control"
              name="depositAmount"
              id="expense-my-suffix-depositAmount"
              data-cy="depositAmount"
              :class="{ valid: !v$.depositAmount.$invalid, invalid: v$.depositAmount.$invalid }"
              v-model.number="v$.depositAmount.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.expense.totalAmount')"
              for="expense-my-suffix-totalAmount"
            ></label>
            <input
              type="number"
              class="form-control"
              name="totalAmount"
              id="expense-my-suffix-totalAmount"
              data-cy="totalAmount"
              :class="{ valid: !v$.totalAmount.$invalid, invalid: v$.totalAmount.$invalid }"
              v-model.number="v$.totalAmount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.expense.shift')" for="expense-my-suffix-shift"></label>
            <input
              type="text"
              class="form-control"
              name="shift"
              id="expense-my-suffix-shift"
              data-cy="shift"
              :class="{ valid: !v$.shift.$invalid, invalid: v$.shift.$invalid }"
              v-model="v$.shift.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.expense.date')" for="expense-my-suffix-date"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="expense-my-suffix-date"
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
                id="expense-my-suffix-date"
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
              v-text="t$('rcuApplicationApp.expense.arrivalDate')"
              for="expense-my-suffix-arrivalDate"
            ></label>
            <div class="d-flex">
              <input
                id="expense-my-suffix-arrivalDate"
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
            <label class="form-control-label" v-text="t$('rcuApplicationApp.expense.leaveDate')" for="expense-my-suffix-leaveDate"></label>
            <div class="d-flex">
              <input
                id="expense-my-suffix-leaveDate"
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
              v-text="t$('rcuApplicationApp.expense.guestCount')"
              for="expense-my-suffix-guestCount"
            ></label>
            <input
              type="number"
              class="form-control"
              name="guestCount"
              id="expense-my-suffix-guestCount"
              data-cy="guestCount"
              :class="{ valid: !v$.guestCount.$invalid, invalid: v$.guestCount.$invalid }"
              v-model.number="v$.guestCount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.expense.hotelName')" for="expense-my-suffix-hotelName"></label>
            <input
              type="text"
              class="form-control"
              name="hotelName"
              id="expense-my-suffix-hotelName"
              data-cy="hotelName"
              :class="{ valid: !v$.hotelName.$invalid, invalid: v$.hotelName.$invalid }"
              v-model="v$.hotelName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.expense.hotelId')" for="expense-my-suffix-hotelId"></label>
            <input
              type="text"
              class="form-control"
              name="hotelId"
              id="expense-my-suffix-hotelId"
              data-cy="hotelId"
              :class="{ valid: !v$.hotelId.$invalid, invalid: v$.hotelId.$invalid }"
              v-model="v$.hotelId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.expense.restaurantName')"
              for="expense-my-suffix-restaurantName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="restaurantName"
              id="expense-my-suffix-restaurantName"
              data-cy="restaurantName"
              :class="{ valid: !v$.restaurantName.$invalid, invalid: v$.restaurantName.$invalid }"
              v-model="v$.restaurantName.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.expense.restaurantId')"
              for="expense-my-suffix-restaurantId"
            ></label>
            <input
              type="text"
              class="form-control"
              name="restaurantId"
              id="expense-my-suffix-restaurantId"
              data-cy="restaurantId"
              :class="{ valid: !v$.restaurantId.$invalid, invalid: v$.restaurantId.$invalid }"
              v-model="v$.restaurantId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.expense.clientId')" for="expense-my-suffix-clientId"></label>
            <input
              type="text"
              class="form-control"
              name="clientId"
              id="expense-my-suffix-clientId"
              data-cy="clientId"
              :class="{ valid: !v$.clientId.$invalid, invalid: v$.clientId.$invalid }"
              v-model="v$.clientId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.expense.metadata')" for="expense-my-suffix-metadata"></label>
            <select class="form-control" id="expense-my-suffix-metadata" data-cy="metadata" name="metadata" v-model="expense.metadata">
              <option :value="null"></option>
              <option
                :value="expense.metadata && metadataOption.id === expense.metadata.id ? expense.metadata : metadataOption"
                v-for="metadataOption in metadata"
                :key="metadataOption.id"
              >
                {{ metadataOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.expense.expenses')" for="expense-my-suffix-expenses"></label>
            <select class="form-control" id="expense-my-suffix-expenses" data-cy="expenses" name="expenses" v-model="expense.expenses">
              <option :value="null"></option>
              <option
                :value="expense.expenses && expensesOption.id === expense.expenses.id ? expense.expenses : expensesOption"
                v-for="expensesOption in expenses"
                :key="expensesOption.id"
              >
                {{ expensesOption.id }}
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
<script lang="ts" src="./expense-my-suffix-update.component.ts"></script>
