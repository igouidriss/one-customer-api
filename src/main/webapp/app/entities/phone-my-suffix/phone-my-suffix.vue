<template>
  <div>
    <h2 id="page-heading" data-cy="PhoneHeading">
      <span v-text="t$('rcuApplicationApp.phone.home.title')" id="phone-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('rcuApplicationApp.phone.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PhoneMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-phone-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('rcuApplicationApp.phone.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && phones && phones.length === 0">
      <span v-text="t$('rcuApplicationApp.phone.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="phones && phones.length > 0">
      <table class="table table-striped" aria-describedby="phones">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.phone.type')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.phone.number')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.phone.payload')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="phone in phones" :key="phone.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PhoneMySuffixView', params: { phoneId: phone.id } }">{{ phone.id }}</router-link>
            </td>
            <td>{{ phone.type }}</td>
            <td>{{ phone.number }}</td>
            <td>
              <div v-if="phone.payload">
                <router-link :to="{ name: 'PayloadMySuffixView', params: { payloadId: phone.payload.id } }">{{
                  phone.payload.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PhoneMySuffixView', params: { phoneId: phone.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PhoneMySuffixEdit', params: { phoneId: phone.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(phone)"
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
        <span id="rcuApplicationApp.phone.delete.question" data-cy="phoneDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-phone-heading" v-text="t$('rcuApplicationApp.phone.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-phone"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removePhoneMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./phone-my-suffix.component.ts"></script>
