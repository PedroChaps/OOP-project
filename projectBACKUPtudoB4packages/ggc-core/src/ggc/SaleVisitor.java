package ggc;

public class SaleVisitor extends ProductVisitor{

    public SaleVisitor(Warehouse warehouse){super(warehouse);}

    public Breakdown sellBatches(DerivedProduct pr, Partner p, int amount){
        throw new UnsupportedOperationException();
    }

    public double aggregate(DerivedProduct p){
        return getWarehouse().aggregate(p);
    }
}
