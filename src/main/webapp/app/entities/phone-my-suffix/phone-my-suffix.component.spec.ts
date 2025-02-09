import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import PhoneMySuffix from './phone-my-suffix.vue';
import PhoneMySuffixService from './phone-my-suffix.service';
import AlertService from '@/shared/alert/alert.service';

type PhoneMySuffixComponentType = InstanceType<typeof PhoneMySuffix>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('PhoneMySuffix Management Component', () => {
    let phoneServiceStub: SinonStubbedInstance<PhoneMySuffixService>;
    let mountOptions: MountingOptions<PhoneMySuffixComponentType>['global'];

    beforeEach(() => {
      phoneServiceStub = sinon.createStubInstance<PhoneMySuffixService>(PhoneMySuffixService);
      phoneServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          phoneService: () => phoneServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        phoneServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(PhoneMySuffix, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(phoneServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.phones[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: PhoneMySuffixComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(PhoneMySuffix, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        phoneServiceStub.retrieve.reset();
        phoneServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        phoneServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removePhoneMySuffix();
        await comp.$nextTick(); // clear components

        // THEN
        expect(phoneServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(phoneServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
