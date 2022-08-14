package ggc;

import java.io.Serializable;

public abstract class Notification implements Serializable {
    private DeliveryMethod _method;
    private Product _product;
    private double _price;

    public Notification(DeliveryMethod method, Product product, double price){
        _method = method;
        _product = product;
        _price = price;
    }

    public DeliveryMethod getDeliveryMethod(){return _method;}

    public abstract String getName();
    @Override
    public String toString(){
        return getName() + "|" + _product.getId() + "|" +Math.round(_price);
    }
}
