import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import SourceReferenceMySuffixService from './source-reference-my-suffix.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import GoldenRecordMySuffixService from '@/entities/golden-record-my-suffix/golden-record-my-suffix.service';
import { type IGoldenRecordMySuffix } from '@/shared/model/golden-record-my-suffix.model';
import { type ISourceReferenceMySuffix, SourceReferenceMySuffix } from '@/shared/model/source-reference-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SourceReferenceMySuffixUpdate',
  setup() {
    const sourceReferenceService = inject('sourceReferenceService', () => new SourceReferenceMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const sourceReference: Ref<ISourceReferenceMySuffix> = ref(new SourceReferenceMySuffix());

    const goldenRecordService = inject('goldenRecordService', () => new GoldenRecordMySuffixService());

    const goldenRecords: Ref<IGoldenRecordMySuffix[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveSourceReferenceMySuffix = async sourceReferenceId => {
      try {
        const res = await sourceReferenceService().find(sourceReferenceId);
        sourceReference.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.sourceReferenceId) {
      retrieveSourceReferenceMySuffix(route.params.sourceReferenceId);
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
      source: {},
      value: {},
      goldenRecord: {},
    };
    const v$ = useVuelidate(validationRules, sourceReference as any);
    v$.value.$validate();

    return {
      sourceReferenceService,
      alertService,
      sourceReference,
      previousState,
      isSaving,
      currentLanguage,
      goldenRecords,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.sourceReference.id) {
        this.sourceReferenceService()
          .update(this.sourceReference)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.sourceReference.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.sourceReferenceService()
          .create(this.sourceReference)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.sourceReference.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
