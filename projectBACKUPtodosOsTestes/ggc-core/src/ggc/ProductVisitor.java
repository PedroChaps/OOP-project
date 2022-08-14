package ggc;

import java.io.Serializable;

public abstract class ProductVisitor implements Serializable {
    private Warehouse _warehouse;

    public ProductVisitor (Warehouse warehouse){ _warehouse = warehouse;}
    public abstract Breakdown sellBatches(DerivedProduct pr, Partner p, int amount);
    public Warehouse getWarehouse(){return _warehouse;}
    public abstract double aggregate(DerivedProduct p);
}
