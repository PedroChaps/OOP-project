package ggc;

public class BreakdownVisitor extends ProductVisitor{

    public BreakdownVisitor(Warehouse warehouse){
        super(warehouse);
    }

    public Breakdown proceedWithBreakdown(DerivedProduct pr,
                                          Partner p, int amount) {
        Warehouse wh = getWarehouse();

        double priceToPay = wh.sellBatches(pr, amount);
        double priceReturned = wh.createdBatchesFromDerivedProduct(pr,
                                                           p, amount);

        double finalPrice = priceToPay - priceReturned;

        Breakdown b = new Breakdown(wh.getTransactionsID(), amount,
               finalPrice, p, pr, wh.craftExtendedRecipe(pr, amount));
        b.pay(wh.getDate());

        return b;
    }

    public double aggregate(DerivedProduct p){
        throw new UnsupportedOperationException();
    }
}
