import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PayloadMySuffixService from './payload-my-suffix.service';
import { type IPayloadMySuffix } from '@/shared/model/payload-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PayloadMySuffixDetails',
  setup() {
    const payloadService = inject('payloadService', () => new PayloadMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const payload: Ref<IPayloadMySuffix> = ref({});

    const retrievePayloadMySuffix = async payloadId => {
      try {
        const res = await payloadService().find(payloadId);
        payload.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.payloadId) {
      retrievePayloadMySuffix(route.params.payloadId);
    }

    return {
      alertService,
      payload,

      previousState,
      t$: useI18n().t,
    };
  },
});
