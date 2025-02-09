import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EmailMySuffixUpdate from './email-my-suffix-update.vue';
import EmailMySuffixService from './email-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

import PayloadMySuffixService from '@/entities/payload-my-suffix/payload-my-suffix.service';

type EmailMySuffixUpdateComponentType = InstanceType<typeof EmailMySuffixUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const emailSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EmailMySuffixUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('EmailMySuffix Management Update Component', () => {
    let comp: EmailMySuffixUpdateComponentType;
    let emailServiceStub: SinonStubbedInstance<EmailMySuffixService>;

    beforeEach(() => {
      route = {};
      emailServiceStub = sinon.createStubInstance<EmailMySuffixService>(EmailMySuffixService);
      emailServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          emailService: () => emailServiceStub,
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
        const wrapper = shallowMount(EmailMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.email = emailSample;
        emailServiceStub.update.resolves(emailSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(emailServiceStub.update.calledWith(emailSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        emailServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EmailMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.email = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(emailServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        emailServiceStub.find.resolves(emailSample);
        emailServiceStub.retrieve.resolves([emailSample]);

        // WHEN
        route = {
          params: {
            emailId: `${emailSample.id}`,
          },
        };
        const wrapper = shallowMount(EmailMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.email).toMatchObject(emailSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        emailServiceStub.find.resolves(emailSample);
        const wrapper = shallowMount(EmailMySuffixUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
