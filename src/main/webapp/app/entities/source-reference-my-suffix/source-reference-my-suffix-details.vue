<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="sourceReference">
        <h2 class="jh-entity-heading" data-cy="sourceReferenceDetailsHeading">
          <span v-text="t$('rcuApplicationApp.sourceReference.detail.title')"></span> {{ sourceReference.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('rcuApplicationApp.sourceReference.source')"></span>
          </dt>
          <dd>
            <span>{{ sourceReference.source }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.sourceReference.value')"></span>
          </dt>
          <dd>
            <span>{{ sourceReference.value }}</span>
          </dd>
          <dt>
            <span v-text="t$('rcuApplicationApp.sourceReference.goldenRecord')"></span>
          </dt>
          <dd>
            <div v-if="sourceReference.goldenRecord">
              <router-link :to="{ name: 'GoldenRecordMySuffixView', params: { goldenRecordId: sourceReference.goldenRecord.id } }">{{
                sourceReference.goldenRecord.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="sourceReference.id"
          :to="{ name: 'SourceReferenceMySuffixEdit', params: { sourceReferenceId: sourceReference.id } }"
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

<script lang="ts" src="./source-reference-my-suffix-details.component.ts"></script>
