import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PhoneMySuffixService from './phone-my-suffix.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PayloadMySuffixService from '@/entities/payload-my-suffix/payload-my-suffix.service';
import { type IPayloadMySuffix } from '@/shared/model/payload-my-suffix.model';
import { type IPhoneMySuffix, PhoneMySuffix } from '@/shared/model/phone-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PhoneMySuffixUpdate',
  setup() {
    const phoneService = inject('phoneService', () => new PhoneMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const phone: Ref<IPhoneMySuffix> = ref(new PhoneMySuffix());

    const payloadService = inject('payloadService', () => new PayloadMySuffixService());

    const payloads: Ref<IPayloadMySuffix[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePhoneMySuffix = async phoneId => {
      try {
        const res = await phoneService().find(phoneId);
        phone.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.phoneId) {
      retrievePhoneMySuffix(route.params.phoneId);
    }

    const initRelationships = () => {
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
      type: {},
      number: {},
      payload: {},
    };
    const v$ = useVuelidate(validationRules, phone as any);
    v$.value.$validate();

    return {
      phoneService,
      alertService,
      phone,
      previousState,
      isSaving,
      currentLanguage,
      payloads,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.phone.id) {
        this.phoneService()
          .update(this.phone)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.phone.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.phoneService()
          .create(this.phone)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.phone.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
