export interface IExpensesMySuffix {
  id?: string;
  totalExpense?: number | null;
}

export class ExpensesMySuffix implements IExpensesMySuffix {
  constructor(
    public id?: string,
    public totalExpense?: number | null,
  ) {}
}
