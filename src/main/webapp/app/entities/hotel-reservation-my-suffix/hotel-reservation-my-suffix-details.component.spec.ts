import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import HotelReservationMySuffixDetails from './hotel-reservation-my-suffix-details.vue';
import HotelReservationMySuffixService from './hotel-reservation-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type HotelReservationMySuffixDetailsComponentType = InstanceType<typeof HotelReservationMySuffixDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const hotelReservationSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('HotelReservationMySuffix Management Detail Component', () => {
    let hotelReservationServiceStub: SinonStubbedInstance<HotelReservationMySuffixService>;
    let mountOptions: MountingOptions<HotelReservationMySuffixDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      hotelReservationServiceStub = sinon.createStubInstance<HotelReservationMySuffixService>(HotelReservationMySuffixService);

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
          hotelReservationService: () => hotelReservationServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        hotelReservationServiceStub.find.resolves(hotelReservationSample);
        route = {
          params: {
            hotelReservationId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(HotelReservationMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.hotelReservation).toMatchObject(hotelReservationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        hotelReservationServiceStub.find.resolves(hotelReservationSample);
        const wrapper = shallowMount(HotelReservationMySuffixDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
