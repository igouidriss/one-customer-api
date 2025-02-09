export interface ICancelledMySuffix {
  id?: string;
  cancelReason?: string | null;
  isItCancelled?: boolean | null;
  cancelDate?: Date | null;
}

export class CancelledMySuffix implements ICancelledMySuffix {
  constructor(
    public id?: string,
    public cancelReason?: string | null,
    public isItCancelled?: boolean | null,
    public cancelDate?: Date | null,
  ) {
    this.isItCancelled = this.isItCancelled ?? false;
  }
}
