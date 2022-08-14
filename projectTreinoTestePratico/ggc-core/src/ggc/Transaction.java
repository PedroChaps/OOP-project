package ggc;


import java.io.Serializable;

public abstract class Transaction implements Serializable {
    private int _id;
    private int _amount;
    private double _finalPrice = 0;
    private double _paidPrice = 0;
    private Partner _partner;
    private int _paymentDate;
    private Product _product;
    private boolean _paid = false;

    public Transaction (int id, int amount, double finalPrice,
                                    Partner partner, Product product){
        _id = id;
        _amount = amount;
        _finalPrice = finalPrice;
        _partner = partner;
        _product = product;
    }

    public int getId(){return _id;}
    public int getAmount(){return _amount;}
    public Partner getAssociatedPartner(){
        return _partner;
    }
    public Product getAssociatedProduct(){
        return _product;
    }
    public double getFinalPrice(){return _finalPrice;}
    public double getPaidPrice(){return _paidPrice;}
    public int getPaymentDate(){return  _paymentDate;}
    public abstract String getName();


    public void setPaymentDate(int date){
        _paymentDate = date;
    }
    public void setFinalPrice(double price){
        _finalPrice = price;
    }
    public void setPaidPrice(double price){
        _paidPrice = price;
    }


    public boolean isPaid(){return _paid;}
    public void gotPaid(){_paid = true;}
    public void pay(int date){} // Default is do nothing


    //toString made in China
    public abstract String displayTransaction(int date);

    @Override
    public String toString(){
        return getName() + "|" +
                getId() + "|" +
                _partner.getId() + "|" +
                _product.getId() + "|" +
                getAmount();
    }
    public void acceptAcquisitionVisitor(AcquisitionVisitor v){}
    public void acceptSalesAndBreakDownVisitor(SalesAndBreakDownVisitor v){}
    public Sale acceptFindHighestSale(HighestSaleVisitor v){
        throw new UnsupportedOperationException();
    }
}
