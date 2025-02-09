<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.oneCustomer.home.createOrEditLabel"
          data-cy="OneCustomerCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.oneCustomer.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="oneCustomer.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="oneCustomer.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.oneCustomer.domaine')"
              for="one-customer-my-suffix-domaine"
            ></label>
            <input
              type="text"
              class="form-control"
              name="domaine"
              id="one-customer-my-suffix-domaine"
              data-cy="domaine"
              :class="{ valid: !v$.domaine.$invalid, invalid: v$.domaine.$invalid }"
              v-model="v$.domaine.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.oneCustomer.aggregateId')"
              for="one-customer-my-suffix-aggregateId"
            ></label>
            <input
              type="text"
              class="form-control"
              name="aggregateId"
              id="one-customer-my-suffix-aggregateId"
              data-cy="aggregateId"
              :class="{ valid: !v$.aggregateId.$invalid, invalid: v$.aggregateId.$invalid }"
              v-model="v$.aggregateId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.oneCustomer.aggregateType')"
              for="one-customer-my-suffix-aggregateType"
            ></label>
            <input
              type="text"
              class="form-control"
              name="aggregateType"
              id="one-customer-my-suffix-aggregateType"
              data-cy="aggregateType"
              :class="{ valid: !v$.aggregateType.$invalid, invalid: v$.aggregateType.$invalid }"
              v-model="v$.aggregateType.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.oneCustomer.timestamp')"
              for="one-customer-my-suffix-timestamp"
            ></label>
            <div class="d-flex">
              <input
                id="one-customer-my-suffix-timestamp"
                data-cy="timestamp"
                type="datetime-local"
                class="form-control"
                name="timestamp"
                :class="{ valid: !v$.timestamp.$invalid, invalid: v$.timestamp.$invalid }"
                :value="convertDateTimeFromServer(v$.timestamp.$model)"
                @change="updateInstantField('timestamp', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.oneCustomer.goldenRecord')"
              for="one-customer-my-suffix-goldenRecord"
            ></label>
            <select
              class="form-control"
              id="one-customer-my-suffix-goldenRecord"
              data-cy="goldenRecord"
              name="goldenRecord"
              v-model="oneCustomer.goldenRecord"
            >
              <option :value="null"></option>
              <option
                :value="
                  oneCustomer.goldenRecord && goldenRecordOption.id === oneCustomer.goldenRecord.id
                    ? oneCustomer.goldenRecord
                    : goldenRecordOption
                "
                v-for="goldenRecordOption in goldenRecords"
                :key="goldenRecordOption.id"
              >
                {{ goldenRecordOption.id }}
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
<script lang="ts" src="./one-customer-my-suffix-update.component.ts"></script>
