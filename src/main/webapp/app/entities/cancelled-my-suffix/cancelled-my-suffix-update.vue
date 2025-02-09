<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.cancelled.home.createOrEditLabel"
          data-cy="CancelledCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.cancelled.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="cancelled.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="cancelled.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.cancelled.cancelReason')"
              for="cancelled-my-suffix-cancelReason"
            ></label>
            <input
              type="text"
              class="form-control"
              name="cancelReason"
              id="cancelled-my-suffix-cancelReason"
              data-cy="cancelReason"
              :class="{ valid: !v$.cancelReason.$invalid, invalid: v$.cancelReason.$invalid }"
              v-model="v$.cancelReason.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.cancelled.isItCancelled')"
              for="cancelled-my-suffix-isItCancelled"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="isItCancelled"
              id="cancelled-my-suffix-isItCancelled"
              data-cy="isItCancelled"
              :class="{ valid: !v$.isItCancelled.$invalid, invalid: v$.isItCancelled.$invalid }"
              v-model="v$.isItCancelled.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.cancelled.cancelDate')"
              for="cancelled-my-suffix-cancelDate"
            ></label>
            <div class="d-flex">
              <input
                id="cancelled-my-suffix-cancelDate"
                data-cy="cancelDate"
                type="datetime-local"
                class="form-control"
                name="cancelDate"
                :class="{ valid: !v$.cancelDate.$invalid, invalid: v$.cancelDate.$invalid }"
                :value="convertDateTimeFromServer(v$.cancelDate.$model)"
                @change="updateInstantField('cancelDate', $event)"
              />
            </div>
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
<script lang="ts" src="./cancelled-my-suffix-update.component.ts"></script>
