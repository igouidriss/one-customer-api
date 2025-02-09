import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import PhoneMySuffixService from './phone-my-suffix.service';
import { type IPhoneMySuffix } from '@/shared/model/phone-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PhoneMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const phoneService = inject('phoneService', () => new PhoneMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const phones: Ref<IPhoneMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrievePhoneMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await phoneService().retrieve();
        phones.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePhoneMySuffixs();
    };

    onMounted(async () => {
      await retrievePhoneMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPhoneMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePhoneMySuffix = async () => {
      try {
        await phoneService().delete(removeId.value);
        const message = t$('rcuApplicationApp.phone.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePhoneMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      phones,
      handleSyncList,
      isFetching,
      retrievePhoneMySuffixs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePhoneMySuffix,
      t$,
    };
  },
});
