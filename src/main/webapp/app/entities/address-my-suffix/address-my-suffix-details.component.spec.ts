import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AddressMySuffixDetails from './address-my-suffix-details.vue';
import AddressMySuffixService from './address-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type AddressMySuffixDetailsComponentType = InstanceType<typeof AddressMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const addressSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('AddressMySuffix Management Detail Component', () => {
    let addressServiceStub: SinonStubbedInstance<AddressMySuffixService>;
    let mountOptions: MountingOptions<AddressMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      addressServiceStub = sinon.createStubInstance<AddressMySuffixService>(AddressMySuffixService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          addressService: () => addressServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        addressServiceStub.find.resolves(addressSample);
        route = {
          params: {
            addressId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(AddressMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.address).toMatchObject(addressSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        addressServiceStub.find.resolves(addressSample);
        const wrapper = shallowMount(AddressMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
