import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AddressMySuffixService from './address-my-suffix.service';
import { type IAddressMySuffix } from '@/shared/model/address-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AddressMySuffixDetails',
  setup() {
    const addressService = inject('addressService', () => new AddressMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const address: Ref<IAddressMySuffix> = ref({});

    const retrieveAddressMySuffix = async addressId => {
      try {
        const res = await addressService().find(addressId);
        address.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.addressId) {
      retrieveAddressMySuffix(route.params.addressId);
    }

    return {
      alertService,
      address,

      previousState,
      t$: useI18n().t,
    };
  },
});
