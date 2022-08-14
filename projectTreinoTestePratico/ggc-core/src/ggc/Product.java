package ggc;
import ggc.exceptions.NotEnoughQuantityException;

import java.io.Serializable;
import java.text.Collator;
import java.util.HashSet;

public abstract class Product implements Serializable, Comparable<Product>, Observable {
    private String _id;
    private int _stock = 0;
    private double _maxPrice;
    private double _minPrice;
    private int _offsetN;
    private HashSet<Observer> _observers = new HashSet<>();


    public Product(String id, double maxPrice){
        _id = id;
        _maxPrice = maxPrice;
        _minPrice = maxPrice;
    }

    public String getId(){return _id;}
    public int getStock(){return _stock;}
    public int getOffsetN(){return _offsetN;}
    public double getMaxPrice(){
        return _maxPrice;
    }


    public void setOffsetN(int offset){
        _offsetN = offset;
    }
    public void setMaxPrice(double newMaxPrice){
        _maxPrice = newMaxPrice;
    }
    public void setMinPrice(double newMinPrice){
        _minPrice = newMinPrice;
    }
    public void changeStock(int quantity){_stock += quantity;}


    public int compareTo(Product other){
        final Collator collator = Collator.getInstance();
        collator.setStrength(Collator.NO_DECOMPOSITION);

        return collator.compare(_id.toLowerCase(),
                other.getId().toLowerCase());
    }



    public boolean containsObserver(Observer o){
        return _observers.contains(o);
    }
    public void registerObserver(Observer o){
        _observers.add(o);
    }
    public void removeObserver(Observer o){
        _observers.remove(o);
    }
    public void notifyObservers(Notification n){
        for (Observer o : _observers)
            o.update(n);
    }


    public abstract Breakdown acceptBreakdown(ProductVisitor warehouse, Partner p, int amount);
    public abstract double acceptSale(ProductVisitor visitor);
    public abstract boolean checkAvailability(int amount) throws NotEnoughQuantityException;


    public boolean equals(Object o){
        if  (o instanceof Product){
            Product p = (Product) o;
            return _id.equals(p.getId());
        }
        return false;
    }

    @Override
    public String toString(){
        return _id + "|" +
                Math.round(_maxPrice) + "|" +
                _stock;
    }

}
