import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import OneCustomerMySuffixUpdate from './one-customer-my-suffix-update.vue';
import OneCustomerMySuffixService from './one-customer-my-suffix.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import GoldenRecordMySuffixService from '@/entities/golden-record-my-suffix/golden-record-my-suffix.service';

type OneCustomerMySuffixUpdateComponentType = InstanceType<typeof OneCustomerMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const oneCustomerSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OneCustomerMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('OneCustomerMySuffix Management Update Component', () => {
    let comp: OneCustomerMySuffixUpdateComponentType;
    let oneCustomerServiceStub: SinonStubbedInstance<OneCustomerMySuffixService>;

    beforeEach(() => {
      route = {};
      oneCustomerServiceStub = sinon.createStubInstance<OneCustomerMySuffixService>(OneCustomerMySuffixService);
      oneCustomerServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          oneCustomerService: () => oneCustomerServiceStub,
          goldenRecordService: () =>
            sinon.createStubInstance<GoldenRecordMySuffixService>(GoldenRecordMySuffixService, {
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
        const wrapper = shallowMount(OneCustomerMySuffixUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(OneCustomerMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.oneCustomer = oneCustomerSample;
        oneCustomerServiceStub.update.resolves(oneCustomerSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(oneCustomerServiceStub.update.calledWith(oneCustomerSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        oneCustomerServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OneCustomerMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.oneCustomer = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(oneCustomerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        oneCustomerServiceStub.find.resolves(oneCustomerSample);
        oneCustomerServiceStub.retrieve.resolves([oneCustomerSample]);

        // WHEN
        route = {
          params: {
            oneCustomerId: `${oneCustomerSample.id}`,
          },
        };
        const wrapper = shallowMount(OneCustomerMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.oneCustomer).toMatchObject(oneCustomerSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        oneCustomerServiceStub.find.resolves(oneCustomerSample);
        const wrapper = shallowMount(OneCustomerMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
