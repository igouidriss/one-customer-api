<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.email.home.createOrEditLabel"
          data-cy="EmailCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.email.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="email.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="email.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.email.type')" for="email-my-suffix-type"></label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="email-my-suffix-type"
              data-cy="type"
              :class="{ valid: !v$.type.$invalid, invalid: v$.type.$invalid }"
              v-model="v$.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.email.value')" for="email-my-suffix-value"></label>
            <input
              type="text"
              class="form-control"
              name="value"
              id="email-my-suffix-value"
              data-cy="value"
              :class="{ valid: !v$.value.$invalid, invalid: v$.value.$invalid }"
              v-model="v$.value.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.email.payload')" for="email-my-suffix-payload"></label>
            <select class="form-control" id="email-my-suffix-payload" data-cy="payload" name="payload" v-model="email.payload">
              <option :value="null"></option>
              <option
                :value="email.payload && payloadOption.id === email.payload.id ? email.payload : payloadOption"
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
<script lang="ts" src="./email-my-suffix-update.component.ts"></script>
