import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AddressMySuffixService from './address-my-suffix.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PayloadMySuffixService from '@/entities/payload-my-suffix/payload-my-suffix.service';
import { type IPayloadMySuffix } from '@/shared/model/payload-my-suffix.model';
import { AddressMySuffix, type IAddressMySuffix } from '@/shared/model/address-my-suffix.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AddressMySuffixUpdate',
  setup() {
    const addressService = inject('addressService', () => new AddressMySuffixService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const address: Ref<IAddressMySuffix> = ref(new AddressMySuffix());

    const payloadService = inject('payloadService', () => new PayloadMySuffixService());

    const payloads: Ref<IPayloadMySuffix[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAddressMySuffix = async addressId => {
      try {
        const res = await addressService().find(addressId);
        address.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.addressId) {
      retrieveAddressMySuffix(route.params.addressId);
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
      zipCode: {},
      city: {},
      country: {},
      line1: {},
      line2: {},
      line3: {},
      payload: {},
    };
    const v$ = useVuelidate(validationRules, address as any);
    v$.value.$validate();

    return {
      addressService,
      alertService,
      address,
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
      if (this.address.id) {
        this.addressService()
          .update(this.address)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('rcuApplicationApp.address.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.addressService()
          .create(this.address)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('rcuApplicationApp.address.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
