import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import RestorationReservationMySuffix from './restoration-reservation-my-suffix.vue';
import RestorationReservationMySuffixService from './restoration-reservation-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type RestorationReservationMySuffixComponentType = InstanceType<typeof RestorationReservationMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('RestorationReservationMySuffix Management Component', () => {
    let restorationReservationServiceStub: SinonStubbedInstance<RestorationReservationMySuffixService>;
    let mountOptions: MountingOptions<RestorationReservationMySuffixComponentType>['global'];

    beforeEach(() => {
      restorationReservationServiceStub = sinon.createStubInstance<RestorationReservationMySuffixService>(
        RestorationReservationMySuffixService,
      );
      restorationReservationServiceStub.retrieve.resolves({ headers: {} });

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
          restorationReservationService: () => restorationReservationServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        restorationReservationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(RestorationReservationMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(restorationReservationServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.restorationReservations[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(RestorationReservationMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(restorationReservationServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: RestorationReservationMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(RestorationReservationMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        restorationReservationServiceStub.retrieve.reset();
        restorationReservationServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        restorationReservationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(restorationReservationServiceStub.retrieve.called).toBeTruthy();
        expect(comp.restorationReservations[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        restorationReservationServiceStub.retrieve.reset();
        restorationReservationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(restorationReservationServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.restorationReservations[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(restorationReservationServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        restorationReservationServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeRestorationReservationMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(restorationReservationServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(restorationReservationServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
