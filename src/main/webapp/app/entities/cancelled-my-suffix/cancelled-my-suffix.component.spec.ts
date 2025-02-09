import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import CancelledMySuffix from './cancelled-my-suffix.vue';
import CancelledMySuffixService from './cancelled-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type CancelledMySuffixComponentType = InstanceType<typeof CancelledMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('CancelledMySuffix Management Component', () => {
    let cancelledServiceStub: SinonStubbedInstance<CancelledMySuffixService>;
    let mountOptions: MountingOptions<CancelledMySuffixComponentType>['global'];

    beforeEach(() => {
      cancelledServiceStub = sinon.createStubInstance<CancelledMySuffixService>(CancelledMySuffixService);
      cancelledServiceStub.retrieve.resolves({ headers: {} });

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
          cancelledService: () => cancelledServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        cancelledServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(CancelledMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(cancelledServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.cancelleds[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: CancelledMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(CancelledMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        cancelledServiceStub.retrieve.reset();
        cancelledServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        cancelledServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeCancelledMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(cancelledServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(cancelledServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
