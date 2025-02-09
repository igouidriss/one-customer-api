import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ExpenseMySuffixDetails from './expense-my-suffix-details.vue';
import ExpenseMySuffixService from './expense-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type ExpenseMySuffixDetailsComponentType = InstanceType<typeof ExpenseMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const expenseSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ExpenseMySuffix Management Detail Component', () => {
    let expenseServiceStub: SinonStubbedInstance<ExpenseMySuffixService>;
    let mountOptions: MountingOptions<ExpenseMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      expenseServiceStub = sinon.createStubInstance<ExpenseMySuffixService>(ExpenseMySuffixService);

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
          expenseService: () => expenseServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        expenseServiceStub.find.resolves(expenseSample);
        route = {
          params: {
            expenseId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(ExpenseMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.expense).toMatchObject(expenseSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        expenseServiceStub.find.resolves(expenseSample);
        const wrapper = shallowMount(ExpenseMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
