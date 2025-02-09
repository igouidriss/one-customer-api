import { type IPayloadMySuffix } from '@/shared/model/payload-my-suffix.model';

export interface IAddressMySuffix {
  id?: string;
  type?: string | null;
  zipCode?: string | null;
  city?: string | null;
  country?: string | null;
  line1?: string | null;
  line2?: string | null;
  line3?: string | null;
  payload?: IPayloadMySuffix | null;
}

export class AddressMySuffix implements IAddressMySuffix {
  constructor(
    public id?: string,
    public type?: string | null,
    public zipCode?: string | null,
    public city?: string | null,
    public country?: string | null,
    public line1?: string | null,
    public line2?: string | null,
    public line3?: string | null,
    public payload?: IPayloadMySuffix | null,
  ) {}
}
