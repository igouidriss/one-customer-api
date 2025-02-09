import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import SourceReferenceMySuffixService from './source-reference-my-suffix.service';
import { type ISourceReferenceMySuffix } from '@/shared/model/source-reference-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SourceReferenceMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const sourceReferenceService = inject('sourceReferenceService', () => new SourceReferenceMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const sourceReferences: Ref<ISourceReferenceMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveSourceReferenceMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await sourceReferenceService().retrieve();
        sourceReferences.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveSourceReferenceMySuffixs();
    };

    onMounted(async () => {
      await retrieveSourceReferenceMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ISourceReferenceMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeSourceReferenceMySuffix = async () => {
      try {
        await sourceReferenceService().delete(removeId.value);
        const message = t$('rcuApplicationApp.sourceReference.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveSourceReferenceMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      sourceReferences,
      handleSyncList,
      isFetching,
      retrieveSourceReferenceMySuffixs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeSourceReferenceMySuffix,
      t$,
    };
  },
});
