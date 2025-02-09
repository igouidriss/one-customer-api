import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CancelledMySuffixService from './cancelled-my-suffix.service';
import { useDateFormat } from '@/shared/composables';
import { type ICancelledMySuffix } from '@/shared/model/cancelled-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CancelledMySuffixDetails',
  setup() {
    const dateFormat = useDateFormat();
    const cancelledService = inject('cancelledService', () => new CancelledMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const cancelled: Ref<ICancelledMySuffix> = ref({});

    const retrieveCancelledMySuffix = async cancelledId => {
      try {
        const res = await cancelledService().find(cancelledId);
        cancelled.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cancelledId) {
      retrieveCancelledMySuffix(route.params.cancelledId);
    }

    return {
      ...dateFormat,
      alertService,
      cancelled,

      previousState,
      t$: useI18n().t,
    };
  },
});
