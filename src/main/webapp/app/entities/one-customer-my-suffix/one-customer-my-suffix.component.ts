import { type Ref, defineComponent, inject, onMounted, ref, watch } from 'vue';
import { useI18n } from 'vue-i18n';

import OneCustomerMySuffixService from './one-customer-my-suffix.service';
import { type IOneCustomerMySuffix } from '@/shared/model/one-customer-my-suffix.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OneCustomerMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const oneCustomerService = inject('oneCustomerService', () => new OneCustomerMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const itemsPerPage = ref(20);
    const queryCount: Ref<number> = ref(null);
    const page: Ref<number> = ref(1);
    const propOrder = ref('id');
    const reverse = ref(false);
    const totalItems = ref(0);

    const oneCustomers: Ref<IOneCustomerMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {
      page.value = 1;
    };

    const sort = (): Array<any> => {
      const result = [`${propOrder.value},${reverse.value ? 'desc' : 'asc'}`];
      if (propOrder.value !== 'id') {
        result.push('id');
      }
      return result;
    };

    const retrieveOneCustomerMySuffixs = async () => {
      isFetching.value = true;
      try {
        const paginationQuery = {
          page: page.value - 1,
          size: itemsPerPage.value,
          sort: sort(),
        };
        const res = await oneCustomerService().retrieve(paginationQuery);
        totalItems.value = Number(res.headers['x-total-count']);
        queryCount.value = totalItems.value;
        oneCustomers.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveOneCustomerMySuffixs();
    };

    onMounted(async () => {
      await retrieveOneCustomerMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IOneCustomerMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeOneCustomerMySuffix = async () => {
      try {
        await oneCustomerService().delete(removeId.value);
        const message = t$('rcuApplicationApp.oneCustomer.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveOneCustomerMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    const changeOrder = (newOrder: string) => {
      if (propOrder.value === newOrder) {
        reverse.value = !reverse.value;
      } else {
        reverse.value = false;
      }
      propOrder.value = newOrder;
    };

    // Whenever order changes, reset the pagination
    watch([propOrder, reverse], async () => {
      if (page.value === 1) {
        // first page, retrieve new data
        await retrieveOneCustomerMySuffixs();
      } else {
        // reset the pagination
        clear();
      }
    });

    // Whenever page changes, switch to the new page.
    watch(page, async () => {
      await retrieveOneCustomerMySuffixs();
    });

    return {
      oneCustomers,
      handleSyncList,
      isFetching,
      retrieveOneCustomerMySuffixs,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeOneCustomerMySuffix,
      itemsPerPage,
      queryCount,
      page,
      propOrder,
      reverse,
      totalItems,
      changeOrder,
      t$,
    };
  },
});
