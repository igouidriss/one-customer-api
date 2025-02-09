<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.payload.home.createOrEditLabel"
          data-cy="PayloadCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.payload.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="payload.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="payload.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.payload.lastName')" for="payload-my-suffix-lastName"></label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="payload-my-suffix-lastName"
              data-cy="lastName"
              :class="{ valid: !v$.lastName.$invalid, invalid: v$.lastName.$invalid }"
              v-model="v$.lastName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.payload.firstName')" for="payload-my-suffix-firstName"></label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="payload-my-suffix-firstName"
              data-cy="firstName"
              :class="{ valid: !v$.firstName.$invalid, invalid: v$.firstName.$invalid }"
              v-model="v$.firstName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.payload.birthDate')" for="payload-my-suffix-birthDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="payload-my-suffix-birthDate"
                  v-model="v$.birthDate.$model"
                  name="birthDate"
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
                id="payload-my-suffix-birthDate"
                data-cy="birthDate"
                type="text"
                class="form-control"
                name="birthDate"
                :class="{ valid: !v$.birthDate.$invalid, invalid: v$.birthDate.$invalid }"
                v-model="v$.birthDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.payload.lang')" for="payload-my-suffix-lang"></label>
            <input
              type="text"
              class="form-control"
              name="lang"
              id="payload-my-suffix-lang"
              data-cy="lang"
              :class="{ valid: !v$.lang.$invalid, invalid: v$.lang.$invalid }"
              v-model="v$.lang.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.payload.isVip')" for="payload-my-suffix-isVip"></label>
            <input
              type="checkbox"
              class="form-check"
              name="isVip"
              id="payload-my-suffix-isVip"
              data-cy="isVip"
              :class="{ valid: !v$.isVip.$invalid, invalid: v$.isVip.$invalid }"
              v-model="v$.isVip.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.payload.metadata')" for="payload-my-suffix-metadata"></label>
            <select class="form-control" id="payload-my-suffix-metadata" data-cy="metadata" name="metadata" v-model="payload.metadata">
              <option :value="null"></option>
              <option
                :value="payload.metadata && metadataOption.id === payload.metadata.id ? payload.metadata : metadataOption"
                v-for="metadataOption in metadata"
                :key="metadataOption.id"
              >
                {{ metadataOption.id }}
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
<script lang="ts" src="./payload-my-suffix-update.component.ts"></script>
