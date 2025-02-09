import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import ExpenseMySuffix from './expense-my-suffix.vue';
import ExpenseMySuffixService from './expense-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type ExpenseMySuffixComponentType = InstanceType<typeof ExpenseMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('ExpenseMySuffix Management Component', () => {
    let expenseServiceStub: SinonStubbedInstance<ExpenseMySuffixService>;
    let mountOptions: MountingOptions<ExpenseMySuffixComponentType>['global'];

    beforeEach(() => {
      expenseServiceStub = sinon.createStubInstance<ExpenseMySuffixService>(ExpenseMySuffixService);
      expenseServiceStub.retrieve.resolves({ headers: {} });

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
          expenseService: () => expenseServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        expenseServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(ExpenseMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(expenseServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.expenses[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: ExpenseMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(ExpenseMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        expenseServiceStub.retrieve.reset();
        expenseServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        expenseServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeExpenseMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(expenseServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(expenseServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
