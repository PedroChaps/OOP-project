package ggc;

import java.io.Serializable;
import java.util.List;

public abstract class TransactionsVisitor implements Serializable {
    private Warehouse _warehouse;

    public TransactionsVisitor (Warehouse warehouse){
        _warehouse = warehouse;
    }
    public Warehouse getWarehouse(){return _warehouse;}
    public abstract void showTransaction(Transaction t);
}
