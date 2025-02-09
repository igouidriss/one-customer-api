import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MetadataMySuffixDetails from './metadata-my-suffix-details.vue';
import MetadataMySuffixService from './metadata-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type MetadataMySuffixDetailsComponentType = InstanceType<typeof MetadataMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const metadataSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MetadataMySuffix Management Detail Component', () => {
    let metadataServiceStub: SinonStubbedInstance<MetadataMySuffixService>;
    let mountOptions: MountingOptions<MetadataMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      metadataServiceStub = sinon.createStubInstance<MetadataMySuffixService>(MetadataMySuffixService);

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
          metadataService: () => metadataServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        metadataServiceStub.find.resolves(metadataSample);
        route = {
          params: {
            metadataId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MetadataMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.metadata).toMatchObject(metadataSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        metadataServiceStub.find.resolves(metadataSample);
        const wrapper = shallowMount(MetadataMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
