import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import SourceReferenceMySuffixService from './source-reference-my-suffix.service';
import { type ISourceReferenceMySuffix } from '@/shared/model/source-reference-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SourceReferenceMySuffixDetails',
  setup() {
    const sourceReferenceService = inject('sourceReferenceService', () => new SourceReferenceMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const sourceReference: Ref<ISourceReferenceMySuffix> = ref({});

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

    return {
      alertService,
      sourceReference,

      previousState,
      t$: useI18n().t,
    };
  },
});
