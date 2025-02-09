<template>
  <div>
    <h2 id="page-heading" data-cy="AddressHeading">
      <span v-text="t$('rcuApplicationApp.address.home.title')" id="address-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('rcuApplicationApp.address.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'AddressMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-address-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('rcuApplicationApp.address.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && addresses && addresses.length === 0">
      <span v-text="t$('rcuApplicationApp.address.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="addresses && addresses.length > 0">
      <table class="table table-striped" aria-describedby="addresses">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.address.type')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.address.zipCode')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.address.city')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.address.country')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.address.line1')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.address.line2')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.address.line3')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.address.payload')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="address in addresses" :key="address.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AddressMySuffixView', params: { addressId: address.id } }">{{ address.id }}</router-link>
            </td>
            <td>{{ address.type }}</td>
            <td>{{ address.zipCode }}</td>
            <td>{{ address.city }}</td>
            <td>{{ address.country }}</td>
            <td>{{ address.line1 }}</td>
            <td>{{ address.line2 }}</td>
            <td>{{ address.line3 }}</td>
            <td>
              <div v-if="address.payload">
                <router-link :to="{ name: 'PayloadMySuffixView', params: { payloadId: address.payload.id } }">{{
                  address.payload.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AddressMySuffixView', params: { addressId: address.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AddressMySuffixEdit', params: { addressId: address.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(address)"
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
        <span id="rcuApplicationApp.address.delete.question" data-cy="addressDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-address-heading" v-text="t$('rcuApplicationApp.address.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-address"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeAddressMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./address-my-suffix.component.ts"></script>
