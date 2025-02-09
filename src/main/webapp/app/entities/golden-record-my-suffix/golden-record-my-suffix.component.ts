import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import GoldenRecordMySuffixService from './golden-record-my-suffix.service';
import { type IGoldenRecordMySuffix } from '@/shared/model/golden-record-my-suffix.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'GoldenRecordMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const goldenRecordService = inject('goldenRecordService', () => new GoldenRecordMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const goldenRecords: Ref<IGoldenRecordMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveGoldenRecordMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await goldenRecordService().retrieve();
        goldenRecords.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveGoldenRecordMySuffixs();
    };

    onMounted(async () => {
      await retrieveGoldenRecordMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IGoldenRecordMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeGoldenRecordMySuffix = async () => {
      try {
        await goldenRecordService().delete(removeId.value);
        const message = t$('rcuApplicationApp.goldenRecord.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveGoldenRecordMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      goldenRecords,
      handleSyncList,
      isFetching,
      retrieveGoldenRecordMySuffixs,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeGoldenRecordMySuffix,
      t$,
    };
  },
});
