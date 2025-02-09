import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import RestorationReservationMySuffixUpdate from './restoration-reservation-my-suffix-update.vue';
import RestorationReservationMySuffixService from './restoration-reservation-my-suffix.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import CancelledMySuffixService from '@/entities/cancelled-my-suffix/cancelled-my-suffix.service';
import ExpensesMySuffixService from '@/entities/expenses-my-suffix/expenses-my-suffix.service';
import MetadataMySuffixService from '@/entities/metadata-my-suffix/metadata-my-suffix.service';
import OneCustomerMySuffixService from '@/entities/one-customer-my-suffix/one-customer-my-suffix.service';

type RestorationReservationMySuffixUpdateComponentType = InstanceType<typeof RestorationReservationMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const restorationReservationSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RestorationReservationMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RestorationReservationMySuffix Management Update Component', () => {
    let comp: RestorationReservationMySuffixUpdateComponentType;
    let restorationReservationServiceStub: SinonStubbedInstance<RestorationReservationMySuffixService>;

    beforeEach(() => {
      route = {};
      restorationReservationServiceStub = sinon.createStubInstance<RestorationReservationMySuffixService>(
        RestorationReservationMySuffixService,
      );
      restorationReservationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          restorationReservationService: () => restorationReservationServiceStub,
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
        const wrapper = shallowMount(RestorationReservationMySuffixUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(RestorationReservationMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.restorationReservation = restorationReservationSample;
        restorationReservationServiceStub.update.resolves(restorationReservationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(restorationReservationServiceStub.update.calledWith(restorationReservationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        restorationReservationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RestorationReservationMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.restorationReservation = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(restorationReservationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        restorationReservationServiceStub.find.resolves(restorationReservationSample);
        restorationReservationServiceStub.retrieve.resolves([restorationReservationSample]);

        // WHEN
        route = {
          params: {
            restorationReservationId: `${restorationReservationSample.id}`,
          },
        };
        const wrapper = shallowMount(RestorationReservationMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.restorationReservation).toMatchObject(restorationReservationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        restorationReservationServiceStub.find.resolves(restorationReservationSample);
        const wrapper = shallowMount(RestorationReservationMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
