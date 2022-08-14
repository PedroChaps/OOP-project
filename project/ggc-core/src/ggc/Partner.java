package ggc;

import java.io.Serializable;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

public class Partner implements Serializable, Comparable<Partner>, Observer {
    private String _id;
    private String _name;
    private String _address;
    private double _totalValueBought = 0;
    private double _totalValueSold = 0;
    private double _totalValuePaid = 0;
    private ArrayList<Notification> _notifications = new ArrayList<>();

    private Status _status = new NormalStatus(this,0);

    protected void setStatus(Status status){
        _status = status;
    }


    public Partner(String id, String name, String address){
        _id = id;
        _address = address;
        _name = name;
    }


    public String getId(){
        return _id;
    }
    public String getName(){
        return _name;
    }
    public String getAddress(){
        return _address;
    }


    public void addValueBought(double value){
        _totalValueBought += value;
    }
    public void addValueSold(double value){
        _totalValueSold += value;
    }
    public void addValuePaid(double value){
        _totalValuePaid += value;
    }


    public void registerNonDelayedSell(double value){
        _status.nonDelayedSell(value);
    }
    public void registerDelayedSell(double delay){
        _status.delayedSell(delay);
    }
    public double getDiscountOrFine(int limitDate, int currentDate, int N){
        return _status.getDiscountOrFine(limitDate, currentDate, N);
    }
    public String getStatusName(){return _status.getName();}
    public double getStatusPoints(){return _status.getPoints();}


    public void update (Notification n){
        n.getDeliveryMethod().deliver(this,n);
    }
    public boolean hasNotifications(){
        return (_notifications.size() != 0);
    }
    public void addNotification (Notification n){
        _notifications.add(n);
    }
    public List<Notification> getNotifications(){
        return _notifications;
    }
    public void removeAllNotifications(){
        _notifications.clear();
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Partner){
            Partner a = (Partner) o;
            return _id.equals(a.getId()) && _name.equals(a.getName())
                    && _address.equals(a.getAddress());
        }
        return false;
    }

    @Override
    public String toString(){
        return _id + "|" +
                _name + "|" +
                _address + "|" +
                getStatusName() + "|" +
                Math.round(getStatusPoints()) + "|" +
                Math.round(_totalValueBought) + "|" +
                Math.round(_totalValueSold) + "|" +
                Math.round(_totalValuePaid);
    }

    public int compareTo(Partner other){
        final Collator collator = Collator.getInstance();
        collator.setStrength(Collator.NO_DECOMPOSITION);

        return collator.compare(_id.toLowerCase(),
                other.getId().toLowerCase());
    }

}
