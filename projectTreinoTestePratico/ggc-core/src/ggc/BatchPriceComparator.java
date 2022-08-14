package ggc;

import java.io.Serializable;
import java.util.Comparator;

public class BatchPriceComparator implements Serializable, Comparator<Batch>{
    public int compare(Batch b1, Batch b2){
        if ((int) (Math.signum(b1.getPrice() - b2.getPrice())) == 0)
            return b1.getId() - b2.getId();

        return (int) Math.signum(b1.getPrice() - b2.getPrice());
    }
}
