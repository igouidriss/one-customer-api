export interface IMetadataMySuffix {
  id?: string;
  idEvent?: string | null;
  metaTimestamp?: Date | null;
}

export class MetadataMySuffix implements IMetadataMySuffix {
  constructor(
    public id?: string,
    public idEvent?: string | null,
    public metaTimestamp?: Date | null,
  ) {}
}
