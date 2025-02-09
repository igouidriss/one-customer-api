import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ExpensesMySuffixService from './expenses-my-suffix.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { ExpensesMySuffix, type IExpensesMySuffix } from '@/shared/model/expenses-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ExpensesMySuffixUpdate',
  setup() {
    const expensesService = inject('expensesService', () => new ExpensesMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const expenses: Ref<IExpensesMySuffix> = ref(new ExpensesMySuffix());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveExpensesMySuffix = async expensesId => {
      try {
        const res = await expensesService().find(expensesId);
        expenses.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.expensesId) {
      retrieveExpensesMySuffix(route.params.expensesId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      totalExpense: {},
      expenses: {},
    };
    const v$ = useVuelidate(validationRules, expenses as any);
    v$.value.$validate();

    return {
      expensesService,
      alertService,
      expenses,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.expenses.id) {
        this.expensesService()
          .update(this.expenses)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.expenses.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.expensesService()
          .create(this.expenses)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.expenses.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
