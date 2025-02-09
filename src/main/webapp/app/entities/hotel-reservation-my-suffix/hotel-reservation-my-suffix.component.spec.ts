import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import HotelReservationMySuffix from './hotel-reservation-my-suffix.vue';
import HotelReservationMySuffixService from './hotel-reservation-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type HotelReservationMySuffixComponentType = InstanceType<typeof HotelReservationMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('HotelReservationMySuffix Management Component', () => {
    let hotelReservationServiceStub: SinonStubbedInstance<HotelReservationMySuffixService>;
    let mountOptions: MountingOptions<HotelReservationMySuffixComponentType>['global'];

    beforeEach(() => {
      hotelReservationServiceStub = sinon.createStubInstance<HotelReservationMySuffixService>(HotelReservationMySuffixService);
      hotelReservationServiceStub.retrieve.resolves({ headers: {} });

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
          hotelReservationService: () => hotelReservationServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        hotelReservationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(HotelReservationMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(hotelReservationServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.hotelReservations[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(HotelReservationMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(hotelReservationServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: HotelReservationMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(HotelReservationMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        hotelReservationServiceStub.retrieve.reset();
        hotelReservationServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        hotelReservationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(hotelReservationServiceStub.retrieve.called).toBeTruthy();
        expect(comp.hotelReservations[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        hotelReservationServiceStub.retrieve.reset();
        hotelReservationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(hotelReservationServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.hotelReservations[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(hotelReservationServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        hotelReservationServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeHotelReservationMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(hotelReservationServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(hotelReservationServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
