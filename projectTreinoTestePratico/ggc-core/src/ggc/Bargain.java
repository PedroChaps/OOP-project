package ggc;

public class Bargain extends Notification {
    private String _name = "BARGAIN";

    public Bargain (DeliveryMethod d, Product product, double price){
        super(d,  product,  price);
    }

    public String getName(){
        return _name;
    }
}
