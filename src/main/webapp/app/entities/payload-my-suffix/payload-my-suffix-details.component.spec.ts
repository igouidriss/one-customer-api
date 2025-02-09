import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PayloadMySuffixDetails from './payload-my-suffix-details.vue';
import PayloadMySuffixService from './payload-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type PayloadMySuffixDetailsComponentType = InstanceType<typeof PayloadMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const payloadSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('PayloadMySuffix Management Detail Component', () => {
    let payloadServiceStub: SinonStubbedInstance<PayloadMySuffixService>;
    let mountOptions: MountingOptions<PayloadMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      payloadServiceStub = sinon.createStubInstance<PayloadMySuffixService>(PayloadMySuffixService);

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
          payloadService: () => payloadServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        payloadServiceStub.find.resolves(payloadSample);
        route = {
          params: {
            payloadId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(PayloadMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.payload).toMatchObject(payloadSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        payloadServiceStub.find.resolves(payloadSample);
        const wrapper = shallowMount(PayloadMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
