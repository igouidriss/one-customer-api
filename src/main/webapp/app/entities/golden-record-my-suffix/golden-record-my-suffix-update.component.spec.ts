import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import GoldenRecordMySuffixUpdate from './golden-record-my-suffix-update.vue';
import GoldenRecordMySuffixService from './golden-record-my-suffix.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import CancelledMySuffixService from '@/entities/cancelled-my-suffix/cancelled-my-suffix.service';
import PayloadMySuffixService from '@/entities/payload-my-suffix/payload-my-suffix.service';

type GoldenRecordMySuffixUpdateComponentType = InstanceType<typeof GoldenRecordMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const goldenRecordSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<GoldenRecordMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('GoldenRecordMySuffix Management Update Component', () => {
    let comp: GoldenRecordMySuffixUpdateComponentType;
    let goldenRecordServiceStub: SinonStubbedInstance<GoldenRecordMySuffixService>;

    beforeEach(() => {
      route = {};
      goldenRecordServiceStub = sinon.createStubInstance<GoldenRecordMySuffixService>(GoldenRecordMySuffixService);
      goldenRecordServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          goldenRecordService: () => goldenRecordServiceStub,
          cancelledService: () =>
            sinon.createStubInstance<CancelledMySuffixService>(CancelledMySuffixService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          payloadService: () =>
            sinon.createStubInstance<PayloadMySuffixService>(PayloadMySuffixService, {
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
        const wrapper = shallowMount(GoldenRecordMySuffixUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(GoldenRecordMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.goldenRecord = goldenRecordSample;
        goldenRecordServiceStub.update.resolves(goldenRecordSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(goldenRecordServiceStub.update.calledWith(goldenRecordSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        goldenRecordServiceStub.create.resolves(entity);
        const wrapper = shallowMount(GoldenRecordMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.goldenRecord = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(goldenRecordServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        goldenRecordServiceStub.find.resolves(goldenRecordSample);
        goldenRecordServiceStub.retrieve.resolves([goldenRecordSample]);

        // WHEN
        route = {
          params: {
            goldenRecordId: `${goldenRecordSample.id}`,
          },
        };
        const wrapper = shallowMount(GoldenRecordMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.goldenRecord).toMatchObject(goldenRecordSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        goldenRecordServiceStub.find.resolves(goldenRecordSample);
        const wrapper = shallowMount(GoldenRecordMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
