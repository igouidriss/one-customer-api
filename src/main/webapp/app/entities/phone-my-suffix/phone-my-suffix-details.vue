<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="phone">
        <h2 class="jh-entity-heading" data-cy="phoneDetailsHeading">
          <span v-text="t$('rcuApplicationApp.phone.detail.title')"></span> {{ phone.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.phone.type')"></span>
          </dt>
          <dd>
            <span>{{ phone.type }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.phone.number')"></span>
          </dt>
          <dd>
            <span>{{ phone.number }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.phone.payload')"></span>
          </dt>
          <dd>
            <div v-if="phone.payload">
              <router-link :to="{ name: 'PayloadMySuffixView', params: { payloadId: phone.payload.id } }">{{
                phone.payload.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="phone.id" :to="{ name: 'PhoneMySuffixEdit', params: { phoneId: phone.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./phone-my-suffix-details.component.ts"></script>
