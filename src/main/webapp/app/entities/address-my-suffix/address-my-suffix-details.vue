<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="address">
        <h2 class="jh-entity-heading" data-cy="addressDetailsHeading">
          <span v-text="t$('rcuApplicationApp.address.detail.title')"></span> {{ address.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.address.type')"></span>
          </dt>
          <dd>
            <span>{{ address.type }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.address.zipCode')"></span>
          </dt>
          <dd>
            <span>{{ address.zipCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.address.city')"></span>
          </dt>
          <dd>
            <span>{{ address.city }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.address.country')"></span>
          </dt>
          <dd>
            <span>{{ address.country }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.address.line1')"></span>
          </dt>
          <dd>
            <span>{{ address.line1 }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.address.line2')"></span>
          </dt>
          <dd>
            <span>{{ address.line2 }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.address.line3')"></span>
          </dt>
          <dd>
            <span>{{ address.line3 }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.address.payload')"></span>
          </dt>
          <dd>
            <div v-if="address.payload">
              <router-link :to="{ name: 'PayloadMySuffixView', params: { payloadId: address.payload.id } }">{{
                address.payload.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="address.id"
          :to="{ name: 'AddressMySuffixEdit', params: { addressId: address.id } }"
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

<script lang="ts" src="./address-my-suffix-details.component.ts"></script>
