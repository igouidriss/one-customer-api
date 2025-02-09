import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import ExpensesMySuffixService from './expenses-my-suffix.service';
import { type IExpensesMySuffix } from '@/shared/model/expenses-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ExpensesMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const expensesService = inject('expensesService', () => new ExpensesMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const expenses: Ref<IExpensesMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveExpensesMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await expensesService().retrieve();
        expenses.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveExpensesMySuffixs();
    };

    onMounted(async () => {
      await retrieveExpensesMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IExpensesMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeExpensesMySuffix = async () => {
      try {
        await expensesService().delete(removeId.value);
        const message = t$('rcuApplicationApp.expenses.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveExpensesMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      expenses,
      handleSyncList,
      isFetching,
      retrieveExpensesMySuffixs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeExpensesMySuffix,
      t$,
    };
  },
});
