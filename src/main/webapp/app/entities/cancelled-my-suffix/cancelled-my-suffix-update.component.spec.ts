import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import CancelledMySuffixUpdate from './cancelled-my-suffix-update.vue';
import CancelledMySuffixService from './cancelled-my-suffix.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

type CancelledMySuffixUpdateComponentType = InstanceType<typeof CancelledMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cancelledSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CancelledMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CancelledMySuffix Management Update Component', () => {
    let comp: CancelledMySuffixUpdateComponentType;
    let cancelledServiceStub: SinonStubbedInstance<CancelledMySuffixService>;

    beforeEach(() => {
      route = {};
      cancelledServiceStub = sinon.createStubInstance<CancelledMySuffixService>(CancelledMySuffixService);
      cancelledServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          cancelledService: () => cancelledServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(CancelledMySuffixUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(CancelledMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cancelled = cancelledSample;
        cancelledServiceStub.update.resolves(cancelledSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cancelledServiceStub.update.calledWith(cancelledSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        cancelledServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CancelledMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.cancelled = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cancelledServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        cancelledServiceStub.find.resolves(cancelledSample);
        cancelledServiceStub.retrieve.resolves([cancelledSample]);

        // WHEN
        route = {
          params: {
            cancelledId: `${cancelledSample.id}`,
          },
        };
        const wrapper = shallowMount(CancelledMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.cancelled).toMatchObject(cancelledSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cancelledServiceStub.find.resolves(cancelledSample);
        const wrapper = shallowMount(CancelledMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
