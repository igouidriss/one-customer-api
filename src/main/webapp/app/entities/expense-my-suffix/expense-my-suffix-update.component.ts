import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ExpenseMySuffixService from './expense-my-suffix.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MetadataMySuffixService from '@/entities/metadata-my-suffix/metadata-my-suffix.service';
import { type IMetadataMySuffix } from '@/shared/model/metadata-my-suffix.model';
import ExpensesMySuffixService from '@/entities/expenses-my-suffix/expenses-my-suffix.service';
import { type IExpensesMySuffix } from '@/shared/model/expenses-my-suffix.model';
import { ExpenseMySuffix, type IExpenseMySuffix } from '@/shared/model/expense-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ExpenseMySuffixUpdate',
  setup() {
    const expenseService = inject('expenseService', () => new ExpenseMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const expense: Ref<IExpenseMySuffix> = ref(new ExpenseMySuffix());

    const metadataService = inject('metadataService', () => new MetadataMySuffixService());

    const metadata: Ref<IMetadataMySuffix[]> = ref([]);

    const expensesService = inject('expensesService', () => new ExpensesMySuffixService());

    const expenses: Ref<IExpensesMySuffix[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveExpenseMySuffix = async expenseId => {
      try {
        const res = await expenseService().find(expenseId);
        res.arrivalDate = new Date(res.arrivalDate);
        res.leaveDate = new Date(res.leaveDate);
        expense.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.expenseId) {
      retrieveExpenseMySuffix(route.params.expenseId);
    }

    const initRelationships = () => {
      metadataService()
        .retrieve()
        .then(res => {
          metadata.value = res.data;
        });
      expensesService()
        .retrieve()
        .then(res => {
          expenses.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      expenseType: {},
      amount: {},
      depositAmount: {},
      totalAmount: {},
      shift: {},
      date: {},
      arrivalDate: {},
      leaveDate: {},
      guestCount: {},
      hotelName: {},
      hotelId: {},
      restaurantName: {},
      restaurantId: {},
      clientId: {},
      metadata: {},
      expenses: {},
    };
    const v$ = useVuelidate(validationRules, expense as any);
    v$.value.$validate();

    return {
      expenseService,
      alertService,
      expense,
      previousState,
      isSaving,
      currentLanguage,
      metadata,
      expenses,
      v$,
      ...useDateFormat({ entityRef: expense }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.expense.id) {
        this.expenseService()
          .update(this.expense)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.expense.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.expenseService()
          .create(this.expense)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.expense.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
