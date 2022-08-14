package ggc.exceptions;

public class BadDateException extends Exception {

    private final int _date;

    public BadDateException(int date){
        _date = date;
    }

    public int getDate(){
        return _date;
    }

}
