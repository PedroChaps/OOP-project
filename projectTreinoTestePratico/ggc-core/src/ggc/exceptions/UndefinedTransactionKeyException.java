package ggc.exceptions;

public class UndefinedTransactionKeyException extends Exception {

    private final int _id;

    public UndefinedTransactionKeyException(int id){
        _id = id;
    }

    public int getId(){
        return _id;
    }

}
