import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EmailMySuffixService from './email-my-suffix.service';
import { type IEmailMySuffix } from '@/shared/model/email-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EmailMySuffixDetails',
  setup() {
    const emailService = inject('emailService', () => new EmailMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const email: Ref<IEmailMySuffix> = ref({});

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

    return {
      alertService,
      email,

      previousState,
      t$: useI18n().t,
    };
  },
});
