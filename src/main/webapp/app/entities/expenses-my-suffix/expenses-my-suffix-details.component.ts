import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ExpensesMySuffixService from './expenses-my-suffix.service';
import { type IExpensesMySuffix } from '@/shared/model/expenses-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ExpensesMySuffixDetails',
  setup() {
    const expensesService = inject('expensesService', () => new ExpensesMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const expenses: Ref<IExpensesMySuffix> = ref({});

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

    return {
      alertService,
      expenses,

      previousState,
      t$: useI18n().t,
    };
  },
});
