import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CancelledMySuffixDetails from './cancelled-my-suffix-details.vue';
import CancelledMySuffixService from './cancelled-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type CancelledMySuffixDetailsComponentType = InstanceType<typeof CancelledMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const cancelledSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('CancelledMySuffix Management Detail Component', () => {
    let cancelledServiceStub: SinonStubbedInstance<CancelledMySuffixService>;
    let mountOptions: MountingOptions<CancelledMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      cancelledServiceStub = sinon.createStubInstance<CancelledMySuffixService>(CancelledMySuffixService);

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
          cancelledService: () => cancelledServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        cancelledServiceStub.find.resolves(cancelledSample);
        route = {
          params: {
            cancelledId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(CancelledMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.cancelled).toMatchObject(cancelledSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        cancelledServiceStub.find.resolves(cancelledSample);
        const wrapper = shallowMount(CancelledMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
