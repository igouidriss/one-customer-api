import { type ICancelledMySuffix } from '@/shared/model/cancelled-my-suffix.model';
import { type IPayloadMySuffix } from '@/shared/model/payload-my-suffix.model';

export interface IGoldenRecordMySuffix {
  id?: string;
  aggregateId?: string | null;
  aggregateType?: string | null;
  domaine?: string | null;
  mdmId?: string | null;
  source?: string | null;
  recordTimestamp?: Date | null;
  cancelled?: ICancelledMySuffix | null;
  payload?: IPayloadMySuffix | null;
}

export class GoldenRecordMySuffix implements IGoldenRecordMySuffix {
  constructor(
    public id?: string,
    public aggregateId?: string | null,
    public aggregateType?: string | null,
    public domaine?: string | null,
    public mdmId?: string | null,
    public source?: string | null,
    public recordTimestamp?: Date | null,
    public cancelled?: ICancelledMySuffix | null,
    public payload?: IPayloadMySuffix | null,
  ) {}
}
