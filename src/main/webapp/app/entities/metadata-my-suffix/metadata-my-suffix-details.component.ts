import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MetadataMySuffixService from './metadata-my-suffix.service';
import { useDateFormat } from '@/shared/composables';
import { type IMetadataMySuffix } from '@/shared/model/metadata-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MetadataMySuffixDetails',
  setup() {
    const dateFormat = useDateFormat();
    const metadataService = inject('metadataService', () => new MetadataMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const metadata: Ref<IMetadataMySuffix> = ref({});

    const retrieveMetadataMySuffix = async metadataId => {
      try {
        const res = await metadataService().find(metadataId);
        metadata.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.metadataId) {
      retrieveMetadataMySuffix(route.params.metadataId);
    }

    return {
      ...dateFormat,
      alertService,
      metadata,

      previousState,
      t$: useI18n().t,
    };
  },
});
