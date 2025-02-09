import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import SourceReferenceMySuffixDetails from './source-reference-my-suffix-details.vue';
import SourceReferenceMySuffixService from './source-reference-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type SourceReferenceMySuffixDetailsComponentType = InstanceType<typeof SourceReferenceMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const sourceReferenceSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('SourceReferenceMySuffix Management Detail Component', () => {
    let sourceReferenceServiceStub: SinonStubbedInstance<SourceReferenceMySuffixService>;
    let mountOptions: MountingOptions<SourceReferenceMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      sourceReferenceServiceStub = sinon.createStubInstance<SourceReferenceMySuffixService>(SourceReferenceMySuffixService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          sourceReferenceService: () => sourceReferenceServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        sourceReferenceServiceStub.find.resolves(sourceReferenceSample);
        route = {
          params: {
            sourceReferenceId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(SourceReferenceMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.sourceReference).toMatchObject(sourceReferenceSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        sourceReferenceServiceStub.find.resolves(sourceReferenceSample);
        const wrapper = shallowMount(SourceReferenceMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
