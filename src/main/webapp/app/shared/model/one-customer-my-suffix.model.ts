import { type IGoldenRecordMySuffix } from '@/shared/model/golden-record-my-suffix.model';

export interface IOneCustomerMySuffix {
  id?: string;
  domaine?: string | null;
  aggregateId?: string | null;
  aggregateType?: string | null;
  timestamp?: Date | null;
  goldenRecord?: IGoldenRecordMySuffix | null;
}

export class OneCustomerMySuffix implements IOneCustomerMySuffix {
  constructor(
    public id?: string,
    public domaine?: string | null,
    public aggregateId?: string | null,
    public aggregateType?: string | null,
    public timestamp?: Date | null,
    public goldenRecord?: IGoldenRecordMySuffix | null,
  ) {}
}
