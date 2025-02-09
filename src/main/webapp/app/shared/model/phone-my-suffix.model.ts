import { type IPayloadMySuffix } from '@/shared/model/payload-my-suffix.model';

export interface IPhoneMySuffix {
  id?: string;
  type?: string | null;
  number?: string | null;
  payload?: IPayloadMySuffix | null;
}

export class PhoneMySuffix implements IPhoneMySuffix {
  constructor(
    public id?: string,
    public type?: string | null,
    public number?: string | null,
    public payload?: IPayloadMySuffix | null,
  ) {}
}
