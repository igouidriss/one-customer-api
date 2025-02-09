import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AddressMySuffixUpdate from './address-my-suffix-update.vue';
import AddressMySuffixService from './address-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

import PayloadMySuffixService from '@/entities/payload-my-suffix/payload-my-suffix.service';

type AddressMySuffixUpdateComponentType = InstanceType<typeof AddressMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const addressSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AddressMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('AddressMySuffix Management Update Component', () => {
    let comp: AddressMySuffixUpdateComponentType;
    let addressServiceStub: SinonStubbedInstance<AddressMySuffixService>;

    beforeEach(() => {
      route = {};
      addressServiceStub = sinon.createStubInstance<AddressMySuffixService>(AddressMySuffixService);
      addressServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          addressService: () => addressServiceStub,
          payloadService: () =>
            sinon.createStubInstance<PayloadMySuffixService>(PayloadMySuffixService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(AddressMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.address = addressSample;
        addressServiceStub.update.resolves(addressSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(addressServiceStub.update.calledWith(addressSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        addressServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AddressMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.address = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(addressServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        addressServiceStub.find.resolves(addressSample);
        addressServiceStub.retrieve.resolves([addressSample]);

        // WHEN
        route = {
          params: {
            addressId: `${addressSample.id}`,
          },
        };
        const wrapper = shallowMount(AddressMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.address).toMatchObject(addressSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        addressServiceStub.find.resolves(addressSample);
        const wrapper = shallowMount(AddressMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
