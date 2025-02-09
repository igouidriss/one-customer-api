<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="goldenRecord">
        <h2 class="jh-entity-heading" data-cy="goldenRecordDetailsHeading">
          <span v-text="t$('rcuApplicationApp.goldenRecord.detail.title')"></span> {{ goldenRecord.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.goldenRecord.aggregateId')"></span>
          </dt>
          <dd>
            <span>{{ goldenRecord.aggregateId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.goldenRecord.aggregateType')"></span>
          </dt>
          <dd>
            <span>{{ goldenRecord.aggregateType }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.goldenRecord.domaine')"></span>
          </dt>
          <dd>
            <span>{{ goldenRecord.domaine }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.goldenRecord.mdmId')"></span>
          </dt>
          <dd>
            <span>{{ goldenRecord.mdmId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.goldenRecord.source')"></span>
          </dt>
          <dd>
            <span>{{ goldenRecord.source }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.goldenRecord.recordTimestamp')"></span>
          </dt>
          <dd>
            <span v-if="goldenRecord.recordTimestamp">{{ formatDateLong(goldenRecord.recordTimestamp) }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.goldenRecord.cancelled')"></span>
          </dt>
          <dd>
            <div v-if="goldenRecord.cancelled">
              <router-link :to="{ name: 'CancelledMySuffixView', params: { cancelledId: goldenRecord.cancelled.id } }">{{
                goldenRecord.cancelled.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.goldenRecord.payload')"></span>
          </dt>
          <dd>
            <div v-if="goldenRecord.payload">
              <router-link :to="{ name: 'PayloadMySuffixView', params: { payloadId: goldenRecord.payload.id } }">{{
                goldenRecord.payload.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="goldenRecord.id"
          :to="{ name: 'GoldenRecordMySuffixEdit', params: { goldenRecordId: goldenRecord.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./golden-record-my-suffix-details.component.ts"></script>
