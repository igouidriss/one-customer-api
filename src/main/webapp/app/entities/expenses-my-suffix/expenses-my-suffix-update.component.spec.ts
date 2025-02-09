import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ExpensesMySuffixUpdate from './expenses-my-suffix-update.vue';
import ExpensesMySuffixService from './expenses-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type ExpensesMySuffixUpdateComponentType = InstanceType<typeof ExpensesMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const expensesSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ExpensesMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ExpensesMySuffix Management Update Component', () => {
    let comp: ExpensesMySuffixUpdateComponentType;
    let expensesServiceStub: SinonStubbedInstance<ExpensesMySuffixService>;

    beforeEach(() => {
      route = {};
      expensesServiceStub = sinon.createStubInstance<ExpensesMySuffixService>(ExpensesMySuffixService);
      expensesServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          expensesService: () => expensesServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ExpensesMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.expenses = expensesSample;
        expensesServiceStub.update.resolves(expensesSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(expensesServiceStub.update.calledWith(expensesSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        expensesServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ExpensesMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.expenses = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(expensesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        expensesServiceStub.find.resolves(expensesSample);
        expensesServiceStub.retrieve.resolves([expensesSample]);

        // WHEN
        route = {
          params: {
            expensesId: `${expensesSample.id}`,
          },
        };
        const wrapper = shallowMount(ExpensesMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.expenses).toMatchObject(expensesSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        expensesServiceStub.find.resolves(expensesSample);
        const wrapper = shallowMount(ExpensesMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
