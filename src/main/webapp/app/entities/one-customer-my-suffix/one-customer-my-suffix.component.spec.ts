import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import OneCustomerMySuffix from './one-customer-my-suffix.vue';
import OneCustomerMySuffixService from './one-customer-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type OneCustomerMySuffixComponentType = InstanceType<typeof OneCustomerMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('OneCustomerMySuffix Management Component', () => {
    let oneCustomerServiceStub: SinonStubbedInstance<OneCustomerMySuffixService>;
    let mountOptions: MountingOptions<OneCustomerMySuffixComponentType>['global'];

    beforeEach(() => {
      oneCustomerServiceStub = sinon.createStubInstance<OneCustomerMySuffixService>(OneCustomerMySuffixService);
      oneCustomerServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          jhiItemCount: true,
          bPagination: true,
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'jhi-sort-indicator': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          oneCustomerService: () => oneCustomerServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        oneCustomerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(OneCustomerMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(oneCustomerServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.oneCustomers[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(OneCustomerMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(oneCustomerServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: OneCustomerMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(OneCustomerMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        oneCustomerServiceStub.retrieve.reset();
        oneCustomerServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        oneCustomerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(oneCustomerServiceStub.retrieve.called).toBeTruthy();
        expect(comp.oneCustomers[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(oneCustomerServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        oneCustomerServiceStub.retrieve.reset();
        oneCustomerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(oneCustomerServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.oneCustomers[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(oneCustomerServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        oneCustomerServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeOneCustomerMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(oneCustomerServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(oneCustomerServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
