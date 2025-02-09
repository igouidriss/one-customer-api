import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PayloadMySuffixUpdate from './payload-my-suffix-update.vue';
import PayloadMySuffixService from './payload-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

import MetadataMySuffixService from '@/entities/metadata-my-suffix/metadata-my-suffix.service';

type PayloadMySuffixUpdateComponentType = InstanceType<typeof PayloadMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const payloadSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PayloadMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('PayloadMySuffix Management Update Component', () => {
    let comp: PayloadMySuffixUpdateComponentType;
    let payloadServiceStub: SinonStubbedInstance<PayloadMySuffixService>;

    beforeEach(() => {
      route = {};
      payloadServiceStub = sinon.createStubInstance<PayloadMySuffixService>(PayloadMySuffixService);
      payloadServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          payloadService: () => payloadServiceStub,
          metadataService: () =>
            sinon.createStubInstance<MetadataMySuffixService>(MetadataMySuffixService, {
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
        const wrapper = shallowMount(PayloadMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.payload = payloadSample;
        payloadServiceStub.update.resolves(payloadSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(payloadServiceStub.update.calledWith(payloadSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        payloadServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PayloadMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.payload = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(payloadServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        payloadServiceStub.find.resolves(payloadSample);
        payloadServiceStub.retrieve.resolves([payloadSample]);

        // WHEN
        route = {
          params: {
            payloadId: `${payloadSample.id}`,
          },
        };
        const wrapper = shallowMount(PayloadMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.payload).toMatchObject(payloadSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        payloadServiceStub.find.resolves(payloadSample);
        const wrapper = shallowMount(PayloadMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
