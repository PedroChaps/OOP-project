package ggc.exceptions;

public class UndefinedPartnerKeyException extends Exception {

    // private static final long serialVersionUID = 202109192006L;

    private final String _id;

    public UndefinedPartnerKeyException(String id){
        _id = id;
    }

    public String getId(){
        return _id;
    }
}
