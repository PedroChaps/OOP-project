package ggc;

public class New extends Notification {
    String _name = "NEW";
    public New(DeliveryMethod d, Product product, double price){
        super(d, product, price);
    }

    public String getName(){
        return _name;
    }

}
