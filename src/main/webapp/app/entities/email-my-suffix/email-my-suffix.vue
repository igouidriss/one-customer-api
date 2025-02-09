<template>
  <div>
    <h2 id="page-heading" data-cy="EmailHeading">
      <span v-text="t$('rcuApplicationApp.email.home.title')" id="email-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('rcuApplicationApp.email.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'EmailMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-email-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('rcuApplicationApp.email.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && emails && emails.length === 0">
      <span v-text="t$('rcuApplicationApp.email.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="emails && emails.length > 0">
      <table class="table table-striped" aria-describedby="emails">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.email.type')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.email.value')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.email.payload')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="email in emails" :key="email.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EmailMySuffixView', params: { emailId: email.id } }">{{ email.id }}</router-link>
            </td>
            <td>{{ email.type }}</td>
            <td>{{ email.value }}</td>
            <td>
              <div v-if="email.payload">
                <router-link :to="{ name: 'PayloadMySuffixView', params: { payloadId: email.payload.id } }">{{
                  email.payload.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EmailMySuffixView', params: { emailId: email.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EmailMySuffixEdit', params: { emailId: email.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(email)"
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
        <span id="rcuApplicationApp.email.delete.question" data-cy="emailDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-email-heading" v-text="t$('rcuApplicationApp.email.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-email"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeEmailMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./email-my-suffix.component.ts"></script>
