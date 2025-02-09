import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import CancelledMySuffixService from './cancelled-my-suffix.service';
import { type ICancelledMySuffix } from '@/shared/model/cancelled-my-suffix.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CancelledMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const cancelledService = inject('cancelledService', () => new CancelledMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const cancelleds: Ref<ICancelledMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveCancelledMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await cancelledService().retrieve();
        cancelleds.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveCancelledMySuffixs();
    };

    onMounted(async () => {
      await retrieveCancelledMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ICancelledMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeCancelledMySuffix = async () => {
      try {
        await cancelledService().delete(removeId.value);
        const message = t$('rcuApplicationApp.cancelled.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveCancelledMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      cancelleds,
      handleSyncList,
      isFetching,
      retrieveCancelledMySuffixs,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeCancelledMySuffix,
      t$,
    };
  },
});
