import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import MetadataMySuffixUpdate from './metadata-my-suffix-update.vue';
import MetadataMySuffixService from './metadata-my-suffix.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

type MetadataMySuffixUpdateComponentType = InstanceType<typeof MetadataMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const metadataSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MetadataMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('MetadataMySuffix Management Update Component', () => {
    let comp: MetadataMySuffixUpdateComponentType;
    let metadataServiceStub: SinonStubbedInstance<MetadataMySuffixService>;

    beforeEach(() => {
      route = {};
      metadataServiceStub = sinon.createStubInstance<MetadataMySuffixService>(MetadataMySuffixService);
      metadataServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          metadataService: () => metadataServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(MetadataMySuffixUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(MetadataMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.metadata = metadataSample;
        metadataServiceStub.update.resolves(metadataSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(metadataServiceStub.update.calledWith(metadataSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        metadataServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MetadataMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.metadata = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(metadataServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        metadataServiceStub.find.resolves(metadataSample);
        metadataServiceStub.retrieve.resolves([metadataSample]);

        // WHEN
        route = {
          params: {
            metadataId: `${metadataSample.id}`,
          },
        };
        const wrapper = shallowMount(MetadataMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.metadata).toMatchObject(metadataSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        metadataServiceStub.find.resolves(metadataSample);
        const wrapper = shallowMount(MetadataMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
