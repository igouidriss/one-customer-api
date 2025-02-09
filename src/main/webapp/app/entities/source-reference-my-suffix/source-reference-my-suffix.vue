<template>
  <div>
    <h2 id="page-heading" data-cy="SourceReferenceHeading">
      <span v-text="t$('rcuApplicationApp.sourceReference.home.title')" id="source-reference-my-suffix-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('rcuApplicationApp.sourceReference.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'SourceReferenceMySuffixCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-source-reference-my-suffix"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('rcuApplicationApp.sourceReference.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && sourceReferences && sourceReferences.length === 0">
      <span v-text="t$('rcuApplicationApp.sourceReference.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="sourceReferences && sourceReferences.length > 0">
      <table class="table table-striped" aria-describedby="sourceReferences">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.sourceReference.source')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.sourceReference.value')"></span></th>
            <th scope="row"><span v-text="t$('rcuApplicationApp.sourceReference.goldenRecord')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="sourceReference in sourceReferences" :key="sourceReference.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SourceReferenceMySuffixView', params: { sourceReferenceId: sourceReference.id } }">{{
                sourceReference.id
              }}</router-link>
            </td>
            <td>{{ sourceReference.source }}</td>
            <td>{{ sourceReference.value }}</td>
            <td>
              <div v-if="sourceReference.goldenRecord">
                <router-link :to="{ name: 'GoldenRecordMySuffixView', params: { goldenRecordId: sourceReference.goldenRecord.id } }">{{
                  sourceReference.goldenRecord.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'SourceReferenceMySuffixView', params: { sourceReferenceId: sourceReference.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'SourceReferenceMySuffixEdit', params: { sourceReferenceId: sourceReference.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(sourceReference)"
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
          id="rcuApplicationApp.sourceReference.delete.question"
          data-cy="sourceReferenceDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-sourceReference-heading" v-text="t$('rcuApplicationApp.sourceReference.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-sourceReference"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeSourceReferenceMySuffix()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./source-reference-my-suffix.component.ts"></script>
