import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RestorationReservationMySuffixService from './restoration-reservation-my-suffix.service';
import { useDateFormat } from '@/shared/composables';
import { type IRestorationReservationMySuffix } from '@/shared/model/restoration-reservation-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RestorationReservationMySuffixDetails',
  setup() {
    const dateFormat = useDateFormat();
    const restorationReservationService = inject('restorationReservationService', () => new RestorationReservationMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const restorationReservation: Ref<IRestorationReservationMySuffix> = ref({});

    const retrieveRestorationReservationMySuffix = async restorationReservationId => {
      try {
        const res = await restorationReservationService().find(restorationReservationId);
        restorationReservation.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.restorationReservationId) {
      retrieveRestorationReservationMySuffix(route.params.restorationReservationId);
    }

    return {
      ...dateFormat,
      alertService,
      restorationReservation,

      previousState,
      t$: useI18n().t,
    };
  },
});
