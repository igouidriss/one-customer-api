<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.sourceReference.home.createOrEditLabel"
          data-cy="SourceReferenceCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.sourceReference.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="sourceReference.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="sourceReference.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.sourceReference.source')"
              for="source-reference-my-suffix-source"
            ></label>
            <input
              type="text"
              class="form-control"
              name="source"
              id="source-reference-my-suffix-source"
              data-cy="source"
              :class="{ valid: !v$.source.$invalid, invalid: v$.source.$invalid }"
              v-model="v$.source.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.sourceReference.value')"
              for="source-reference-my-suffix-value"
            ></label>
            <input
              type="text"
              class="form-control"
              name="value"
              id="source-reference-my-suffix-value"
              data-cy="value"
              :class="{ valid: !v$.value.$invalid, invalid: v$.value.$invalid }"
              v-model="v$.value.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.sourceReference.goldenRecord')"
              for="source-reference-my-suffix-goldenRecord"
            ></label>
            <select
              class="form-control"
              id="source-reference-my-suffix-goldenRecord"
              data-cy="goldenRecord"
              name="goldenRecord"
              v-model="sourceReference.goldenRecord"
            >
              <option :value="null"></option>
              <option
                :value="
                  sourceReference.goldenRecord && goldenRecordOption.id === sourceReference.goldenRecord.id
                    ? sourceReference.goldenRecord
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
<script lang="ts" src="./source-reference-my-suffix-update.component.ts"></script>
