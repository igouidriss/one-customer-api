<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="payload">
        <h2 class="jh-entity-heading" data-cy="payloadDetailsHeading">
          <span v-text="t$('rcuApplicationApp.payload.detail.title')"></span> {{ payload.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.payload.lastName')"></span>
          </dt>
          <dd>
            <span>{{ payload.lastName }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.payload.firstName')"></span>
          </dt>
          <dd>
            <span>{{ payload.firstName }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.payload.birthDate')"></span>
          </dt>
          <dd>
            <span>{{ payload.birthDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.payload.lang')"></span>
          </dt>
          <dd>
            <span>{{ payload.lang }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.payload.isVip')"></span>
          </dt>
          <dd>
            <span>{{ payload.isVip }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.payload.metadata')"></span>
          </dt>
          <dd>
            <div v-if="payload.metadata">
              <router-link :to="{ name: 'MetadataMySuffixView', params: { metadataId: payload.metadata.id } }">{{
                payload.metadata.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="payload.id"
          :to="{ name: 'PayloadMySuffixEdit', params: { payloadId: payload.id } }"
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

<script lang="ts" src="./payload-my-suffix-details.component.ts"></script>
