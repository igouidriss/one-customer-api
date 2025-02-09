import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MetadataMySuffixService from './metadata-my-suffix.service';
import { type IMetadataMySuffix } from '@/shared/model/metadata-my-suffix.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MetadataMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const metadataService = inject('metadataService', () => new MetadataMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const metadata: Ref<IMetadataMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMetadataMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await metadataService().retrieve();
        metadata.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMetadataMySuffixs();
    };

    onMounted(async () => {
      await retrieveMetadataMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMetadataMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMetadataMySuffix = async () => {
      try {
        await metadataService().delete(removeId.value);
        const message = t$('rcuApplicationApp.metadata.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMetadataMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      metadata,
      handleSyncList,
      isFetching,
      retrieveMetadataMySuffixs,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMetadataMySuffix,
      t$,
    };
  },
});
