import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import GoldenRecordMySuffixService from './golden-record-my-suffix.service';
import { useDateFormat } from '@/shared/composables';
import { type IGoldenRecordMySuffix } from '@/shared/model/golden-record-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'GoldenRecordMySuffixDetails',
  setup() {
    const dateFormat = useDateFormat();
    const goldenRecordService = inject('goldenRecordService', () => new GoldenRecordMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const goldenRecord: Ref<IGoldenRecordMySuffix> = ref({});

    const retrieveGoldenRecordMySuffix = async goldenRecordId => {
      try {
        const res = await goldenRecordService().find(goldenRecordId);
        goldenRecord.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.goldenRecordId) {
      retrieveGoldenRecordMySuffix(route.params.goldenRecordId);
    }

    return {
      ...dateFormat,
      alertService,
      goldenRecord,

      previousState,
      t$: useI18n().t,
    };
  },
});
