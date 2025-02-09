import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OneCustomerMySuffixDetails from './one-customer-my-suffix-details.vue';
import OneCustomerMySuffixService from './one-customer-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type OneCustomerMySuffixDetailsComponentType = InstanceType<typeof OneCustomerMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const oneCustomerSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('OneCustomerMySuffix Management Detail Component', () => {
    let oneCustomerServiceStub: SinonStubbedInstance<OneCustomerMySuffixService>;
    let mountOptions: MountingOptions<OneCustomerMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      oneCustomerServiceStub = sinon.createStubInstance<OneCustomerMySuffixService>(OneCustomerMySuffixService);

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
          oneCustomerService: () => oneCustomerServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        oneCustomerServiceStub.find.resolves(oneCustomerSample);
        route = {
          params: {
            oneCustomerId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(OneCustomerMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.oneCustomer).toMatchObject(oneCustomerSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        oneCustomerServiceStub.find.resolves(oneCustomerSample);
        const wrapper = shallowMount(OneCustomerMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
