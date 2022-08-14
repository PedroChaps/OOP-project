package ggc;

public class Acquisition extends Transaction {
    public Acquisition (int id, int amount, double price,Partner partner,Product product){
        super(id, amount, price, partner, product);
    }

    public String getName(){
        return "COMPRA";
    }

    @Override
    public String displayTransaction(int date){
        return super.toString() + "|" + Math.round(getFinalPrice()) + "|" + getPaymentDate();
    }

    @Override
    public String toString(){
        return super.toString() + "|" + Math.round(getFinalPrice()) + "|" + getPaymentDate();
    }

    public void acceptAcquisitionVisitor(AcquisitionVisitor v){
        v.showTransaction(this);
    }
}
