import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import ExpensesMySuffix from './expenses-my-suffix.vue';
import ExpensesMySuffixService from './expenses-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type ExpensesMySuffixComponentType = InstanceType<typeof ExpensesMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('ExpensesMySuffix Management Component', () => {
    let expensesServiceStub: SinonStubbedInstance<ExpensesMySuffixService>;
    let mountOptions: MountingOptions<ExpensesMySuffixComponentType>['global'];

    beforeEach(() => {
      expensesServiceStub = sinon.createStubInstance<ExpensesMySuffixService>(ExpensesMySuffixService);
      expensesServiceStub.retrieve.resolves({ headers: {} });

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
          expensesService: () => expensesServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        expensesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(ExpensesMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(expensesServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.expenses[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: ExpensesMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(ExpensesMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        expensesServiceStub.retrieve.reset();
        expensesServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        expensesServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeExpensesMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(expensesServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(expensesServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
