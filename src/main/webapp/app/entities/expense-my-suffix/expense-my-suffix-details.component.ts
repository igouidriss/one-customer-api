import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ExpenseMySuffixService from './expense-my-suffix.service';
import { useDateFormat } from '@/shared/composables';
import { type IExpenseMySuffix } from '@/shared/model/expense-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ExpenseMySuffixDetails',
  setup() {
    const dateFormat = useDateFormat();
    const expenseService = inject('expenseService', () => new ExpenseMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const expense: Ref<IExpenseMySuffix> = ref({});

    const retrieveExpenseMySuffix = async expenseId => {
      try {
        const res = await expenseService().find(expenseId);
        expense.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.expenseId) {
      retrieveExpenseMySuffix(route.params.expenseId);
    }

    return {
      ...dateFormat,
      alertService,
      expense,

      previousState,
      t$: useI18n().t,
    };
  },
});
