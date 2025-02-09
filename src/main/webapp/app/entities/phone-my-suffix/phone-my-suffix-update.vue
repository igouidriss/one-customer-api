<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.phone.home.createOrEditLabel"
          data-cy="PhoneCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.phone.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="phone.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="phone.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.phone.type')" for="phone-my-suffix-type"></label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="phone-my-suffix-type"
              data-cy="type"
              :class="{ valid: !v$.type.$invalid, invalid: v$.type.$invalid }"
              v-model="v$.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.phone.number')" for="phone-my-suffix-number"></label>
            <input
              type="text"
              class="form-control"
              name="number"
              id="phone-my-suffix-number"
              data-cy="number"
              :class="{ valid: !v$.number.$invalid, invalid: v$.number.$invalid }"
              v-model="v$.number.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.phone.payload')" for="phone-my-suffix-payload"></label>
            <select class="form-control" id="phone-my-suffix-payload" data-cy="payload" name="payload" v-model="phone.payload">
              <option :value="null"></option>
              <option
                :value="phone.payload && payloadOption.id === phone.payload.id ? phone.payload : payloadOption"
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
<script lang="ts" src="./phone-my-suffix-update.component.ts"></script>
