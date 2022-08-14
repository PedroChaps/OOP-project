package ggc;

import java.io.Serializable;
import java.text.Collator;

public class Batch implements Serializable, Comparable<Batch> {

    private final Partner _partner;
    private final Product _product;
    private final int _id;
    private final double _price;
    private int _amount;
    private int _currentAmount;


    public Batch(Partner partner, Product product, int id,
                                           int amount, double price) {
        _partner = partner;
        _product = product;
        _id = id;
        _amount = amount;
        _currentAmount = amount;
        _price = price;
    }


    public Partner getPartner() {
        return _partner;
    }

    public Product getProduct() {
        return _product;
    }

    public int getId(){
        return _id;
    }

    public double getPrice() {
        return _price;
    }

    public int getAmount() {
        return _amount;
    }



    public boolean soldOutBatch() {
        return _currentAmount == 0;
    }

    public void sellout(){
        _currentAmount = 0;
    }

    public void reduceAmount(int amount){
        _currentAmount -= amount;
    }


    @Override
    public String toString(){
        return _product.getId() + "|" +
                _partner.getId() + "|" +
                Math.round(_price) + "|" +
                _amount;
    }


    public int compareTo(Batch other){

        if (_product.compareTo(other.getProduct()) == 0){
            if (_partner.compareTo(other.getPartner()) == 0){
                if (_price == other.getPrice()){
                    return _amount - other.getAmount();
                }
                return (int) Math.signum(_price - other.getPrice());
            }
            return _partner.compareTo(other.getPartner());
        }
        return _product.compareTo(other.getProduct());
    }


    @Override
    public boolean equals(Object o){
        if  (o instanceof Batch){
            Batch b = (Batch) o;
            return _id == b.getId();
        }
        return false;
    }

}

