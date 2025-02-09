import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RestorationReservationMySuffixDetails from './restoration-reservation-my-suffix-details.vue';
import RestorationReservationMySuffixService from './restoration-reservation-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type RestorationReservationMySuffixDetailsComponentType = InstanceType<typeof RestorationReservationMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const restorationReservationSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RestorationReservationMySuffix Management Detail Component', () => {
    let restorationReservationServiceStub: SinonStubbedInstance<RestorationReservationMySuffixService>;
    let mountOptions: MountingOptions<RestorationReservationMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      restorationReservationServiceStub = sinon.createStubInstance<RestorationReservationMySuffixService>(
        RestorationReservationMySuffixService,
      );

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
          restorationReservationService: () => restorationReservationServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        restorationReservationServiceStub.find.resolves(restorationReservationSample);
        route = {
          params: {
            restorationReservationId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(RestorationReservationMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.restorationReservation).toMatchObject(restorationReservationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        restorationReservationServiceStub.find.resolves(restorationReservationSample);
        const wrapper = shallowMount(RestorationReservationMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
