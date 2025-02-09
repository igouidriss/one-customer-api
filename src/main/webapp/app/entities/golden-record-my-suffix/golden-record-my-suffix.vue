<template>
  <div>
    <h2 id="page-heading" data-cy="GoldenRecordHeading">
      <span v-text="t$('rcuApplicationApp.goldenRecord.home.title')" id="golden-record-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('rcuApplicationApp.goldenRecord.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'GoldenRecordMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-golden-record-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('rcuApplicationApp.goldenRecord.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && goldenRecords && goldenRecords.length === 0">
      <span v-text="t$('rcuApplicationApp.goldenRecord.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="goldenRecords && goldenRecords.length > 0">
      <table class="table table-striped" aria-describedby="goldenRecords">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.goldenRecord.aggregateId')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.goldenRecord.aggregateType')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.goldenRecord.domaine')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.goldenRecord.mdmId')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.goldenRecord.source')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.goldenRecord.recordTimestamp')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.goldenRecord.cancelled')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.goldenRecord.payload')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="goldenRecord in goldenRecords" :key="goldenRecord.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'GoldenRecordMySuffixView', params: { goldenRecordId: goldenRecord.id } }">{{
                goldenRecord.id
              }}</router-link>
            </td>
            <td>{{ goldenRecord.aggregateId }}</td>
            <td>{{ goldenRecord.aggregateType }}</td>
            <td>{{ goldenRecord.domaine }}</td>
            <td>{{ goldenRecord.mdmId }}</td>
            <td>{{ goldenRecord.source }}</td>
            <td>{{ formatDateShort(goldenRecord.recordTimestamp) || '' }}</td>
            <td>
              <div v-if="goldenRecord.cancelled">
                <router-link :to="{ name: 'CancelledMySuffixView', params: { cancelledId: goldenRecord.cancelled.id } }">{{
                  goldenRecord.cancelled.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="goldenRecord.payload">
                <router-link :to="{ name: 'PayloadMySuffixView', params: { payloadId: goldenRecord.payload.id } }">{{
                  goldenRecord.payload.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'GoldenRecordMySuffixView', params: { goldenRecordId: goldenRecord.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'GoldenRecordMySuffixEdit', params: { goldenRecordId: goldenRecord.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(goldenRecord)"
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
        <span
          id="rcuApplicationApp.goldenRecord.delete.question"
          data-cy="goldenRecordDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-goldenRecord-heading" v-text="t$('rcuApplicationApp.goldenRecord.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-goldenRecord"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeGoldenRecordMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./golden-record-my-suffix.component.ts"></script>
