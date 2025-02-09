import { type IGoldenRecordMySuffix } from '@/shared/model/golden-record-my-suffix.model';

export interface ISourceReferenceMySuffix {
  id?: string;
  source?: string | null;
  value?: string | null;
  goldenRecord?: IGoldenRecordMySuffix | null;
}

export class SourceReferenceMySuffix implements ISourceReferenceMySuffix {
  constructor(
    public id?: string,
    public source?: string | null,
    public value?: string | null,
    public goldenRecord?: IGoldenRecordMySuffix | null,
  ) {}
}
