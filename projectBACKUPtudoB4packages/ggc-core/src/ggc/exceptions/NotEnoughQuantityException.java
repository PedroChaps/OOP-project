package ggc.exceptions;

import ggc.Notification;
import ggc.Product;

public class NotEnoughQuantityException extends Exception{
    private Product _product;
    private int _requested;
    private int _available;
    public NotEnoughQuantityException(Product product, int requested,int available){
        _product = product;
        _requested = requested;
        _available = available;
    }
    public String getId(){
        return _product.getId();
    }
    public int getRequested(){return _requested;}
    public int getAvailable(){return _available;}
}
