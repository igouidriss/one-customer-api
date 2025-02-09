import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import GoldenRecordMySuffix from './golden-record-my-suffix.vue';
import GoldenRecordMySuffixService from './golden-record-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type GoldenRecordMySuffixComponentType = InstanceType<typeof GoldenRecordMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('GoldenRecordMySuffix Management Component', () => {
    let goldenRecordServiceStub: SinonStubbedInstance<GoldenRecordMySuffixService>;
    let mountOptions: MountingOptions<GoldenRecordMySuffixComponentType>['global'];

    beforeEach(() => {
      goldenRecordServiceStub = sinon.createStubInstance<GoldenRecordMySuffixService>(GoldenRecordMySuffixService);
      goldenRecordServiceStub.retrieve.resolves({ headers: {} });

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
          goldenRecordService: () => goldenRecordServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        goldenRecordServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(GoldenRecordMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(goldenRecordServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.goldenRecords[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: GoldenRecordMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(GoldenRecordMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        goldenRecordServiceStub.retrieve.reset();
        goldenRecordServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        goldenRecordServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeGoldenRecordMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(goldenRecordServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(goldenRecordServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
