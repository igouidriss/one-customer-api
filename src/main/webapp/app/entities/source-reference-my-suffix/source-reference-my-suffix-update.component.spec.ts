import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import SourceReferenceMySuffixUpdate from './source-reference-my-suffix-update.vue';
import SourceReferenceMySuffixService from './source-reference-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

import GoldenRecordMySuffixService from '@/entities/golden-record-my-suffix/golden-record-my-suffix.service';

type SourceReferenceMySuffixUpdateComponentType = InstanceType<typeof SourceReferenceMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const sourceReferenceSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<SourceReferenceMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('SourceReferenceMySuffix Management Update Component', () => {
    let comp: SourceReferenceMySuffixUpdateComponentType;
    let sourceReferenceServiceStub: SinonStubbedInstance<SourceReferenceMySuffixService>;

    beforeEach(() => {
      route = {};
      sourceReferenceServiceStub = sinon.createStubInstance<SourceReferenceMySuffixService>(SourceReferenceMySuffixService);
      sourceReferenceServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          sourceReferenceService: () => sourceReferenceServiceStub,
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

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(SourceReferenceMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.sourceReference = sourceReferenceSample;
        sourceReferenceServiceStub.update.resolves(sourceReferenceSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sourceReferenceServiceStub.update.calledWith(sourceReferenceSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        sourceReferenceServiceStub.create.resolves(entity);
        const wrapper = shallowMount(SourceReferenceMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.sourceReference = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sourceReferenceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        sourceReferenceServiceStub.find.resolves(sourceReferenceSample);
        sourceReferenceServiceStub.retrieve.resolves([sourceReferenceSample]);

        // WHEN
        route = {
          params: {
            sourceReferenceId: `${sourceReferenceSample.id}`,
          },
        };
        const wrapper = shallowMount(SourceReferenceMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.sourceReference).toMatchObject(sourceReferenceSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        sourceReferenceServiceStub.find.resolves(sourceReferenceSample);
        const wrapper = shallowMount(SourceReferenceMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
