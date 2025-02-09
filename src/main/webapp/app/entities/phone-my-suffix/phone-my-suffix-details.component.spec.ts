import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PhoneMySuffixDetails from './phone-my-suffix-details.vue';
import PhoneMySuffixService from './phone-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type PhoneMySuffixDetailsComponentType = InstanceType<typeof PhoneMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const phoneSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('PhoneMySuffix Management Detail Component', () => {
    let phoneServiceStub: SinonStubbedInstance<PhoneMySuffixService>;
    let mountOptions: MountingOptions<PhoneMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      phoneServiceStub = sinon.createStubInstance<PhoneMySuffixService>(PhoneMySuffixService);

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
          phoneService: () => phoneServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        phoneServiceStub.find.resolves(phoneSample);
        route = {
          params: {
            phoneId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(PhoneMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.phone).toMatchObject(phoneSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        phoneServiceStub.find.resolves(phoneSample);
        const wrapper = shallowMount(PhoneMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
