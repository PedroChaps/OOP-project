package ggc;

import java.util.List;

public class AcquisitionVisitor extends TransactionsVisitor {

    private List<Transaction> _transactions;

    public AcquisitionVisitor(Warehouse warehouse,List<Transaction> acquisitons){
        super(warehouse);
        _transactions = acquisitons;

    }
    public void showTransaction(Transaction t){
        getWarehouse().addToList(t,_transactions);
    }
}
