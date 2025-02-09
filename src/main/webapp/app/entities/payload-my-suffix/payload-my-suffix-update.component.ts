import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PayloadMySuffixService from './payload-my-suffix.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MetadataMySuffixService from '@/entities/metadata-my-suffix/metadata-my-suffix.service';
import { type IMetadataMySuffix } from '@/shared/model/metadata-my-suffix.model';
import { type IPayloadMySuffix, PayloadMySuffix } from '@/shared/model/payload-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PayloadMySuffixUpdate',
  setup() {
    const payloadService = inject('payloadService', () => new PayloadMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const payload: Ref<IPayloadMySuffix> = ref(new PayloadMySuffix());

    const metadataService = inject('metadataService', () => new MetadataMySuffixService());

    const metadata: Ref<IMetadataMySuffix[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      metadataService()
        .retrieve()
        .then(res => {
          metadata.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      lastName: {},
      firstName: {},
      birthDate: {},
      lang: {},
      isVip: {},
      metadata: {},
      emails: {},
      phones: {},
      addresses: {},
    };
    const v$ = useVuelidate(validationRules, payload as any);
    v$.value.$validate();

    return {
      payloadService,
      alertService,
      payload,
      previousState,
      isSaving,
      currentLanguage,
      metadata,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.payload.id) {
        this.payloadService()
          .update(this.payload)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.payload.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.payloadService()
          .create(this.payload)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.payload.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
