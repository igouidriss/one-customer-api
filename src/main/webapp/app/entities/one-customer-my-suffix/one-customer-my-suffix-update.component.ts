import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OneCustomerMySuffixService from './one-customer-my-suffix.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import GoldenRecordMySuffixService from '@/entities/golden-record-my-suffix/golden-record-my-suffix.service';
import { type IGoldenRecordMySuffix } from '@/shared/model/golden-record-my-suffix.model';
import { type IOneCustomerMySuffix, OneCustomerMySuffix } from '@/shared/model/one-customer-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OneCustomerMySuffixUpdate',
  setup() {
    const oneCustomerService = inject('oneCustomerService', () => new OneCustomerMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const oneCustomer: Ref<IOneCustomerMySuffix> = ref(new OneCustomerMySuffix());

    const goldenRecordService = inject('goldenRecordService', () => new GoldenRecordMySuffixService());

    const goldenRecords: Ref<IGoldenRecordMySuffix[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveOneCustomerMySuffix = async oneCustomerId => {
      try {
        const res = await oneCustomerService().find(oneCustomerId);
        res.timestamp = new Date(res.timestamp);
        oneCustomer.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.oneCustomerId) {
      retrieveOneCustomerMySuffix(route.params.oneCustomerId);
    }

    const initRelationships = () => {
      goldenRecordService()
        .retrieve()
        .then(res => {
          goldenRecords.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      domaine: {},
      aggregateId: {},
      aggregateType: {},
      timestamp: {},
      goldenRecord: {},
      hotelReservations: {},
      restorationReservations: {},
    };
    const v$ = useVuelidate(validationRules, oneCustomer as any);
    v$.value.$validate();

    return {
      oneCustomerService,
      alertService,
      oneCustomer,
      previousState,
      isSaving,
      currentLanguage,
      goldenRecords,
      v$,
      ...useDateFormat({ entityRef: oneCustomer }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.oneCustomer.id) {
        this.oneCustomerService()
          .update(this.oneCustomer)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.oneCustomer.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.oneCustomerService()
          .create(this.oneCustomer)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.oneCustomer.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
