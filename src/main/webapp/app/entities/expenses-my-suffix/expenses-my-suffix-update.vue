<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="rcuApplicationApp.expenses.home.createOrEditLabel"
          data-cy="ExpensesCreateUpdateHeading"
          v-text="t$('rcuApplicationApp.expenses.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="expenses.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="expenses.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('rcuApplicationApp.expenses.totalExpense')"
              for="expenses-my-suffix-totalExpense"
            ></label>
            <input
              type="number"
              class="form-control"
              name="totalExpense"
              id="expenses-my-suffix-totalExpense"
              data-cy="totalExpense"
              :class="{ valid: !v$.totalExpense.$invalid, invalid: v$.totalExpense.$invalid }"
              v-model.number="v$.totalExpense.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./expenses-my-suffix-update.component.ts"></script>
