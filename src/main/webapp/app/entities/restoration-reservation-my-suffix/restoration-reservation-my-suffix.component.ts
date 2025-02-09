import { type Ref, defineComponent, inject, onMounted, ref, watch, watchEffect } from 'vue';
import { useI18n } from 'vue-i18n';
import { useIntersectionObserver } from '@vueuse/core';

import RestorationReservationMySuffixService from './restoration-reservation-my-suffix.service';
import { type IRestorationReservationMySuffix } from '@/shared/model/restoration-reservation-my-suffix.model';
import useDataUtils from '@/shared/data/data-utils.service';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RestorationReservationMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const dataUtils = useDataUtils();
    const restorationReservationService = inject('restorationReservationService', () => new RestorationReservationMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const itemsPerPage = ref(20);
    const queryCount: Ref<number> = ref(null);
    const page: Ref<number> = ref(1);
    const propOrder = ref('id');
    const reverse = ref(false);
    const totalItems = ref(0);
    const links: Ref<any> = ref({});

    const restorationReservations: Ref<IRestorationReservationMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {
      page.value = 1;
      links.value = {};
      restorationReservations.value = [];
    };

    const sort = (): Array<any> => {
      const result = [`${propOrder.value},${reverse.value ? 'desc' : 'asc'}`];
      if (propOrder.value !== 'id') {
        result.push('id');
      }
      return result;
    };

    const retrieveRestorationReservationMySuffixs = async () => {
      isFetching.value = true;
      try {
        const paginationQuery = {
          page: page.value - 1,
          size: itemsPerPage.value,
          sort: sort(),
        };
        const res = await restorationReservationService().retrieve(paginationQuery);
        totalItems.value = Number(res.headers['x-total-count']);
        queryCount.value = totalItems.value;
        links.value = dataUtils.parseLinks(res.headers?.link);
        restorationReservations.value.push(...(res.data ?? []));
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      clear();
    };

    onMounted(async () => {
      await retrieveRestorationReservationMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRestorationReservationMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRestorationReservationMySuffix = async () => {
      try {
        await restorationReservationService().delete(removeId.value);
        const message = t$('rcuApplicationApp.restorationReservation.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        clear();
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
    watch([propOrder, reverse], () => {
      clear();
    });

    // Whenever the data resets or page changes, switch to the new page.
    watch([restorationReservations, page], async ([data, page], [_prevData, prevPage]) => {
      if (data.length === 0 || page !== prevPage) {
        await retrieveRestorationReservationMySuffixs();
      }
    });

    const infiniteScrollEl = ref<HTMLElement>(null);
    const intersectionObserver = useIntersectionObserver(
      infiniteScrollEl,
      intersection => {
        if (intersection[0].isIntersecting && !isFetching.value) {
          page.value++;
        }
      },
      {
        threshold: 0.5,
        immediate: false,
      },
    );
    watchEffect(() => {
      if (links.value.next) {
        intersectionObserver.resume();
      } else if (intersectionObserver.isActive) {
        intersectionObserver.pause();
      }
    });

    return {
      restorationReservations,
      handleSyncList,
      isFetching,
      retrieveRestorationReservationMySuffixs,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRestorationReservationMySuffix,
      itemsPerPage,
      queryCount,
      page,
      propOrder,
      reverse,
      totalItems,
      changeOrder,
      infiniteScrollEl,
      t$,
      ...dataUtils,
    };
  },
});
