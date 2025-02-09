import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PhoneMySuffixUpdate from './phone-my-suffix-update.vue';
import PhoneMySuffixService from './phone-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

import PayloadMySuffixService from '@/entities/payload-my-suffix/payload-my-suffix.service';

type PhoneMySuffixUpdateComponentType = InstanceType<typeof PhoneMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const phoneSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PhoneMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('PhoneMySuffix Management Update Component', () => {
    let comp: PhoneMySuffixUpdateComponentType;
    let phoneServiceStub: SinonStubbedInstance<PhoneMySuffixService>;

    beforeEach(() => {
      route = {};
      phoneServiceStub = sinon.createStubInstance<PhoneMySuffixService>(PhoneMySuffixService);
      phoneServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          phoneService: () => phoneServiceStub,
          payloadService: () =>
            sinon.createStubInstance<PayloadMySuffixService>(PayloadMySuffixService, {
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
        const wrapper = shallowMount(PhoneMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.phone = phoneSample;
        phoneServiceStub.update.resolves(phoneSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(phoneServiceStub.update.calledWith(phoneSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        phoneServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PhoneMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.phone = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(phoneServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        phoneServiceStub.find.resolves(phoneSample);
        phoneServiceStub.retrieve.resolves([phoneSample]);

        // WHEN
        route = {
          params: {
            phoneId: `${phoneSample.id}`,
          },
        };
        const wrapper = shallowMount(PhoneMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.phone).toMatchObject(phoneSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        phoneServiceStub.find.resolves(phoneSample);
        const wrapper = shallowMount(PhoneMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
