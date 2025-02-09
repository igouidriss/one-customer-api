import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CancelledMySuffixService from './cancelled-my-suffix.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { CancelledMySuffix, type ICancelledMySuffix } from '@/shared/model/cancelled-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CancelledMySuffixUpdate',
  setup() {
    const cancelledService = inject('cancelledService', () => new CancelledMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const cancelled: Ref<ICancelledMySuffix> = ref(new CancelledMySuffix());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCancelledMySuffix = async cancelledId => {
      try {
        const res = await cancelledService().find(cancelledId);
        res.cancelDate = new Date(res.cancelDate);
        cancelled.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.cancelledId) {
      retrieveCancelledMySuffix(route.params.cancelledId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      cancelReason: {},
      isItCancelled: {},
      cancelDate: {},
    };
    const v$ = useVuelidate(validationRules, cancelled as any);
    v$.value.$validate();

    return {
      cancelledService,
      alertService,
      cancelled,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      ...useDateFormat({ entityRef: cancelled }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.cancelled.id) {
        this.cancelledService()
          .update(this.cancelled)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.cancelled.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.cancelledService()
          .create(this.cancelled)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.cancelled.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
