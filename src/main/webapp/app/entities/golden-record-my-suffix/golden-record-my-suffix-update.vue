<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.goldenRecord.home.createOrEditLabel"
          data-cy="GoldenRecordCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.goldenRecord.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="goldenRecord.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="goldenRecord.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.goldenRecord.aggregateId')"
              for="golden-record-my-suffix-aggregateId"
            ></label>
            <input
              type="text"
              class="form-control"
              name="aggregateId"
              id="golden-record-my-suffix-aggregateId"
              data-cy="aggregateId"
              :class="{ valid: !v$.aggregateId.$invalid, invalid: v$.aggregateId.$invalid }"
              v-model="v$.aggregateId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.goldenRecord.aggregateType')"
              for="golden-record-my-suffix-aggregateType"
            ></label>
            <input
              type="text"
              class="form-control"
              name="aggregateType"
              id="golden-record-my-suffix-aggregateType"
              data-cy="aggregateType"
              :class="{ valid: !v$.aggregateType.$invalid, invalid: v$.aggregateType.$invalid }"
              v-model="v$.aggregateType.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.goldenRecord.domaine')"
              for="golden-record-my-suffix-domaine"
            ></label>
            <input
              type="text"
              class="form-control"
              name="domaine"
              id="golden-record-my-suffix-domaine"
              data-cy="domaine"
              :class="{ valid: !v$.domaine.$invalid, invalid: v$.domaine.$invalid }"
              v-model="v$.domaine.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.goldenRecord.mdmId')"
              for="golden-record-my-suffix-mdmId"
            ></label>
            <input
              type="text"
              class="form-control"
              name="mdmId"
              id="golden-record-my-suffix-mdmId"
              data-cy="mdmId"
              :class="{ valid: !v$.mdmId.$invalid, invalid: v$.mdmId.$invalid }"
              v-model="v$.mdmId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.goldenRecord.source')"
              for="golden-record-my-suffix-source"
            ></label>
            <input
              type="text"
              class="form-control"
              name="source"
              id="golden-record-my-suffix-source"
              data-cy="source"
              :class="{ valid: !v$.source.$invalid, invalid: v$.source.$invalid }"
              v-model="v$.source.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.goldenRecord.recordTimestamp')"
              for="golden-record-my-suffix-recordTimestamp"
            ></label>
            <div class="d-flex">
              <input
                id="golden-record-my-suffix-recordTimestamp"
                data-cy="recordTimestamp"
                type="datetime-local"
                class="form-control"
                name="recordTimestamp"
                :class="{ valid: !v$.recordTimestamp.$invalid, invalid: v$.recordTimestamp.$invalid }"
                :value="convertDateTimeFromServer(v$.recordTimestamp.$model)"
                @change="updateInstantField('recordTimestamp', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.goldenRecord.cancelled')"
              for="golden-record-my-suffix-cancelled"
            ></label>
            <select
              class="form-control"
              id="golden-record-my-suffix-cancelled"
              data-cy="cancelled"
              name="cancelled"
              v-model="goldenRecord.cancelled"
            >
              <option :value="null"></option>
              <option
                :value="
                  goldenRecord.cancelled && cancelledOption.id === goldenRecord.cancelled.id ? goldenRecord.cancelled : cancelledOption
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
              v-text="t$('rcuApplicationApp.goldenRecord.payload')"
              for="golden-record-my-suffix-payload"
            ></label>
            <select
              class="form-control"
              id="golden-record-my-suffix-payload"
              data-cy="payload"
              name="payload"
              v-model="goldenRecord.payload"
            >
              <option :value="null"></option>
              <option
                :value="goldenRecord.payload && payloadOption.id === goldenRecord.payload.id ? goldenRecord.payload : payloadOption"
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
<script lang="ts" src="./golden-record-my-suffix-update.component.ts"></script>
