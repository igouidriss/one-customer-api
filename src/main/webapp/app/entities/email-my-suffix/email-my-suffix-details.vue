<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="email">
        <h2 class="jh-entity-heading" data-cy="emailDetailsHeading">
          <span v-text="t$('rcuApplicationApp.email.detail.title')"></span> {{ email.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.email.type')"></span>
          </dt>
          <dd>
            <span>{{ email.type }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.email.value')"></span>
          </dt>
          <dd>
            <span>{{ email.value }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.email.payload')"></span>
          </dt>
          <dd>
            <div v-if="email.payload">
              <router-link :to="{ name: 'PayloadMySuffixView', params: { payloadId: email.payload.id } }">{{
                email.payload.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="email.id" :to="{ name: 'EmailMySuffixEdit', params: { emailId: email.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./email-my-suffix-details.component.ts"></script>
