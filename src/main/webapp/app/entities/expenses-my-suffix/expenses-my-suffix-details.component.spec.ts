import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ExpensesMySuffixDetails from './expenses-my-suffix-details.vue';
import ExpensesMySuffixService from './expenses-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type ExpensesMySuffixDetailsComponentType = InstanceType<typeof ExpensesMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const expensesSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ExpensesMySuffix Management Detail Component', () => {
    let expensesServiceStub: SinonStubbedInstance<ExpensesMySuffixService>;
    let mountOptions: MountingOptions<ExpensesMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      expensesServiceStub = sinon.createStubInstance<ExpensesMySuffixService>(ExpensesMySuffixService);

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
          expensesService: () => expensesServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        expensesServiceStub.find.resolves(expensesSample);
        route = {
          params: {
            expensesId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(ExpensesMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.expenses).toMatchObject(expensesSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        expensesServiceStub.find.resolves(expensesSample);
        const wrapper = shallowMount(ExpensesMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
