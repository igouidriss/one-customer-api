import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OneCustomerMySuffixService from './one-customer-my-suffix.service';
import { useDateFormat } from '@/shared/composables';
import { type IOneCustomerMySuffix } from '@/shared/model/one-customer-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OneCustomerMySuffixDetails',
  setup() {
    const dateFormat = useDateFormat();
    const oneCustomerService = inject('oneCustomerService', () => new OneCustomerMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const oneCustomer: Ref<IOneCustomerMySuffix> = ref({});

    const retrieveOneCustomerMySuffix = async oneCustomerId => {
      try {
        const res = await oneCustomerService().find(oneCustomerId);
        oneCustomer.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.oneCustomerId) {
      retrieveOneCustomerMySuffix(route.params.oneCustomerId);
    }

    return {
      ...dateFormat,
      alertService,
      oneCustomer,

      previousState,
      t$: useI18n().t,
    };
  },
});
