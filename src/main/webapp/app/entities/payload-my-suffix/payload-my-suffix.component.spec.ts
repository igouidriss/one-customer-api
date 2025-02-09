import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import PayloadMySuffix from './payload-my-suffix.vue';
import PayloadMySuffixService from './payload-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type PayloadMySuffixComponentType = InstanceType<typeof PayloadMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('PayloadMySuffix Management Component', () => {
    let payloadServiceStub: SinonStubbedInstance<PayloadMySuffixService>;
    let mountOptions: MountingOptions<PayloadMySuffixComponentType>['global'];

    beforeEach(() => {
      payloadServiceStub = sinon.createStubInstance<PayloadMySuffixService>(PayloadMySuffixService);
      payloadServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          payloadService: () => payloadServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        payloadServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(PayloadMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(payloadServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.payloads[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: PayloadMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(PayloadMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        payloadServiceStub.retrieve.reset();
        payloadServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        payloadServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removePayloadMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(payloadServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(payloadServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
