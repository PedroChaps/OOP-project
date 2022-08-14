package ggc;

public interface Observer {
    void update(Notification n);
    boolean equals(Object o);
}
