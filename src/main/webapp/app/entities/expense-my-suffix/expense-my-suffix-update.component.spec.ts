import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import ExpenseMySuffixUpdate from './expense-my-suffix-update.vue';
import ExpenseMySuffixService from './expense-my-suffix.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import MetadataMySuffixService from '@/entities/metadata-my-suffix/metadata-my-suffix.service';
import ExpensesMySuffixService from '@/entities/expenses-my-suffix/expenses-my-suffix.service';

type ExpenseMySuffixUpdateComponentType = InstanceType<typeof ExpenseMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const expenseSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ExpenseMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ExpenseMySuffix Management Update Component', () => {
    let comp: ExpenseMySuffixUpdateComponentType;
    let expenseServiceStub: SinonStubbedInstance<ExpenseMySuffixService>;

    beforeEach(() => {
      route = {};
      expenseServiceStub = sinon.createStubInstance<ExpenseMySuffixService>(ExpenseMySuffixService);
      expenseServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          expenseService: () => expenseServiceStub,
          metadataService: () =>
            sinon.createStubInstance<MetadataMySuffixService>(MetadataMySuffixService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          expensesService: () =>
            sinon.createStubInstance<ExpensesMySuffixService>(ExpensesMySuffixService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(ExpenseMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ExpenseMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.expense = expenseSample;
        expenseServiceStub.update.resolves(expenseSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(expenseServiceStub.update.calledWith(expenseSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        expenseServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ExpenseMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.expense = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(expenseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        expenseServiceStub.find.resolves(expenseSample);
        expenseServiceStub.retrieve.resolves([expenseSample]);

        // WHEN
        route = {
          params: {
            expenseId: `${expenseSample.id}`,
          },
        };
        const wrapper = shallowMount(ExpenseMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.expense).toMatchObject(expenseSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        expenseServiceStub.find.resolves(expenseSample);
        const wrapper = shallowMount(ExpenseMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
