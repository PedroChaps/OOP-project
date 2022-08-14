package ggc;

import java.util.List;

public class SalesAndBreakDownVisitor extends TransactionsVisitor{
    private List<String> _transactions;

    private int _date;

    public SalesAndBreakDownVisitor(Warehouse warehouse,
                                   List<String> transactions, int date){
        super(warehouse);
        _transactions = transactions;
        _date = date;
    }
    public void showTransaction(Transaction t){
        getWarehouse().addToList(t.displayTransaction(_date), _transactions);
    }
}
