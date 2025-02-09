import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RestorationReservationMySuffixService from './restoration-reservation-my-suffix.service';
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
import {
  type IRestorationReservationMySuffix,
  RestorationReservationMySuffix,
} from '@/shared/model/restoration-reservation-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RestorationReservationMySuffixUpdate',
  setup() {
    const restorationReservationService = inject('restorationReservationService', () => new RestorationReservationMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const restorationReservation: Ref<IRestorationReservationMySuffix> = ref(new RestorationReservationMySuffix());

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

    const retrieveRestorationReservationMySuffix = async restorationReservationId => {
      try {
        const res = await restorationReservationService().find(restorationReservationId);
        res.reservationTimestamp = new Date(res.reservationTimestamp);
        res.arrivalDate = new Date(res.arrivalDate);
        restorationReservation.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.restorationReservationId) {
      retrieveRestorationReservationMySuffix(route.params.restorationReservationId);
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
      depositAmount: {},
      totalAmount: {},
      shift: {},
      guestCount: {},
      arrivalDate: {},
      restaurantName: {},
      restaurantId: {},
      cancelled: {},
      expenses: {},
      metadata: {},
      oneCustomer: {},
    };
    const v$ = useVuelidate(validationRules, restorationReservation as any);
    v$.value.$validate();

    return {
      restorationReservationService,
      alertService,
      restorationReservation,
      previousState,
      isSaving,
      currentLanguage,
      cancelleds,
      expenses,
      metadata,
      oneCustomers,
      v$,
      ...useDateFormat({ entityRef: restorationReservation }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.restorationReservation.id) {
        this.restorationReservationService()
          .update(this.restorationReservation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.restorationReservation.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.restorationReservationService()
          .create(this.restorationReservation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.restorationReservation.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
