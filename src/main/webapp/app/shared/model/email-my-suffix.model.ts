import { type IPayloadMySuffix } from '@/shared/model/payload-my-suffix.model';

export interface IEmailMySuffix {
  id?: string;
  type?: string | null;
  value?: string | null;
  payload?: IPayloadMySuffix | null;
}

export class EmailMySuffix implements IEmailMySuffix {
  constructor(
    public id?: string,
    public type?: string | null,
    public value?: string | null,
    public payload?: IPayloadMySuffix | null,
  ) {}
}
