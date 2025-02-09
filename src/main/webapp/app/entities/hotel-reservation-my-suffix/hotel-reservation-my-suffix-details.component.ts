import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import HotelReservationMySuffixService from './hotel-reservation-my-suffix.service';
import { useDateFormat } from '@/shared/composables';
import { type IHotelReservationMySuffix } from '@/shared/model/hotel-reservation-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'HotelReservationMySuffixDetails',
  setup() {
    const dateFormat = useDateFormat();
    const hotelReservationService = inject('hotelReservationService', () => new HotelReservationMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const hotelReservation: Ref<IHotelReservationMySuffix> = ref({});

    const retrieveHotelReservationMySuffix = async hotelReservationId => {
      try {
        const res = await hotelReservationService().find(hotelReservationId);
        hotelReservation.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.hotelReservationId) {
      retrieveHotelReservationMySuffix(route.params.hotelReservationId);
    }

    return {
      ...dateFormat,
      alertService,
      hotelReservation,

      previousState,
      t$: useI18n().t,
    };
  },
});
