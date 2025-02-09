import { type IMetadataMySuffix } from '@/shared/model/metadata-my-suffix.model';

export interface IPayloadMySuffix {
  id?: string;
  lastName?: string | null;
  firstName?: string | null;
  birthDate?: Date | null;
  lang?: string | null;
  isVip?: boolean | null;
  metadata?: IMetadataMySuffix | null;
}

export class PayloadMySuffix implements IPayloadMySuffix {
  constructor(
    public id?: string,
    public lastName?: string | null,
    public firstName?: string | null,
    public birthDate?: Date | null,
    public lang?: string | null,
    public isVip?: boolean | null,
    public metadata?: IMetadataMySuffix | null,
  ) {
    this.isVip = this.isVip ?? false;
  }
}
