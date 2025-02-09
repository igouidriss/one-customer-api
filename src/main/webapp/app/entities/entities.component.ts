import { defineComponent, provide } from 'vue';

import OneCustomerMySuffixService from './one-customer-my-suffix/one-customer-my-suffix.service';
import GoldenRecordMySuffixService from './golden-record-my-suffix/golden-record-my-suffix.service';
import PayloadMySuffixService from './payload-my-suffix/payload-my-suffix.service';
import EmailMySuffixService from './email-my-suffix/email-my-suffix.service';
import PhoneMySuffixService from './phone-my-suffix/phone-my-suffix.service';
import AddressMySuffixService from './address-my-suffix/address-my-suffix.service';
import CancelledMySuffixService from './cancelled-my-suffix/cancelled-my-suffix.service';
import SourceReferenceMySuffixService from './source-reference-my-suffix/source-reference-my-suffix.service';
import HotelReservationMySuffixService from './hotel-reservation-my-suffix/hotel-reservation-my-suffix.service';
import RestorationReservationMySuffixService from './restoration-reservation-my-suffix/restoration-reservation-my-suffix.service';
import ExpensesMySuffixService from './expenses-my-suffix/expenses-my-suffix.service';
import ExpenseMySuffixService from './expense-my-suffix/expense-my-suffix.service';
import MetadataMySuffixService from './metadata-my-suffix/metadata-my-suffix.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('oneCustomerService', () => new OneCustomerMySuffixService());
    provide('goldenRecordService', () => new GoldenRecordMySuffixService());
    provide('payloadService', () => new PayloadMySuffixService());
    provide('emailService', () => new EmailMySuffixService());
    provide('phoneService', () => new PhoneMySuffixService());
    provide('addressService', () => new AddressMySuffixService());
    provide('cancelledService', () => new CancelledMySuffixService());
    provide('sourceReferenceService', () => new SourceReferenceMySuffixService());
    provide('hotelReservationService', () => new HotelReservationMySuffixService());
    provide('restorationReservationService', () => new RestorationReservationMySuffixService());
    provide('expensesService', () => new ExpensesMySuffixService());
    provide('expenseService', () => new ExpenseMySuffixService());
    provide('metadataService', () => new MetadataMySuffixService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
