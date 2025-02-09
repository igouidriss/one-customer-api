import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import HotelReservationMySuffixService from './hotel-reservation-my-suffix.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CancelledMySuffixService from '@/entities/cancelled-my-suffix/cancelled-my-suffix.service';
import { type ICancelledMySuffix } from '@/shared/model/cancelled-my-suffix.model';
import ExpensesMySuffixService from '@/entities/expenses-my-suffix/expenses-my-suffix.service';
import { type IExpensesMySuffix } from '@/shared/model/expenses-my-suffix.model';
import MetadataMySuffixService from '@/entities/metadata-my-suffix/metadata-my-suffix.service';
import { type IMetadataMySuffix } from '@/shared/model/metadata-my-suffix.model';
import OneCustomerMySuffixService from '@/entities/one-customer-my-suffix/one-customer-my-suffix.service';
import { type IOneCustomerMySuffix } from '@/shared/model/one-customer-my-suffix.model';
import { HotelReservationMySuffix, type IHotelReservationMySuffix } from '@/shared/model/hotel-reservation-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'HotelReservationMySuffixUpdate',
  setup() {
    const hotelReservationService = inject('hotelReservationService', () => new HotelReservationMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const hotelReservation: Ref<IHotelReservationMySuffix> = ref(new HotelReservationMySuffix());

    const cancelledService = inject('cancelledService', () => new CancelledMySuffixService());

    const cancelleds: Ref<ICancelledMySuffix[]> = ref([]);

    const expensesService = inject('expensesService', () => new ExpensesMySuffixService());

    const expenses: Ref<IExpensesMySuffix[]> = ref([]);

    const metadataService = inject('metadataService', () => new MetadataMySuffixService());

    const metadata: Ref<IMetadataMySuffix[]> = ref([]);

    const oneCustomerService = inject('oneCustomerService', () => new OneCustomerMySuffixService());

    const oneCustomers: Ref<IOneCustomerMySuffix[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveHotelReservationMySuffix = async hotelReservationId => {
      try {
        const res = await hotelReservationService().find(hotelReservationId);
        res.reservationTimestamp = new Date(res.reservationTimestamp);
        res.arrivalDate = new Date(res.arrivalDate);
        res.leaveDate = new Date(res.leaveDate);
        hotelReservation.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.hotelReservationId) {
      retrieveHotelReservationMySuffix(route.params.hotelReservationId);
    }

    const initRelationships = () => {
      cancelledService()
        .retrieve()
        .then(res => {
          cancelleds.value = res.data;
        });
      expensesService()
        .retrieve()
        .then(res => {
          expenses.value = res.data;
        });
      metadataService()
        .retrieve()
        .then(res => {
          metadata.value = res.data;
        });
      oneCustomerService()
        .retrieve()
        .then(res => {
          oneCustomers.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      aggregateId: {},
      aggregateType: {},
      clientId: {},
      domaine: {},
      source: {},
      reservationTimestamp: {},
      projection: {},
      date: {},
      totalAmount: {},
      arrivalDate: {},
      leaveDate: {},
      guestCount: {},
      hotelName: {},
      hotelId: {},
      cancelled: {},
      expenses: {},
      metadata: {},
      oneCustomer: {},
    };
    const v$ = useVuelidate(validationRules, hotelReservation as any);
    v$.value.$validate();

    return {
      hotelReservationService,
      alertService,
      hotelReservation,
      previousState,
      isSaving,
      currentLanguage,
      cancelleds,
      expenses,
      metadata,
      oneCustomers,
      v$,
      ...useDateFormat({ entityRef: hotelReservation }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.hotelReservation.id) {
        this.hotelReservationService()
          .update(this.hotelReservation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.hotelReservation.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.hotelReservationService()
          .create(this.hotelReservation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.hotelReservation.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
