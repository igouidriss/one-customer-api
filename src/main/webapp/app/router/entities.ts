import { Authority } from '@/shared/security/authority';
const Entities = () => import('@/entities/entities.vue');

const OneCustomerMySuffix = () => import('@/entities/one-customer-my-suffix/one-customer-my-suffix.vue');
const OneCustomerMySuffixUpdate = () => import('@/entities/one-customer-my-suffix/one-customer-my-suffix-update.vue');
const OneCustomerMySuffixDetails = () => import('@/entities/one-customer-my-suffix/one-customer-my-suffix-details.vue');

const GoldenRecordMySuffix = () => import('@/entities/golden-record-my-suffix/golden-record-my-suffix.vue');
const GoldenRecordMySuffixUpdate = () => import('@/entities/golden-record-my-suffix/golden-record-my-suffix-update.vue');
const GoldenRecordMySuffixDetails = () => import('@/entities/golden-record-my-suffix/golden-record-my-suffix-details.vue');

const PayloadMySuffix = () => import('@/entities/payload-my-suffix/payload-my-suffix.vue');
const PayloadMySuffixUpdate = () => import('@/entities/payload-my-suffix/payload-my-suffix-update.vue');
const PayloadMySuffixDetails = () => import('@/entities/payload-my-suffix/payload-my-suffix-details.vue');

const EmailMySuffix = () => import('@/entities/email-my-suffix/email-my-suffix.vue');
const EmailMySuffixUpdate = () => import('@/entities/email-my-suffix/email-my-suffix-update.vue');
const EmailMySuffixDetails = () => import('@/entities/email-my-suffix/email-my-suffix-details.vue');

const PhoneMySuffix = () => import('@/entities/phone-my-suffix/phone-my-suffix.vue');
const PhoneMySuffixUpdate = () => import('@/entities/phone-my-suffix/phone-my-suffix-update.vue');
const PhoneMySuffixDetails = () => import('@/entities/phone-my-suffix/phone-my-suffix-details.vue');

const AddressMySuffix = () => import('@/entities/address-my-suffix/address-my-suffix.vue');
const AddressMySuffixUpdate = () => import('@/entities/address-my-suffix/address-my-suffix-update.vue');
const AddressMySuffixDetails = () => import('@/entities/address-my-suffix/address-my-suffix-details.vue');

const CancelledMySuffix = () => import('@/entities/cancelled-my-suffix/cancelled-my-suffix.vue');
const CancelledMySuffixUpdate = () => import('@/entities/cancelled-my-suffix/cancelled-my-suffix-update.vue');
const CancelledMySuffixDetails = () => import('@/entities/cancelled-my-suffix/cancelled-my-suffix-details.vue');

const SourceReferenceMySuffix = () => import('@/entities/source-reference-my-suffix/source-reference-my-suffix.vue');
const SourceReferenceMySuffixUpdate = () => import('@/entities/source-reference-my-suffix/source-reference-my-suffix-update.vue');
const SourceReferenceMySuffixDetails = () => import('@/entities/source-reference-my-suffix/source-reference-my-suffix-details.vue');

const HotelReservationMySuffix = () => import('@/entities/hotel-reservation-my-suffix/hotel-reservation-my-suffix.vue');
const HotelReservationMySuffixUpdate = () => import('@/entities/hotel-reservation-my-suffix/hotel-reservation-my-suffix-update.vue');
const HotelReservationMySuffixDetails = () => import('@/entities/hotel-reservation-my-suffix/hotel-reservation-my-suffix-details.vue');

const RestorationReservationMySuffix = () => import('@/entities/restoration-reservation-my-suffix/restoration-reservation-my-suffix.vue');
const RestorationReservationMySuffixUpdate = () =>
  import('@/entities/restoration-reservation-my-suffix/restoration-reservation-my-suffix-update.vue');
const RestorationReservationMySuffixDetails = () =>
  import('@/entities/restoration-reservation-my-suffix/restoration-reservation-my-suffix-details.vue');

const ExpensesMySuffix = () => import('@/entities/expenses-my-suffix/expenses-my-suffix.vue');
const ExpensesMySuffixUpdate = () => import('@/entities/expenses-my-suffix/expenses-my-suffix-update.vue');
const ExpensesMySuffixDetails = () => import('@/entities/expenses-my-suffix/expenses-my-suffix-details.vue');

const ExpenseMySuffix = () => import('@/entities/expense-my-suffix/expense-my-suffix.vue');
const ExpenseMySuffixUpdate = () => import('@/entities/expense-my-suffix/expense-my-suffix-update.vue');
const ExpenseMySuffixDetails = () => import('@/entities/expense-my-suffix/expense-my-suffix-details.vue');

const MetadataMySuffix = () => import('@/entities/metadata-my-suffix/metadata-my-suffix.vue');
const MetadataMySuffixUpdate = () => import('@/entities/metadata-my-suffix/metadata-my-suffix-update.vue');
const MetadataMySuffixDetails = () => import('@/entities/metadata-my-suffix/metadata-my-suffix-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'one-customer-my-suffix',
      name: 'OneCustomerMySuffix',
      component: OneCustomerMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'one-customer-my-suffix/new',
      name: 'OneCustomerMySuffixCreate',
      component: OneCustomerMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'one-customer-my-suffix/:oneCustomerId/edit',
      name: 'OneCustomerMySuffixEdit',
      component: OneCustomerMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'one-customer-my-suffix/:oneCustomerId/view',
      name: 'OneCustomerMySuffixView',
      component: OneCustomerMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'golden-record-my-suffix',
      name: 'GoldenRecordMySuffix',
      component: GoldenRecordMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'golden-record-my-suffix/new',
      name: 'GoldenRecordMySuffixCreate',
      component: GoldenRecordMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'golden-record-my-suffix/:goldenRecordId/edit',
      name: 'GoldenRecordMySuffixEdit',
      component: GoldenRecordMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'golden-record-my-suffix/:goldenRecordId/view',
      name: 'GoldenRecordMySuffixView',
      component: GoldenRecordMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payload-my-suffix',
      name: 'PayloadMySuffix',
      component: PayloadMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payload-my-suffix/new',
      name: 'PayloadMySuffixCreate',
      component: PayloadMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payload-my-suffix/:payloadId/edit',
      name: 'PayloadMySuffixEdit',
      component: PayloadMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payload-my-suffix/:payloadId/view',
      name: 'PayloadMySuffixView',
      component: PayloadMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'email-my-suffix',
      name: 'EmailMySuffix',
      component: EmailMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'email-my-suffix/new',
      name: 'EmailMySuffixCreate',
      component: EmailMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'email-my-suffix/:emailId/edit',
      name: 'EmailMySuffixEdit',
      component: EmailMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'email-my-suffix/:emailId/view',
      name: 'EmailMySuffixView',
      component: EmailMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phone-my-suffix',
      name: 'PhoneMySuffix',
      component: PhoneMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phone-my-suffix/new',
      name: 'PhoneMySuffixCreate',
      component: PhoneMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phone-my-suffix/:phoneId/edit',
      name: 'PhoneMySuffixEdit',
      component: PhoneMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phone-my-suffix/:phoneId/view',
      name: 'PhoneMySuffixView',
      component: PhoneMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'address-my-suffix',
      name: 'AddressMySuffix',
      component: AddressMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'address-my-suffix/new',
      name: 'AddressMySuffixCreate',
      component: AddressMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'address-my-suffix/:addressId/edit',
      name: 'AddressMySuffixEdit',
      component: AddressMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'address-my-suffix/:addressId/view',
      name: 'AddressMySuffixView',
      component: AddressMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cancelled-my-suffix',
      name: 'CancelledMySuffix',
      component: CancelledMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cancelled-my-suffix/new',
      name: 'CancelledMySuffixCreate',
      component: CancelledMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cancelled-my-suffix/:cancelledId/edit',
      name: 'CancelledMySuffixEdit',
      component: CancelledMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cancelled-my-suffix/:cancelledId/view',
      name: 'CancelledMySuffixView',
      component: CancelledMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'source-reference-my-suffix',
      name: 'SourceReferenceMySuffix',
      component: SourceReferenceMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'source-reference-my-suffix/new',
      name: 'SourceReferenceMySuffixCreate',
      component: SourceReferenceMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'source-reference-my-suffix/:sourceReferenceId/edit',
      name: 'SourceReferenceMySuffixEdit',
      component: SourceReferenceMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'source-reference-my-suffix/:sourceReferenceId/view',
      name: 'SourceReferenceMySuffixView',
      component: SourceReferenceMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'hotel-reservation-my-suffix',
      name: 'HotelReservationMySuffix',
      component: HotelReservationMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'hotel-reservation-my-suffix/new',
      name: 'HotelReservationMySuffixCreate',
      component: HotelReservationMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'hotel-reservation-my-suffix/:hotelReservationId/edit',
      name: 'HotelReservationMySuffixEdit',
      component: HotelReservationMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'hotel-reservation-my-suffix/:hotelReservationId/view',
      name: 'HotelReservationMySuffixView',
      component: HotelReservationMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'restoration-reservation-my-suffix',
      name: 'RestorationReservationMySuffix',
      component: RestorationReservationMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'restoration-reservation-my-suffix/new',
      name: 'RestorationReservationMySuffixCreate',
      component: RestorationReservationMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'restoration-reservation-my-suffix/:restorationReservationId/edit',
      name: 'RestorationReservationMySuffixEdit',
      component: RestorationReservationMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'restoration-reservation-my-suffix/:restorationReservationId/view',
      name: 'RestorationReservationMySuffixView',
      component: RestorationReservationMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expenses-my-suffix',
      name: 'ExpensesMySuffix',
      component: ExpensesMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expenses-my-suffix/new',
      name: 'ExpensesMySuffixCreate',
      component: ExpensesMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expenses-my-suffix/:expensesId/edit',
      name: 'ExpensesMySuffixEdit',
      component: ExpensesMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expenses-my-suffix/:expensesId/view',
      name: 'ExpensesMySuffixView',
      component: ExpensesMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense-my-suffix',
      name: 'ExpenseMySuffix',
      component: ExpenseMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense-my-suffix/new',
      name: 'ExpenseMySuffixCreate',
      component: ExpenseMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense-my-suffix/:expenseId/edit',
      name: 'ExpenseMySuffixEdit',
      component: ExpenseMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'expense-my-suffix/:expenseId/view',
      name: 'ExpenseMySuffixView',
      component: ExpenseMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'metadata-my-suffix',
      name: 'MetadataMySuffix',
      component: MetadataMySuffix,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'metadata-my-suffix/new',
      name: 'MetadataMySuffixCreate',
      component: MetadataMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'metadata-my-suffix/:metadataId/edit',
      name: 'MetadataMySuffixEdit',
      component: MetadataMySuffixUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'metadata-my-suffix/:metadataId/view',
      name: 'MetadataMySuffixView',
      component: MetadataMySuffixDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
