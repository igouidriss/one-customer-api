import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import PayloadMySuffixService from './payload-my-suffix.service';
import { type IPayloadMySuffix } from '@/shared/model/payload-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PayloadMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const payloadService = inject('payloadService', () => new PayloadMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const payloads: Ref<IPayloadMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrievePayloadMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await payloadService().retrieve();
        payloads.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePayloadMySuffixs();
    };

    onMounted(async () => {
      await retrievePayloadMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPayloadMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePayloadMySuffix = async () => {
      try {
        await payloadService().delete(removeId.value);
        const message = t$('rcuApplicationApp.payload.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePayloadMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      payloads,
      handleSyncList,
      isFetching,
      retrievePayloadMySuffixs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePayloadMySuffix,
      t$,
    };
  },
});
