import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import AddressMySuffix from './address-my-suffix.vue';
import AddressMySuffixService from './address-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type AddressMySuffixComponentType = InstanceType<typeof AddressMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('AddressMySuffix Management Component', () => {
    let addressServiceStub: SinonStubbedInstance<AddressMySuffixService>;
    let mountOptions: MountingOptions<AddressMySuffixComponentType>['global'];

    beforeEach(() => {
      addressServiceStub = sinon.createStubInstance<AddressMySuffixService>(AddressMySuffixService);
      addressServiceStub.retrieve.resolves({ headers: {} });

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
          addressService: () => addressServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        addressServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(AddressMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(addressServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.addresses[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: AddressMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(AddressMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        addressServiceStub.retrieve.reset();
        addressServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        addressServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeAddressMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(addressServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(addressServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
