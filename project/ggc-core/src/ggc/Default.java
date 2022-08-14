package ggc;

import java.io.Serializable;

public class Default implements DeliveryMethod, Serializable {

    public void deliver(Partner p,Notification n){
        p.addNotification(n);
    }
}
