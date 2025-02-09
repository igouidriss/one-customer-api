import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import SourceReferenceMySuffix from './source-reference-my-suffix.vue';
import SourceReferenceMySuffixService from './source-reference-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type SourceReferenceMySuffixComponentType = InstanceType<typeof SourceReferenceMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('SourceReferenceMySuffix Management Component', () => {
    let sourceReferenceServiceStub: SinonStubbedInstance<SourceReferenceMySuffixService>;
    let mountOptions: MountingOptions<SourceReferenceMySuffixComponentType>['global'];

    beforeEach(() => {
      sourceReferenceServiceStub = sinon.createStubInstance<SourceReferenceMySuffixService>(SourceReferenceMySuffixService);
      sourceReferenceServiceStub.retrieve.resolves({ headers: {} });

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
          sourceReferenceService: () => sourceReferenceServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        sourceReferenceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(SourceReferenceMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(sourceReferenceServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.sourceReferences[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: SourceReferenceMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(SourceReferenceMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        sourceReferenceServiceStub.retrieve.reset();
        sourceReferenceServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        sourceReferenceServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeSourceReferenceMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(sourceReferenceServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(sourceReferenceServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
