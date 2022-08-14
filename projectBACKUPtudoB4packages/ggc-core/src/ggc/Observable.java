package ggc;

public interface Observable {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers(Notification n);
}
