<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cancelled">
        <h2 class="jh-entity-heading" data-cy="cancelledDetailsHeading">
          <span v-text="t$('rcuApplicationApp.cancelled.detail.title')"></span> {{ cancelled.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.cancelled.cancelReason')"></span>
          </dt>
          <dd>
            <span>{{ cancelled.cancelReason }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.cancelled.isItCancelled')"></span>
          </dt>
          <dd>
            <span>{{ cancelled.isItCancelled }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.cancelled.cancelDate')"></span>
          </dt>
          <dd>
            <span v-if="cancelled.cancelDate">{{ formatDateLong(cancelled.cancelDate) }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="cancelled.id"
          :to="{ name: 'CancelledMySuffixEdit', params: { cancelledId: cancelled.id } }"
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

<script lang="ts" src="./cancelled-my-suffix-details.component.ts"></script>
