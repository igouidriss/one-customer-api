import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EmailMySuffixService from './email-my-suffix.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PayloadMySuffixService from '@/entities/payload-my-suffix/payload-my-suffix.service';
import { type IPayloadMySuffix } from '@/shared/model/payload-my-suffix.model';
import { EmailMySuffix, type IEmailMySuffix } from '@/shared/model/email-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EmailMySuffixUpdate',
  setup() {
    const emailService = inject('emailService', () => new EmailMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const email: Ref<IEmailMySuffix> = ref(new EmailMySuffix());

    const payloadService = inject('payloadService', () => new PayloadMySuffixService());

    const payloads: Ref<IPayloadMySuffix[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveEmailMySuffix = async emailId => {
      try {
        const res = await emailService().find(emailId);
        email.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.emailId) {
      retrieveEmailMySuffix(route.params.emailId);
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
      value: {},
      payload: {},
    };
    const v$ = useVuelidate(validationRules, email as any);
    v$.value.$validate();

    return {
      emailService,
      alertService,
      email,
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
      if (this.email.id) {
        this.emailService()
          .update(this.email)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.email.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.emailService()
          .create(this.email)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.email.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
