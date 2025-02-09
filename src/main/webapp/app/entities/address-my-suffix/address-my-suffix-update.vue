<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.address.home.createOrEditLabel"
          data-cy="AddressCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.address.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="address.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="address.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.address.type')" for="address-my-suffix-type"></label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="address-my-suffix-type"
              data-cy="type"
              :class="{ valid: !v$.type.$invalid, invalid: v$.type.$invalid }"
              v-model="v$.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.address.zipCode')" for="address-my-suffix-zipCode"></label>
            <input
              type="text"
              class="form-control"
              name="zipCode"
              id="address-my-suffix-zipCode"
              data-cy="zipCode"
              :class="{ valid: !v$.zipCode.$invalid, invalid: v$.zipCode.$invalid }"
              v-model="v$.zipCode.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.address.city')" for="address-my-suffix-city"></label>
            <input
              type="text"
              class="form-control"
              name="city"
              id="address-my-suffix-city"
              data-cy="city"
              :class="{ valid: !v$.city.$invalid, invalid: v$.city.$invalid }"
              v-model="v$.city.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.address.country')" for="address-my-suffix-country"></label>
            <input
              type="text"
              class="form-control"
              name="country"
              id="address-my-suffix-country"
              data-cy="country"
              :class="{ valid: !v$.country.$invalid, invalid: v$.country.$invalid }"
              v-model="v$.country.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.address.line1')" for="address-my-suffix-line1"></label>
            <input
              type="text"
              class="form-control"
              name="line1"
              id="address-my-suffix-line1"
              data-cy="line1"
              :class="{ valid: !v$.line1.$invalid, invalid: v$.line1.$invalid }"
              v-model="v$.line1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.address.line2')" for="address-my-suffix-line2"></label>
            <input
              type="text"
              class="form-control"
              name="line2"
              id="address-my-suffix-line2"
              data-cy="line2"
              :class="{ valid: !v$.line2.$invalid, invalid: v$.line2.$invalid }"
              v-model="v$.line2.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.address.line3')" for="address-my-suffix-line3"></label>
            <input
              type="text"
              class="form-control"
              name="line3"
              id="address-my-suffix-line3"
              data-cy="line3"
              :class="{ valid: !v$.line3.$invalid, invalid: v$.line3.$invalid }"
              v-model="v$.line3.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.address.payload')" for="address-my-suffix-payload"></label>
            <select class="form-control" id="address-my-suffix-payload" data-cy="payload" name="payload" v-model="address.payload">
              <option :value="null"></option>
              <option
                :value="address.payload && payloadOption.id === address.payload.id ? address.payload : payloadOption"
                v-for="payloadOption in payloads"
                :key="payloadOption.id"
              >
                {{ payloadOption.id }}
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
<script lang="ts" src="./address-my-suffix-update.component.ts"></script>
