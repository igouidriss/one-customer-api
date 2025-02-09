<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.metadata.home.createOrEditLabel"
          data-cy="MetadataCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.metadata.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="metadata.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="metadata.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('rcuApplicationApp.metadata.idEvent')" for="metadata-my-suffix-idEvent"></label>
            <input
              type="text"
              class="form-control"
              name="idEvent"
              id="metadata-my-suffix-idEvent"
              data-cy="idEvent"
              :class="{ valid: !v$.idEvent.$invalid, invalid: v$.idEvent.$invalid }"
              v-model="v$.idEvent.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.metadata.metaTimestamp')"
              for="metadata-my-suffix-metaTimestamp"
            ></label>
            <div class="d-flex">
              <input
                id="metadata-my-suffix-metaTimestamp"
                data-cy="metaTimestamp"
                type="datetime-local"
                class="form-control"
                name="metaTimestamp"
                :class="{ valid: !v$.metaTimestamp.$invalid, invalid: v$.metaTimestamp.$invalid }"
                :value="convertDateTimeFromServer(v$.metaTimestamp.$model)"
                @change="updateInstantField('metaTimestamp', $event)"
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
<script lang="ts" src="./metadata-my-suffix-update.component.ts"></script>
