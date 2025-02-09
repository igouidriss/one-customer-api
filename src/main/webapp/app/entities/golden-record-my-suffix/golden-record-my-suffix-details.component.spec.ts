import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import GoldenRecordMySuffixDetails from './golden-record-my-suffix-details.vue';
import GoldenRecordMySuffixService from './golden-record-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type GoldenRecordMySuffixDetailsComponentType = InstanceType<typeof GoldenRecordMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const goldenRecordSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('GoldenRecordMySuffix Management Detail Component', () => {
    let goldenRecordServiceStub: SinonStubbedInstance<GoldenRecordMySuffixService>;
    let mountOptions: MountingOptions<GoldenRecordMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      goldenRecordServiceStub = sinon.createStubInstance<GoldenRecordMySuffixService>(GoldenRecordMySuffixService);

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
          goldenRecordService: () => goldenRecordServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        goldenRecordServiceStub.find.resolves(goldenRecordSample);
        route = {
          params: {
            goldenRecordId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(GoldenRecordMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.goldenRecord).toMatchObject(goldenRecordSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        goldenRecordServiceStub.find.resolves(goldenRecordSample);
        const wrapper = shallowMount(GoldenRecordMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
