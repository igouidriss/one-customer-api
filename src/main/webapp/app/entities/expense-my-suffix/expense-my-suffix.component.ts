import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import ExpenseMySuffixService from './expense-my-suffix.service';
import { type IExpenseMySuffix } from '@/shared/model/expense-my-suffix.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ExpenseMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const expenseService = inject('expenseService', () => new ExpenseMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const expenses: Ref<IExpenseMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveExpenseMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await expenseService().retrieve();
        expenses.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveExpenseMySuffixs();
    };

    onMounted(async () => {
      await retrieveExpenseMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IExpenseMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeExpenseMySuffix = async () => {
      try {
        await expenseService().delete(removeId.value);
        const message = t$('rcuApplicationApp.expense.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveExpenseMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      expenses,
      handleSyncList,
      isFetching,
      retrieveExpenseMySuffixs,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeExpenseMySuffix,
      t$,
    };
  },
});
