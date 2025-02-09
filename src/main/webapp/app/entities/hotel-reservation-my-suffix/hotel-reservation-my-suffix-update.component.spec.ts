import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import HotelReservationMySuffixUpdate from './hotel-reservation-my-suffix-update.vue';
import HotelReservationMySuffixService from './hotel-reservation-my-suffix.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import CancelledMySuffixService from '@/entities/cancelled-my-suffix/cancelled-my-suffix.service';
import ExpensesMySuffixService from '@/entities/expenses-my-suffix/expenses-my-suffix.service';
import MetadataMySuffixService from '@/entities/metadata-my-suffix/metadata-my-suffix.service';
import OneCustomerMySuffixService from '@/entities/one-customer-my-suffix/one-customer-my-suffix.service';

type HotelReservationMySuffixUpdateComponentType = InstanceType<typeof HotelReservationMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const hotelReservationSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<HotelReservationMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('HotelReservationMySuffix Management Update Component', () => {
    let comp: HotelReservationMySuffixUpdateComponentType;
    let hotelReservationServiceStub: SinonStubbedInstance<HotelReservationMySuffixService>;

    beforeEach(() => {
      route = {};
      hotelReservationServiceStub = sinon.createStubInstance<HotelReservationMySuffixService>(HotelReservationMySuffixService);
      hotelReservationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          hotelReservationService: () => hotelReservationServiceStub,
          cancelledService: () =>
            sinon.createStubInstance<CancelledMySuffixService>(CancelledMySuffixService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          expensesService: () =>
            sinon.createStubInstance<ExpensesMySuffixService>(ExpensesMySuffixService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          metadataService: () =>
            sinon.createStubInstance<MetadataMySuffixService>(MetadataMySuffixService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          oneCustomerService: () =>
            sinon.createStubInstance<OneCustomerMySuffixService>(OneCustomerMySuffixService, {
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
        const wrapper = shallowMount(HotelReservationMySuffixUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(HotelReservationMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.hotelReservation = hotelReservationSample;
        hotelReservationServiceStub.update.resolves(hotelReservationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hotelReservationServiceStub.update.calledWith(hotelReservationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        hotelReservationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(HotelReservationMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.hotelReservation = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hotelReservationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        hotelReservationServiceStub.find.resolves(hotelReservationSample);
        hotelReservationServiceStub.retrieve.resolves([hotelReservationSample]);

        // WHEN
        route = {
          params: {
            hotelReservationId: `${hotelReservationSample.id}`,
          },
        };
        const wrapper = shallowMount(HotelReservationMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.hotelReservation).toMatchObject(hotelReservationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        hotelReservationServiceStub.find.resolves(hotelReservationSample);
        const wrapper = shallowMount(HotelReservationMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
