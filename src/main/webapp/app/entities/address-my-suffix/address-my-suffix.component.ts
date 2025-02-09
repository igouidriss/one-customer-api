import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import AddressMySuffixService from './address-my-suffix.service';
import { type IAddressMySuffix } from '@/shared/model/address-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AddressMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const addressService = inject('addressService', () => new AddressMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const addresses: Ref<IAddressMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveAddressMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await addressService().retrieve();
        addresses.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveAddressMySuffixs();
    };

    onMounted(async () => {
      await retrieveAddressMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IAddressMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeAddressMySuffix = async () => {
      try {
        await addressService().delete(removeId.value);
        const message = t$('rcuApplicationApp.address.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveAddressMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      addresses,
      handleSyncList,
      isFetching,
      retrieveAddressMySuffixs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeAddressMySuffix,
      t$,
    };
  },
});
