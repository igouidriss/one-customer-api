import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MetadataMySuffixService from './metadata-my-suffix.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IMetadataMySuffix, MetadataMySuffix } from '@/shared/model/metadata-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MetadataMySuffixUpdate',
  setup() {
    const metadataService = inject('metadataService', () => new MetadataMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const metadata: Ref<IMetadataMySuffix> = ref(new MetadataMySuffix());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMetadataMySuffix = async metadataId => {
      try {
        const res = await metadataService().find(metadataId);
        res.metaTimestamp = new Date(res.metaTimestamp);
        metadata.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.metadataId) {
      retrieveMetadataMySuffix(route.params.metadataId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      idEvent: {},
      metaTimestamp: {},
    };
    const v$ = useVuelidate(validationRules, metadata as any);
    v$.value.$validate();

    return {
      metadataService,
      alertService,
      metadata,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      ...useDateFormat({ entityRef: metadata }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.metadata.id) {
        this.metadataService()
          .update(this.metadata)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.metadata.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.metadataService()
          .create(this.metadata)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.metadata.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
