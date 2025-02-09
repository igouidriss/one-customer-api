import { type ICancelledMySuffix } from '@/shared/model/cancelled-my-suffix.model';
import { type IExpensesMySuffix } from '@/shared/model/expenses-my-suffix.model';
import { type IMetadataMySuffix } from '@/shared/model/metadata-my-suffix.model';
import { type IOneCustomerMySuffix } from '@/shared/model/one-customer-my-suffix.model';

export interface IRestorationReservationMySuffix {
  id?: string;
  aggregateId?: string | null;
  aggregateType?: string | null;
  clientId?: string | null;
  domaine?: string | null;
  source?: string | null;
  reservationTimestamp?: Date | null;
  projection?: string | null;
  date?: Date | null;
  depositAmount?: number | null;
  totalAmount?: number | null;
  shift?: string | null;
  guestCount?: number | null;
  arrivalDate?: Date | null;
  restaurantName?: string | null;
  restaurantId?: string | null;
  cancelled?: ICancelledMySuffix | null;
  expenses?: IExpensesMySuffix | null;
  metadata?: IMetadataMySuffix | null;
  oneCustomer?: IOneCustomerMySuffix | null;
}

export class RestorationReservationMySuffix implements IRestorationReservationMySuffix {
  constructor(
    public id?: string,
    public aggregateId?: string | null,
    public aggregateType?: string | null,
    public clientId?: string | null,
    public domaine?: string | null,
    public source?: string | null,
    public reservationTimestamp?: Date | null,
    public projection?: string | null,
    public date?: Date | null,
    public depositAmount?: number | null,
    public totalAmount?: number | null,
    public shift?: string | null,
    public guestCount?: number | null,
    public arrivalDate?: Date | null,
    public restaurantName?: string | null,
    public restaurantId?: string | null,
    public cancelled?: ICancelledMySuffix | null,
    public expenses?: IExpensesMySuffix | null,
    public metadata?: IMetadataMySuffix | null,
    public oneCustomer?: IOneCustomerMySuffix | null,
  ) {}
}
