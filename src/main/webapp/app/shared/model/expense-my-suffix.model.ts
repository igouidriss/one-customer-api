import { type IMetadataMySuffix } from '@/shared/model/metadata-my-suffix.model';
import { type IExpensesMySuffix } from '@/shared/model/expenses-my-suffix.model';

export interface IExpenseMySuffix {
  id?: string;
  expenseType?: string | null;
  amount?: number | null;
  depositAmount?: number | null;
  totalAmount?: number | null;
  shift?: string | null;
  date?: Date | null;
  arrivalDate?: Date | null;
  leaveDate?: Date | null;
  guestCount?: number | null;
  hotelName?: string | null;
  hotelId?: string | null;
  restaurantName?: string | null;
  restaurantId?: string | null;
  clientId?: string | null;
  metadata?: IMetadataMySuffix | null;
  expenses?: IExpensesMySuffix | null;
}

export class ExpenseMySuffix implements IExpenseMySuffix {
  constructor(
    public id?: string,
    public expenseType?: string | null,
    public amount?: number | null,
    public depositAmount?: number | null,
    public totalAmount?: number | null,
    public shift?: string | null,
    public date?: Date | null,
    public arrivalDate?: Date | null,
    public leaveDate?: Date | null,
    public guestCount?: number | null,
    public hotelName?: string | null,
    public hotelId?: string | null,
    public restaurantName?: string | null,
    public restaurantId?: string | null,
    public clientId?: string | null,
    public metadata?: IMetadataMySuffix | null,
    public expenses?: IExpensesMySuffix | null,
  ) {}
}
