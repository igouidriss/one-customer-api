<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="oneCustomer">
        <h2 class="jh-entity-heading" data-cy="oneCustomerDetailsHeading">
          <span v-text="t$('rcuApplicationApp.oneCustomer.detail.title')"></span> {{ oneCustomer.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.oneCustomer.domaine')"></span>
          </dt>
          <dd>
            <span>{{ oneCustomer.domaine }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.oneCustomer.aggregateId')"></span>
          </dt>
          <dd>
            <span>{{ oneCustomer.aggregateId }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.oneCustomer.aggregateType')"></span>
          </dt>
          <dd>
            <span>{{ oneCustomer.aggregateType }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.oneCustomer.timestamp')"></span>
          </dt>
          <dd>
            <span v-if="oneCustomer.timestamp">{{ formatDateLong(oneCustomer.timestamp) }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.oneCustomer.goldenRecord')"></span>
          </dt>
          <dd>
            <div v-if="oneCustomer.goldenRecord">
              <router-link :to="{ name: 'GoldenRecordMySuffixView', params: { goldenRecordId: oneCustomer.goldenRecord.id } }">{{
                oneCustomer.goldenRecord.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="oneCustomer.id"
          :to="{ name: 'OneCustomerMySuffixEdit', params: { oneCustomerId: oneCustomer.id } }"
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

<script lang="ts" src="./one-customer-my-suffix-details.component.ts"></script>
