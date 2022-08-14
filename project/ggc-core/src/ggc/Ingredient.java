package ggc;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private Product _product;
    private int _amount;

    public Ingredient(Product product, int amount){
        _product = product;
        _amount = amount;
    }

    public Product getProduct(){return _product;}
    public int getAmount(){return _amount;}
}
