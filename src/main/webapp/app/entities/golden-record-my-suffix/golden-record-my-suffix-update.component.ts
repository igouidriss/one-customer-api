import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import GoldenRecordMySuffixService from './golden-record-my-suffix.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CancelledMySuffixService from '@/entities/cancelled-my-suffix/cancelled-my-suffix.service';
import { type ICancelledMySuffix } from '@/shared/model/cancelled-my-suffix.model';
import PayloadMySuffixService from '@/entities/payload-my-suffix/payload-my-suffix.service';
import { type IPayloadMySuffix } from '@/shared/model/payload-my-suffix.model';
import { GoldenRecordMySuffix, type IGoldenRecordMySuffix } from '@/shared/model/golden-record-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'GoldenRecordMySuffixUpdate',
  setup() {
    const goldenRecordService = inject('goldenRecordService', () => new GoldenRecordMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const goldenRecord: Ref<IGoldenRecordMySuffix> = ref(new GoldenRecordMySuffix());

    const cancelledService = inject('cancelledService', () => new CancelledMySuffixService());

    const cancelleds: Ref<ICancelledMySuffix[]> = ref([]);

    const payloadService = inject('payloadService', () => new PayloadMySuffixService());

    const payloads: Ref<IPayloadMySuffix[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveGoldenRecordMySuffix = async goldenRecordId => {
      try {
        const res = await goldenRecordService().find(goldenRecordId);
        res.recordTimestamp = new Date(res.recordTimestamp);
        goldenRecord.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.goldenRecordId) {
      retrieveGoldenRecordMySuffix(route.params.goldenRecordId);
    }

    const initRelationships = () => {
      cancelledService()
        .retrieve()
        .then(res => {
          cancelleds.value = res.data;
        });
      payloadService()
        .retrieve()
        .then(res => {
          payloads.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      aggregateId: {},
      aggregateType: {},
      domaine: {},
      mdmId: {},
      source: {},
      recordTimestamp: {},
      cancelled: {},
      payload: {},
      sourceReferences: {},
    };
    const v$ = useVuelidate(validationRules, goldenRecord as any);
    v$.value.$validate();

    return {
      goldenRecordService,
      alertService,
      goldenRecord,
      previousState,
      isSaving,
      currentLanguage,
      cancelleds,
      payloads,
      v$,
      ...useDateFormat({ entityRef: goldenRecord }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.goldenRecord.id) {
        this.goldenRecordService()
          .update(this.goldenRecord)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.goldenRecord.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.goldenRecordService()
          .create(this.goldenRecord)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.goldenRecord.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
