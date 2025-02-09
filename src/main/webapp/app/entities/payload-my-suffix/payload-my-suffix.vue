<template>
  <div>
    <h2 id="page-heading" data-cy="PayloadHeading">
      <span v-text="t$('rcuApplicationApp.payload.home.title')" id="payload-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('rcuApplicationApp.payload.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PayloadMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-payload-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('rcuApplicationApp.payload.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && payloads && payloads.length === 0">
      <span v-text="t$('rcuApplicationApp.payload.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="payloads && payloads.length > 0">
      <table class="table table-striped" aria-describedby="payloads">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.payload.lastName')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.payload.firstName')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.payload.birthDate')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.payload.lang')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.payload.isVip')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.payload.metadata')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="payload in payloads" :key="payload.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PayloadMySuffixView', params: { payloadId: payload.id } }">{{ payload.id }}</router-link>
            </td>
            <td>{{ payload.lastName }}</td>
            <td>{{ payload.firstName }}</td>
            <td>{{ payload.birthDate }}</td>
            <td>{{ payload.lang }}</td>
            <td>{{ payload.isVip }}</td>
            <td>
              <div v-if="payload.metadata">
                <router-link :to="{ name: 'MetadataMySuffixView', params: { metadataId: payload.metadata.id } }">{{
                  payload.metadata.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PayloadMySuffixView', params: { payloadId: payload.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PayloadMySuffixEdit', params: { payloadId: payload.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(payload)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="rcuApplicationApp.payload.delete.question" data-cy="payloadDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-payload-heading" v-text="t$('rcuApplicationApp.payload.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-payload"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removePayloadMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./payload-my-suffix.component.ts"></script>
