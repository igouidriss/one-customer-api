import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import MetadataMySuffix from './metadata-my-suffix.vue';
import MetadataMySuffixService from './metadata-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type MetadataMySuffixComponentType = InstanceType<typeof MetadataMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('MetadataMySuffix Management Component', () => {
    let metadataServiceStub: SinonStubbedInstance<MetadataMySuffixService>;
    let mountOptions: MountingOptions<MetadataMySuffixComponentType>['global'];

    beforeEach(() => {
      metadataServiceStub = sinon.createStubInstance<MetadataMySuffixService>(MetadataMySuffixService);
      metadataServiceStub.retrieve.resolves({ headers: {} });

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
          metadataService: () => metadataServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        metadataServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(MetadataMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(metadataServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.metadata[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MetadataMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(MetadataMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        metadataServiceStub.retrieve.reset();
        metadataServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        metadataServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMetadataMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(metadataServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(metadataServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
