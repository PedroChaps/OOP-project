package ggc;

import ggc.exceptions.NotEnoughQuantityException;

public class SimpleProduct extends Product{

    public SimpleProduct(String id, double price){
        super(id, price);
        setOffsetN(3);
    }

    public Breakdown acceptBreakdown(ProductVisitor visitor,
                                               Partner p, int amount){
        throw new UnsupportedOperationException();
    }

    public boolean checkAvailability(int amount) throws
                                          NotEnoughQuantityException {
        return getStock() >= amount;
    }

    public double acceptSale(ProductVisitor visitor){
        throw new UnsupportedOperationException();
    }
}
