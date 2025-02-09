import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PhoneMySuffixService from './phone-my-suffix.service';
import { type IPhoneMySuffix } from '@/shared/model/phone-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PhoneMySuffixDetails',
  setup() {
    const phoneService = inject('phoneService', () => new PhoneMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const phone: Ref<IPhoneMySuffix> = ref({});

    const retrievePhoneMySuffix = async phoneId => {
      try {
        const res = await phoneService().find(phoneId);
        phone.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.phoneId) {
      retrievePhoneMySuffix(route.params.phoneId);
    }

    return {
      alertService,
      phone,

      previousState,
      t$: useI18n().t,
    };
  },
});
