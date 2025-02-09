import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import EmailMySuffixService from './email-my-suffix.service';
import { type IEmailMySuffix } from '@/shared/model/email-my-suffix.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EmailMySuffix',
  setup() {
    const { t: t$ } = useI18n();
    const emailService = inject('emailService', () => new EmailMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const emails: Ref<IEmailMySuffix[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveEmailMySuffixs = async () => {
      isFetching.value = true;
      try {
        const res = await emailService().retrieve();
        emails.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveEmailMySuffixs();
    };

    onMounted(async () => {
      await retrieveEmailMySuffixs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IEmailMySuffix) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeEmailMySuffix = async () => {
      try {
        await emailService().delete(removeId.value);
        const message = t$('rcuApplicationApp.email.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveEmailMySuffixs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      emails,
      handleSyncList,
      isFetching,
      retrieveEmailMySuffixs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeEmailMySuffix,
      t$,
    };
  },
});
